package com.shyamstudio.clans.api.model;

import com.shyamstudio.clans.api.ClansAPI;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the owner of a clan. Extends MemberProfile.
 */
public interface LeaderProfile extends MemberProfile {

    @Override
    default boolean isOwner() {
        return true;
    }

    /**
     * Transfer ownership to the specified member.
     * After transfer, this owner becomes a regular member.
     *
     * @param newOwner the member to promote to owner
     * @deprecated Use {@link ClansAPI#getClanService()} so membership, hierarchy,
     *             event, and persistence rules are applied.
     */
    @Deprecated(since = "1.1.0")
    void transferOwnership(@NotNull MemberProfile newOwner);
}
