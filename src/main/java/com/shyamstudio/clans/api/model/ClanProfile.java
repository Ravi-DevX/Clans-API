package com.shyamstudio.clans.api.model;

import com.shyamstudio.clans.api.ClansAPI;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Represents a clan within the system.
 *
 * <p>This interface is primarily a read model. Direct mutation methods remain for
 * API 1.0.0 binary compatibility but bypass one or more validation, permission,
 * event, limit, feature, or persistence rules.</p>
 */
public interface ClanProfile {

    /**
     * @return The clan's unique, stable, immutable identifier (does not
     *         necessarily equal the owner's UUID; it is preserved across
     *         ownership transfers)
     */
    @NotNull UUID getClanId();

    /**
     * @return The clan owner
     */
    @NotNull LeaderProfile getOwner();

    /**
     * @return The unique clan name/tag
     */
    @NotNull String getTag();

    /**
     * Sets the unique clan name/tag directly.
     *
     * @deprecated Use {@link ClansAPI#getClanService()} to rename a clan through
     *             validated plugin rules.
     */
    @Deprecated(since = "1.1.0")
    void setTag(@NotNull String tag);

    /**
     * Backwards-compatible alias for {@link #getTag()}.
     *
     * @return the non-null unique clan name/tag
     */
    default @NotNull String getName() {
        return getTag();
    }

    /**
     * @return The formatted clan name/tag for messages and menus
     */
    @NotNull String getDisplayName();

    /**
     * Backwards-compatible alias for {@link #setTag(String)}. Null and blank values
     * are ignored to preserve the API 1.0.0 nullable call contract.
     *
     * @deprecated Use {@link ClansAPI#getClanService()} to rename a clan through
     *             validated plugin rules.
     */
    @Deprecated(since = "1.1.0")
    default void setName(@Nullable String name) {
        if (name != null && !name.isBlank()) {
            setTag(name);
        }
    }

    /**
     * <p>Treat the returned list as read-only. It may be a live view; copy it when
     * a stable snapshot is required.</p>
     *
     * @return members excluding the owner
     */
    @NotNull List<MemberProfile> getMemberList();

    /**
     * <p>Treat the returned list as read-only. Implementations may return either a
     * snapshot or a live view.</p>
     *
     * @return all members including the owner
     */
    @NotNull List<MemberProfile> getAllMembers();

    /**
     * <p>Treat the returned list as read-only. It may be a live view; copy it when
     * a stable snapshot is required.</p>
     *
     * @return allied clan UUIDs
     */
    @NotNull List<UUID> getAllyList();

    /**
     * <p>Treat the returned list and profiles as read-only. Implementations may
     * return either a snapshot or a live view.</p>
     *
     * @return resolved, non-null allied clan profiles
     */
    @NotNull List<ClanProfile> getAllyClans();

    /**
     * @return The clan home location, or null if not set
     */
    @Nullable Location getHome();

    /**
     * Sets the clan home directly.
     *
     * @deprecated Use {@link ClansAPI#getClanService()} so feature, permission,
     *             event, and persistence rules are applied.
     */
    @Deprecated(since = "1.1.0")
    void setHome(@Nullable Location home);

    /**
     * @return The clan description, or null if not set
     */
    @Nullable String getDescription();

    /**
     * Sets the clan description directly.
     *
     * @deprecated Use {@link ClansAPI#getClanService()} so validation, permission,
     *             event, and persistence rules are applied.
     */
    @Deprecated(since = "1.1.0")
    void setDescription(@Nullable String description);

    /**
     * @return The clan spawn location, or null if not set
     */
    @Nullable Location getSpawn();

    /**
     * Sets the clan spawn location directly.
     *
     * @deprecated Use {@link ClansAPI#getClanService()} so feature, permission,
     *             event, and persistence rules are applied.
     */
    @Deprecated(since = "1.1.0")
    void setSpawn(@Nullable Location spawn);

    /**
     * <p>The returned Bukkit inventory is live and mutable. Mutating it directly
     * bypasses feature, permission, event, and persistence rules.</p>
     *
     * @return the shared clan inventory
     */
    @NotNull Inventory getInventory();

    /**
     * @return Whether friendly fire is enabled
     */
    boolean isFriendlyFire();

    /**
     * Sets friendly fire directly.
     *
     * @deprecated Use {@link ClansAPI#getClanService()} so permission, event, and
     *             persistence rules are applied.
     */
    @Deprecated(since = "1.1.0")
    void setFriendlyFire(boolean enabled);

    /**
     * @return Whether the clan is open (anyone can join)
     */
    boolean isOpen();

    /**
     * Sets open/closed membership status directly.
     *
     * @deprecated Use {@link ClansAPI#getClanService()} so permission, event, and
     *             persistence rules are applied.
     */
    @Deprecated(since = "1.1.0")
    void setOpen(boolean open);

    /**
     * @return The clan's calculated score
     */
    double getScore();

