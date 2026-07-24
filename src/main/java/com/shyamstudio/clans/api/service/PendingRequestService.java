package com.shyamstudio.clans.api.service;

import com.shyamstudio.clans.api.model.ClanAllianceRequest;
import com.shyamstudio.clans.api.model.ClanInvite;
import com.shyamstudio.clans.api.model.ClanJoinRequest;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.UUID;

/** Read-only snapshots of pending clan invitations and requests. */
public interface PendingRequestService {

    @NotNull Collection<ClanInvite> getInvitesForPlayer(@NotNull UUID playerUuid);

    @NotNull Collection<ClanJoinRequest> getJoinRequestsForClan(@NotNull UUID clanId);

    @NotNull Collection<ClanJoinRequest> getJoinRequestsByPlayer(@NotNull UUID playerUuid);

    @NotNull Collection<ClanAllianceRequest> getAllianceRequestsForClan(@NotNull UUID clanId);

    @NotNull Collection<ClanAllianceRequest> getAllianceRequestsFromClan(@NotNull UUID clanId);
}
