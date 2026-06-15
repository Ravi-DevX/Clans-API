package com.shyamstudio.clans.api.model;

import com.shyamstudio.clans.api.option.ClanPrivilege;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Represents a role within a clan hierarchy.
 * Lower priority = higher rank (Leader = -1, then 0, 1, 2...).
 */
public interface RoleProfile {

    /**
     * @return The display name of this role
     */
    @NotNull String getName();

    /**
     * @return The priority of this role (-1 = leader/highest)
     */
    int getPriority();

    /**
     * @return The set of permissions this role has
     */
    @NotNull Set<ClanPrivilege> getPermissions();

    /**
     * Checks if this role has a specific permission.
     */
    boolean hasPermission(@NotNull ClanPrivilege permission);

    /**
     * Checks if this role is higher or equal rank to another.
     * Lower priority number = higher rank.
     */
    default boolean isHigherOrEqual(@NotNull RoleProfile other) {
        return this.getPriority() <= other.getPriority();
    }

    /**
     * Checks if this role is strictly higher rank than another.
     */
    default boolean isHigherThan(@NotNull RoleProfile other) {
        return this.getPriority() < other.getPriority();
    }

    /**
     * @return The symbol of this role (e.g. 👑, ★)
     */
    default @NotNull String getSymbol() {
        return "";
    }

    /**
     * @return The hex color of this role (e.g. &#FFFFFF)
     */
    default @NotNull String getColor() {
        return "&#FFFFFF";
    }

    /**
     * @return The color-formatted display name with the symbol prefix (e.g. &#FFAA00★ Co-Leader)
     */
    default @NotNull String getDisplayName() {
        String sym = getSymbol();
        return getColor() + (sym.isEmpty() ? "" : sym + " ") + getName();
    }
}
