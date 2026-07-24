package com.shyamstudio.clans.api.model;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/** Immutable snapshot of a clan invitation sent to a player. */
public record ClanInvite(@NotNull UUID clanId,
                         @NotNull UUID targetPlayerUuid,
                         long createdAt,
                         long expiresAt) {
}
