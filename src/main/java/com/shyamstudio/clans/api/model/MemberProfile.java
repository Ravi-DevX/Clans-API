package com.shyamstudio.clans.api.model;

import com.shyamstudio.clans.api.option.ClanPrivilege;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Represents a member of a clan (non-owner).
 */
public interface MemberProfile {

    /**
     * @return The member's UUID
     */
    @NotNull UUID getUuid();

    /**
     * @return The member's cached username
     */
    @NotNull String getUsername();

    /**
     * Sets the cached username (updated on join).
     */
    void setUsername(@NotNull String username);

    /**
     * @return The member's role within the clan
     */
    @NotNull RoleProfile getRole();

    /**
     * Sets the member's role.
     */
    void setRole(@NotNull RoleProfile role);

    /**
     * Checks if this member has a specific permission.
     */
    boolean hasPermission(@NotNull ClanPrivilege permission);

    /**
     * @return true if this member is the clan owner
     */
    default boolean isOwner() {
        return false;
    }
}
