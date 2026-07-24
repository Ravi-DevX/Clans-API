package com.shyamstudio.clans.api.model;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/** Immutable snapshot of a player's request to join a clan. */
public record ClanJoinRequest(@NotNull UUID clanId,
                              @NotNull UUID playerUuid,
                              long createdAt,
                              long expiresAt) {
}
