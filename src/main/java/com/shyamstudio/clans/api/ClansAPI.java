package com.shyamstudio.clans.api;

import com.shyamstudio.clans.api.model.ClanProfile;
import com.shyamstudio.clans.api.model.UserProfile;
import com.shyamstudio.clans.api.option.ChatChannel;
import com.shyamstudio.clans.api.option.ClanFeature;
import com.shyamstudio.clans.api.service.ChatService;
import com.shyamstudio.clans.api.service.ClanService;
import com.shyamstudio.clans.api.service.ClansExtensionService;
import com.shyamstudio.clans.api.service.PendingRequestService;
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
 * order, or use {@code softdepend} and guard with {@link ClansProvider#isAvailable()}.
 * Because Clans completes licensed storage startup asynchronously, also check
 * {@link #isReady()} or listen for {@link com.shyamstudio.clans.api.event.ClansReadyEvent}
 * before gameplay queries and mutations.</p>
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
     * Gets the public API contract version exposed by this artifact.
     *
     * <p>The default keeps implementations compiled against API 1.0.0 source and
     * binary compatible. Implementations may override it when exposing a different
     * contract version.</p>
     *
     * @return the public API contract version
     * @since 1.1.0
     */
    default @NotNull String getApiVersion() {
        return ApiVersion.CURRENT;
    }

    /**
     * Gets the version of the running Clans plugin implementation.
     *
     * @return the plugin version, or {@code "unknown"} when the implementation
     *         does not expose version metadata
     * @since 1.1.0
     */
    default @NotNull String getPluginVersion() {
        return "unknown";
    }

    /**
     * Checks whether the running Clans implementation has finished asynchronous
     * startup and may serve gameplay queries and mutations.
     *
     * @return {@code true} only after Clans has published its ready event
     * @since 1.2.0
     */
    default boolean isReady() {
        return false;
    }

    /**
     * Checks whether a Clans feature is enabled on the running server.
     *
     * <p>Implementations compiled against API 1.0.0 default to enabled because
     * that contract did not expose runtime feature switches.</p>
     *
     * @param feature the feature to check
     * @return {@code true} when the feature is enabled
     * @since 1.1.0
     */
    default boolean isFeatureEnabled(@NotNull ClanFeature feature) {
        return true;
    }

    /**
     * Gets a clan by its unique ID. The ID is a stable, immutable identifier
     * that does not change across renames or ownership transfers; do not assume
     * it equals the owner's UUID. A disbanded clan is deleted and must not be
     * treated as the same identity as a later clan.
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
     * Gets the effective chat channel selected for a player.
     *
     * <p>Implementations compiled against API 1.0.0 default to
     * {@link ChatChannel#PUBLIC} because they cannot expose authoritative channel
     * state through this contract.</p>
     *
     * @param playerUuid the player's unique UUID
     * @return the player's effective chat channel
     * @since 1.1.0
     */
    default @NotNull ChatChannel getChatChannel(@NotNull UUID playerUuid) {
        return ChatChannel.PUBLIC;
    }

    /**
     * Gets all currently loaded and cached clans on the server.
     *
     * <p>Treat the returned collection and profiles as read-only. An implementation
     * may return a live view that changes as clans load or unload; copy it when a
     * stable snapshot is required.</p>
     *
     * @return the currently loaded clans
     */
    @NotNull
    Collection<ClanProfile> getClans();

    /**
     * Gets all currently active online users tracked by the plugin.
     *
     * <p>Treat the returned map and profiles as read-only. An implementation may
     * return a live view; copy it when a stable snapshot is required.</p>
     *
     * @return the currently tracked online users by UUID
     */
    @NotNull
    Map<UUID, UserProfile> getOnlineUsers();

    /**
     * Gets controlled clan and ally chat operations.
     *
     * @return the chat service
     * @throws UnsupportedOperationException if the running implementation predates
     *         this service
     * @since 1.1.0
     */
    default @NotNull ChatService getChatService() {
        throw new UnsupportedOperationException("ChatService is not available in this Clans implementation");
    }

    /**
     * Gets read-only access to pending invitations and requests.
     *
     * @return the pending-request service
     * @throws UnsupportedOperationException if the running implementation predates
     *         this service
     * @since 1.1.0
     */
    default @NotNull PendingRequestService getPendingRequestService() {
        throw new UnsupportedOperationException("PendingRequestService is not available in this Clans implementation");
    }

    /**
     * Gets validated clan mutation operations.
     *
     * @return the clan service
     * @throws UnsupportedOperationException if the running implementation predates
     *         this service
     * @since 1.1.0
     */
    default @NotNull ClanService getClanService() {
        throw new UnsupportedOperationException("ClanService is not available in this Clans implementation");
    }

    /**
     * Gets the owner-scoped extension registry used by supported Clans addons.
     *
     * <p>Extensions registered through this service are collision checked and are
     * removed automatically when their owning plugin disables.</p>
     *
     * <p>A consumer intentionally supporting a runtime whose API jar predates 1.2.0
     * must check {@link #getApiVersion()} before linking this method; otherwise the JVM
     * may report {@link NoSuchMethodError} before a default implementation can run.</p>
     *
     * @return the extension service
     * @throws UnsupportedOperationException if the running implementation predates
     *         this service
     * @since 1.2.0
     */
    default @NotNull ClansExtensionService getExtensionService() {
        throw new UnsupportedOperationException("ClansExtensionService is not available in this Clans implementation");
    }

}
