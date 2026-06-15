package com.shyamstudio.clans.api.model;

import org.bukkit.Location;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Represents a clan within the system.
 */
public interface ClanProfile {

    /**
     * @return The clan's unique identifier (owner's UUID)
     */
    @NotNull UUID getClanId();

    /**
     * @return The clan owner
     */
    @NotNull LeaderProfile getOwner();

    /**
     * @return The clan tag (short identifier, unique)
     */
    @NotNull String getTag();

    /**
     * Sets the clan tag.
     */
    void setTag(@NotNull String tag);

    /**
     * @return The clan display name, or null if not set
     */
    @Nullable String getName();

    /**
     * @return The formatted display name (fallback to placeholder if null)
     */
    @NotNull String getDisplayName();

    /**
     * Sets the clan display name.
     */
    void setName(@Nullable String name);

    /**
     * @return Members list (excluding owner)
     */
    @NotNull List<MemberProfile> getMemberList();

    /**
     * @return All members including owner
     */
    @NotNull List<MemberProfile> getAllMembers();

    /**
     * @return List of allied clan UUIDs
     */
    @NotNull List<UUID> getAllyList();

    /**
     * @return List of resolved allied ClanProfile objects (non-null only)
     */
    @NotNull List<ClanProfile> getAllyClans();

    /**
     * @return The clan home location, or null if not set
     */
    @Nullable Location getHome();

    /**
     * Sets the clan home.
     */
    void setHome(@Nullable Location home);

    /**
     * @return The clan description, or null if not set
     */
    @Nullable String getDescription();

    /**
     * Sets the clan description.
     */
    void setDescription(@Nullable String description);

    /**
     * @return The clan spawn location, or null if not set
     */
    @Nullable Location getSpawn();

    /**
     * Sets the clan spawn location.
     */
    void setSpawn(@Nullable Location spawn);

    /**
     * @return The shared clan inventory (chest)
     */
    @NotNull Inventory getInventory();

    /**
     * @return Whether friendly fire is enabled
     */
    boolean isFriendlyFire();

    /**
     * Sets friendly fire.
     */
    void setFriendlyFire(boolean enabled);

    /**
     * @return Whether the clan is open (anyone can join)
     */
    boolean isOpen();

    /**
     * Sets open/closed status.
     */
    void setOpen(boolean open);

    /**
     * @return The clan's calculated score
     */
    double getScore();

    /**
     * Updates the score cache.
     */
    void updateScore(double score);

    /**
     * @return The clan's leaderboard rank
     */
    int getRank();

    /**
     * Updates the rank cache.
     */
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
     * Sets the banner pattern data.
     */
    void setBannerData(@Nullable String bannerData);

    // ── Member Management ────────────────────────────────────

    /**
     * Add a member to this clan.
     */
    void addMember(@NotNull UserProfile user);

    /**
     * Remove a member from this clan.
     */
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

    void addAlly(@NotNull ClanProfile clan);

    void removeAlly(@NotNull ClanProfile clan);

    boolean isAlliedWith(@NotNull ClanProfile clan);

    // ── Inventory ────────────────────────────────────────────

    /**
     * @return The inventory contents as a slot-to-item map
     */
    @NotNull Map<Integer, org.bukkit.inventory.ItemStack> getInventoryAsMap();

    // ── Persistence ──────────────────────────────────────────

    /**
     * Marks this clan as dirty and queues it for save.
     */
    void save();

    /**
     * @return true if this clan is marked as closed/deleted
     */
    boolean isClosed();

    /**
     * Sets closed/deleted state.
     */
    void setClosed(boolean closed);

    // ── Utilities ────────────────────────────────────────────

    boolean hasName(@NotNull String name);

    boolean hasTag(@NotNull String tag);

    /**
     * @return The clan's current bank balance
     */
    double getBalance();

    /**
     * Sets the clan's bank balance
     */
    void setBalance(double balance);

    /**
     * Deposits money into the clan bank from the player's account.
     * @return true if deposit succeeded
     */
    boolean deposit(@NotNull org.bukkit.entity.Player player, double amount);

    /**
     * Withdraws money from the clan bank and gives it to the player.
     * @return true if withdrawal succeeded
     */
    boolean withdraw(@NotNull org.bukkit.entity.Player player, double amount);
}
