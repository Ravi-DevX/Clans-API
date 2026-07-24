package com.shyamstudio.clans.api.model;

import com.shyamstudio.clans.api.ClansAPI;
import com.shyamstudio.clans.api.option.ClanPrivilege;
import org.jetbrains.annotations.NotNull;

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
     * Sets the cached username directly.
     *
     * @deprecated Usernames are maintained by the plugin. Use validated operations
     *             exposed through {@link ClansAPI#getClanService()} instead of
     *             mutating model state.
     */
    @Deprecated(since = "1.1.0")
    void setUsername(@NotNull String username);

    /**
     * @return The member's role within the clan
     */
    @NotNull RoleProfile getRole();

    /**
     * Sets the member's role directly.
     *
     * @deprecated Use {@link ClansAPI#getClanService()} so hierarchy, permission,
     *             event, and persistence rules are applied.
     */
    @Deprecated(since = "1.1.0")
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
