import java.security.MessageDigest
import org.gradle.api.tasks.compile.JavaCompile

plugins {
    `java-library`
    `maven-publish`
}

group = "com.shyamstudio.clans"
version = providers.gradleProperty("apiVersion").get()

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
    compileOnly("org.jetbrains:annotations:24.1.0")
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(21)
    withSourcesJar()
    withJavadocJar()
}

tasks.withType<Javadoc> {
    (options as StandardJavadocDocletOptions).addStringOption("Xdoclint:none", "-quiet")
}

val apiPackagePath = "com/shyamstudio/clans/api"
val publicApiSourceDirectory = layout.projectDirectory.dir("src/main/java/$apiPackagePath")
val configuredPluginDirectory = providers.gradleProperty("clansPluginDir").orNull
    ?.trim()
    ?.takeIf(String::isNotEmpty)
    ?.let(::file)
val defaultWindowsPluginDirectory = if (System.getProperty("os.name").startsWith("Windows", ignoreCase = true)) {
    file("R:/ShyamPlugins 2.0/Clans").takeIf(File::isDirectory)
} else {
    null
}
val pluginDirectory = configuredPluginDirectory ?: defaultWindowsPluginDirectory

val verifyApiParity = tasks.register("verifyApiParity") {
    group = LifecycleBasePlugin.VERIFICATION_GROUP
    description = "Verifies that the public API Java sources exactly match the API sources in the Clans plugin."

    inputs.dir(publicApiSourceDirectory)
        .withPathSensitivity(PathSensitivity.RELATIVE)

    pluginDirectory?.let { directory ->
        inputs.dir(directory.resolve("src/main/java/$apiPackagePath"))
            .withPathSensitivity(PathSensitivity.RELATIVE)
    }

    onlyIf {
        if (pluginDirectory == null) {
            val environment = if (System.getenv("CI").equals("true", ignoreCase = true)) "CI" else "this environment"
            logger.lifecycle(
                "[verifyApiParity] Skipped: the private Clans plugin checkout is unavailable in $environment. " +
                    "Provide -PclansPluginDir=/path/to/Clans to enable source parity verification."
            )
            false
        } else {
            true
        }
    }

    doLast {
        val publicRoot = publicApiSourceDirectory.asFile
        val pluginRoot = requireNotNull(pluginDirectory).resolve("src/main/java/$apiPackagePath")

        if (!publicRoot.isDirectory) {
            throw GradleException("Public API source directory does not exist: $publicRoot")
        }
        if (!pluginRoot.isDirectory) {
            val origin = if (configuredPluginDirectory != null) "-PclansPluginDir" else "the Windows default"
            throw GradleException("Plugin API source directory resolved from $origin does not exist: $pluginRoot")
        }

        fun collectJavaSources(root: File): Map<String, File> = root.walkTopDown()
            .filter { it.isFile && it.extension.equals("java", ignoreCase = true) }
            .associateBy { it.relativeTo(root).invariantSeparatorsPath }

        fun sha256(file: File): String {
            val digest = MessageDigest.getInstance("SHA-256")
            file.inputStream().buffered().use { input ->
                val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
                while (true) {
                    val count = input.read(buffer)
                    if (count < 0) break
                    digest.update(buffer, 0, count)
                }
            }
            return digest.digest().joinToString("") { byte -> "%02x".format(byte) }
        }

        val publicSources = collectJavaSources(publicRoot)
        val pluginSources = collectJavaSources(pluginRoot)
        val publicOnly = (publicSources.keys - pluginSources.keys).sorted()
        val pluginOnly = (pluginSources.keys - publicSources.keys).sorted()
        val changed = (publicSources.keys intersect pluginSources.keys)
            .sorted()
            .mapNotNull { path ->
                val publicHash = sha256(publicSources.getValue(path))
                val pluginHash = sha256(pluginSources.getValue(path))
                if (publicHash == pluginHash) null else Triple(path, publicHash, pluginHash)
            }

        if (publicOnly.isNotEmpty() || pluginOnly.isNotEmpty() || changed.isNotEmpty()) {
            val report = buildString {
                appendLine("Clans API source parity verification failed.")
                appendLine("Public API: $publicRoot")
                appendLine("Plugin API: $pluginRoot")
                if (publicOnly.isNotEmpty()) {
                    appendLine("Files only in the public API (${publicOnly.size}):")
                    publicOnly.forEach { appendLine("  + $it") }
                }
                if (pluginOnly.isNotEmpty()) {
                    appendLine("Files only in the plugin API (${pluginOnly.size}):")
                    pluginOnly.forEach { appendLine("  - $it") }
                }
                if (changed.isNotEmpty()) {
                    appendLine("Files with different SHA-256 contents (${changed.size}):")
                    changed.forEach { (path, publicHash, pluginHash) ->
                        appendLine("  * $path")
                        appendLine("      public: $publicHash")
                        appendLine("      plugin: $pluginHash")
                    }
                }
            }
            throw GradleException(report.trimEnd())
        }

        logger.lifecycle("[verifyApiParity] Verified ${publicSources.size} Java source files against $pluginRoot.")
    }
}

tasks.named("check") {
    dependsOn(verifyApiParity)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            artifactId = "Clans-API"
        }
    }
}
