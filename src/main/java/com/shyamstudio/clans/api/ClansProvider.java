package com.shyamstudio.clans.api;

import org.jetbrains.annotations.NotNull;

/**
 * Holds the singleton {@link ClansAPI} instance supplied by the Clans plugin at runtime.
 *
 * <p>Consumers should not use this class directly - call {@link ClansAPI#get()} instead.
 * The {@link #register(ClansAPI)} and {@link #unregister()} methods are <b>internal</b>
 * and are invoked by the Clans plugin during enable / disable.</p>
 */
public final class ClansProvider {

    private static ClansAPI instance;

    private ClansProvider() {
    }

    /**
     * Gets the registered API instance.
     *
     * @return the active {@link ClansAPI}
     * @throws IllegalStateException if the Clans plugin has not registered the API yet
     */
    @NotNull
    public static ClansAPI get() {
        ClansAPI api = instance;
        if (api == null) {
            throw new IllegalStateException(
                    "The Clans API is not loaded yet. Make sure the Clans plugin is installed and enabled, "
                            + "and that your plugin declares Clans under 'depend' (or 'softdepend') in plugin.yml.");
        }
        return api;
    }

    /**
     * @return {@code true} if the API has been registered and is ready to use
     */
    public static boolean isAvailable() {
        return instance != null;
    }

    /**
     * Registers the API implementation. Internal - called by the Clans plugin only.
     *
     * @param api the implementation instance
     * @apiNote Not part of the public contract; do not call from consumer plugins.
     */
    public static void register(@NotNull ClansAPI api) {
        instance = api;
    }

    /**
     * Clears the registered API. Internal - called by the Clans plugin on disable.
     *
     * @apiNote Not part of the public contract; do not call from consumer plugins.
     */
    public static void unregister() {
        instance = null;
    }
}
