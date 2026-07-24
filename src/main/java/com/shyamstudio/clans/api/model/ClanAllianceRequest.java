package com.shyamstudio.clans.api.model;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/** Immutable snapshot of a pending alliance request. */
public record ClanAllianceRequest(@NotNull UUID sourceClanId,
                                  @NotNull UUID targetClanId,
                                  long createdAt,
                                  long expiresAt) {
}