    /**
     * Updates the score cache directly.
     *
     * @deprecated Use validated plugin operations exposed through
     *             {@link ClansAPI#getClanService()} instead of mutating model state.
     */
    @Deprecated(since = "1.1.0")
    void updateScore(double score);

    /**
     * @return The clan's leaderboard rank
     */
    int getRank();

    /**
     * Updates the rank cache directly.
     *
     * @deprecated Use validated plugin operations exposed through
     *             {@link ClansAPI#getClanService()} instead of mutating model state.
     */
    @Deprecated(since = "1.1.0")
    void updateRank(int rank);

    /**
     * @return Whether the tag has been changed from creation default
     */
    boolean isTagChanged();

    /**
     * @return The banner pattern data as serialized string, or null
     */
    @Nullable String getBannerData();

    /**
     * Sets banner pattern data directly.
     *
     * @deprecated Use {@link ClansAPI#getClanService()} so feature, permission,
     *             event, and persistence rules are applied.
     */
    @Deprecated(since = "1.1.0")
    void setBannerData(@Nullable String bannerData);

    // ── Member Management ────────────────────────────────────

    /**
     * Adds a member directly.
     *
     * @deprecated Use {@link ClansAPI#getClanService()} so membership, capacity,
     *             invitation, event, and persistence rules are applied.
     */
    @Deprecated(since = "1.1.0")
    void addMember(@NotNull UserProfile user);

    /**
     * Removes a member directly.
     *
     * @deprecated Use {@link ClansAPI#getClanService()} so membership, permission,
     *             event, and persistence rules are applied.
     */
    @Deprecated(since = "1.1.0")
    void removeMember(@NotNull UUID uuid);

    /**
     * @return The member with the given UUID, or null
     */
    @Nullable MemberProfile getMember(@NotNull UUID uuid);

    /**
     * @return true if the given UUID is a member (including owner)
     */
    boolean hasMember(@NotNull UUID uuid);

    /**
     * @return true if the given UUID is the owner
     */
    boolean isOwner(@NotNull UUID uuid);

    // ── Alliance Management ──────────────────────────────────

    /**
     * Adds an alliance directly.
     *
     * @deprecated Use {@link ClansAPI#getClanService()} so request, limit, event,
     *             and persistence rules are applied.
     */
    @Deprecated(since = "1.1.0")
    void addAlly(@NotNull ClanProfile clan);

    /**
     * Removes an alliance directly.
     *
     * @deprecated Use {@link ClansAPI#getClanService()} so permission, event, and
     *             persistence rules are applied.
     */
    @Deprecated(since = "1.1.0")
    void removeAlly(@NotNull ClanProfile clan);

    boolean isAlliedWith(@NotNull ClanProfile clan);

    // ── Inventory ────────────────────────────────────────────

    /**
     * <p>Treat the returned map and item stacks as read-only. Implementations may
     * return either a snapshot or a live view.</p>
     *
     * @return the inventory contents as a slot-to-item map
     */
    @NotNull Map<Integer, org.bukkit.inventory.ItemStack> getInventoryAsMap();

    // ── Persistence ──────────────────────────────────────────

    /**
     * Marks this clan as dirty and queues it for save directly.
     *
     * @deprecated Use validated operations exposed through
     *             {@link ClansAPI#getClanService()}, which manage persistence.
     */
    @Deprecated(since = "1.1.0")
    void save();

    /**
     * @return true if this clan is marked as closed/deleted
     */
    boolean isClosed();

    /**
     * Sets closed/deleted state directly.
     *
     * @deprecated Use {@link ClansAPI#getClanService()} to disband a clan through
     *             validated plugin rules.
     */
    @Deprecated(since = "1.1.0")
    void setClosed(boolean closed);

    // ── Utilities ────────────────────────────────────────────

    boolean hasName(@NotNull String name);

    boolean hasTag(@NotNull String tag);

    /**
     * @return The clan's current bank balance
     */
    double getBalance();

    /**
     * Sets the clan's bank balance directly.
     *
     * @deprecated Use {@link ClansAPI#getClanService()} so economy, concurrency,
     *             event, and persistence rules are applied.
     */
    @Deprecated(since = "1.1.0")
    void setBalance(double balance);

    /**
     * Deposits money into the clan bank from the player's account.
     *
     * @return true if deposit succeeded
     * @deprecated Use {@link ClansAPI#getClanService()} for validated and
     *             concurrency-safe bank operations.
     */
    @Deprecated(since = "1.1.0")
    boolean deposit(@NotNull org.bukkit.entity.Player player, double amount);

    /**
     * Withdraws money from the clan bank and gives it to the player.
     *
     * @return true if withdrawal succeeded
     * @deprecated Use {@link ClansAPI#getClanService()} for validated and
     *             concurrency-safe bank operations.
     */
    @Deprecated(since = "1.1.0")
    boolean withdraw(@NotNull org.bukkit.entity.Player player, double amount);
}
