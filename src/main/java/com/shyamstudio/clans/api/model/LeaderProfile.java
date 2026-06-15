package com.shyamstudio.clans.api.model;

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
     */
    void transferOwnership(@NotNull MemberProfile newOwner);
}
