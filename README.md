# Clans API

The official developer API for the **Clans** Minecraft plugin (Paper / Folia, 1.21+).

This repository contains **only the public API** — the interfaces, events, and enums you
compile against. The implementation ships inside the Clans plugin itself and is provided
at runtime, so you never bundle this dependency into your jar.

[![](https://jitpack.io/v/Ravi-DevX/Clans-API.svg)](https://jitpack.io/#Ravi-DevX/Clans-API)

---

## Adding the dependency

The API is distributed through [JitPack](https://jitpack.io). Add the repository and the
dependency to your build. Always use `compileOnly` (Gradle) / `provided` (Maven) — the
classes are supplied by the Clans plugin at runtime.

### Gradle (Kotlin DSL)

```kotlin
repositories {
    maven("https://jitpack.io")
}

dependencies {
    compileOnly("com.github.Ravi-DevX:Clans-API:1.0.0")
}
```

### Gradle (Groovy DSL)

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    compileOnly 'com.github.Ravi-DevX:Clans-API:1.0.0'
}
```

### Maven

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.Ravi-DevX</groupId>
    <artifactId>Clans-API</artifactId>
    <version>1.0.0</version>
    <scope>provided</scope>
</dependency>
```

> Replace `1.0.0` with any released tag, or use a commit hash / `main-SNAPSHOT` for the
> latest build.

---

## Depending on the Clans plugin

So that Clans loads **before** your plugin and the API is ready, declare it in your
`plugin.yml`:

```yaml
depend: [Clans]          # hard dependency - your plugin won't load without Clans
# or
softdepend: [Clans]      # optional - guard your calls with ClansProvider.isAvailable()
```

---

## Usage

### Getting the API

```java
import com.shyamstudio.clans.api.ClansAPI;
import com.shyamstudio.clans.api.model.ClanProfile;

ClansAPI api = ClansAPI.get();

ClanProfile clan = api.getClanByPlayer(player.getUniqueId());
if (clan != null) {
    getLogger().info(player.getName() + " is in clan " + clan.getTag()
            + " (rank #" + clan.getRank() + ", score " + clan.getScore() + ")");
}
```

If you used `softdepend`, guard the call:

```java
import com.shyamstudio.clans.api.ClansProvider;

if (ClansProvider.isAvailable()) {
    ClansAPI api = ClansAPI.get();
    // ...
}
```

### Reading clan data

```java
ClanProfile clan = api.getClanByTag("WOLVES");
if (clan != null) {
    String name        = clan.getDisplayName();
    int members        = clan.getAllMembers().size();
    double balance     = clan.getBalance();
    boolean open       = clan.isOpen();
    boolean friendlyFF = clan.isFriendlyFire();

    // Members and their roles
    clan.getAllMembers().forEach(member ->
        System.out.println(member.getUsername() + " - " + member.getRole().getName()));

    // Allies
    clan.getAllyClans().forEach(ally ->
        System.out.println("Allied with " + ally.getTag()));
}
```

### Reading player stats

```java
import com.shyamstudio.clans.api.model.UserProfile;
import com.shyamstudio.clans.api.option.ProfileMetric;

UserProfile user = api.getUser(player.getUniqueId());
if (user != null) {
    int kills  = user.getStatistic(ProfileMetric.KILLS);
    int deaths = user.getStatistic(ProfileMetric.DEATHS);
}
```

### Listening to events

Every clan action fires a Bukkit event you can listen and (where applicable) cancel.

```java
import com.shyamstudio.clans.api.event.ClanCreateEvent;
import com.shyamstudio.clans.api.event.PlayerJoinClanEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ClanListener implements Listener {

    @EventHandler
    public void onClanCreate(ClanCreateEvent event) {
        if (event.getTag().equalsIgnoreCase("staff")) {
            event.setCancelled(true); // block reserved tags
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinClanEvent event) {
        getLogger().info(event.getUser().getName()
                + " joined clan " + event.getClan().getTag());
    }
}
```

---

## API surface

### Entry points
| Class | Purpose |
|-------|---------|
| `ClansAPI` | Main accessor — `ClansAPI.get()` |
| `ClansProvider` | Holds the runtime instance — `isAvailable()` |

### Models (`com.shyamstudio.clans.api.model`)
| Interface | Represents |
|-----------|------------|
| `ClanProfile` | A clan: tag, name, members, allies, home, bank, score, rank, settings |
| `UserProfile` | A player: clan membership, statistics, perk limits |
| `MemberProfile` | A clan member: uuid, username, role, permissions |
| `LeaderProfile` | The clan owner (extends `MemberProfile`) |
| `RoleProfile` | A clan role: name, priority, permissions, symbol, color |

### Options (`com.shyamstudio.clans.api.option`)
| Enum | Values |
|------|--------|
| `ClanPrivilege` | Per-role permissions (INVITE_MEMBERS, KICK_MEMBERS, OPEN_VAULT, BANK_WITHDRAW, ...) |
| `ProfileMetric` | Tracked stats (KILLS, DEATHS, DIAMONDS_MINED, SCRAP_SMELTED, BLOCKS_PLACED, BLOCKS_BROKEN, MOBS_KILLED) |

### Events (`com.shyamstudio.clans.api.event`)
All extend `Event` and implement `Cancellable`.

`ClanCreateEvent` · `ClanDisbandEvent` · `PlayerJoinClanEvent` · `PlayerLeaveClanEvent` ·
`ClanAllyChangeEvent` · `ClanBankTransactionEvent` · `ClanRoleChangeEvent` ·
`ClanRenameEvent` · `ClanHomeTeleportEvent` · `ClanHomeUpdateEvent` · `ClanChestOpenEvent`

---

## Notes

- **Roles use inverted priority** — a *lower* priority number means a *higher* rank
  (Leader = -1, Member = 3). Use `RoleProfile#isHigherThan` rather than comparing numbers
  by hand.
- A clan's unique id (`ClanProfile#getClanId`) is the **owner's UUID**.
- The API is read-oriented and event-driven; mutating helpers on `ClanProfile`
  (e.g. bank deposit/withdraw) require an online `Player` and respect the plugin's rules.

## Versioning

The API follows semantic versioning. Added methods → minor bump; changed/removed
methods → major bump. Pin a specific tag in production.

## License

Released under the MIT License — see [LICENSE](LICENSE).
