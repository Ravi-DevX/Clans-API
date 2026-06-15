package com.shyamstudio.clans.api;

import com.shyamstudio.clans.api.model.ClanProfile;
import com.shyamstudio.clans.api.model.UserProfile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 * The public developer API for the Clans plugin.
 *
 * <p>This is a <b>contract only</b>. The concrete implementation lives inside the
 * Clans plugin and is registered at runtime, so you must add Clans as a dependency
 * (or soft-dependency) of your plugin and obtain the instance after Clans has enabled.</p>
 *
 * <h2>Getting the API</h2>
 * <pre>{@code
 * ClansAPI api = ClansAPI.get();
 * ClanProfile clan = api.getClanByPlayer(player.getUniqueId());
 * }</pre>
 *
 * <p>{@link #get()} throws {@link IllegalStateException} if the Clans plugin is not yet
 * enabled - list Clans under {@code depend} in your {@code plugin.yml} to guarantee load
 * order, or use {@code softdepend} and guard with {@link ClansProvider#isAvailable()}.</p>
 *
 * @see ClansProvider
 */
public interface ClansAPI {

    /**
     * Gets the active Clans API instance.
     *
     * @return the API instance provided by the running Clans plugin
     * @throws IllegalStateException if the Clans plugin has not registered the API yet
     */
    @NotNull
    static ClansAPI get() {
        return ClansProvider.get();
    }

    /**
     * Gets a clan by its unique ID (the owner's UUID).
     *
     * @param clanId the clan's unique UUID
     * @return the clan, or {@code null} if not found
     */
    @Nullable
    ClanProfile getClan(@NotNull UUID clanId);

    /**
     * Gets a player's clan by their UUID.
     *
     * @param playerUuid the player's unique UUID
     * @return the clan, or {@code null} if they are not in a clan
     */
    @Nullable
    ClanProfile getClanByPlayer(@NotNull UUID playerUuid);

    /**
     * Gets a clan by its tag (case-insensitive).
     *
     * @param tag the clan tag
     * @return the clan, or {@code null} if not found
     */
    @Nullable
    ClanProfile getClanByTag(@NotNull String tag);

    /**
     * Gets a clan by its display name (case-insensitive).
     *
     * @param name the clan display name
     * @return the clan, or {@code null} if not found
     */
    @Nullable
    ClanProfile getClanByName(@NotNull String name);

    /**
     * Gets a clan user profile by their UUID.
     *
     * @param playerUuid the player's unique UUID
     * @return the user profile, or {@code null} if offline / not cached
     */
    @Nullable
    UserProfile getUser(@NotNull UUID playerUuid);

    /**
     * Gets all currently loaded and cached clans on the server.
     *
     * @return an unmodifiable collection of all clans
     */
    @NotNull
    Collection<ClanProfile> getClans();

    /**
     * Gets all currently active online users tracked by the plugin.
     *
     * @return an unmodifiable map of player UUID to user profile
     */
    @NotNull
    Map<UUID, UserProfile> getOnlineUsers();
}
