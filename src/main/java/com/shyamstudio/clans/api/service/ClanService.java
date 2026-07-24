package com.shyamstudio.clans.api.service;

import com.shyamstudio.clans.api.result.OperationResult;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Validated clan mutations. These operations apply the same feature, permission,
 * limit, persistence, and event rules as the built-in commands.
 */
public interface ClanService {

    @NotNull OperationResult inviteMember(@NotNull Player actor, @NotNull UUID targetPlayerUuid);

    @NotNull OperationResult respondToInvite(@NotNull Player player, @NotNull UUID clanId, boolean accept);

    @NotNull OperationResult requestToJoin(@NotNull Player player, @NotNull UUID clanId);

    @NotNull OperationResult respondToJoinRequest(@NotNull Player actor,
                                                  @NotNull UUID requesterUuid,
                                                  boolean accept);

    @NotNull OperationResult requestAlliance(@NotNull Player actor, @NotNull UUID targetClanId);

    @NotNull OperationResult respondToAllianceRequest(@NotNull Player actor,
                                                       @NotNull UUID sourceClanId,
                                                       boolean accept);

    @NotNull OperationResult cancelAllianceRequest(@NotNull Player actor, @NotNull UUID targetClanId);

    @NotNull OperationResult removeAlliance(@NotNull Player actor, @NotNull UUID targetClanId);

    @NotNull OperationResult leaveClan(@NotNull Player player);

    @NotNull OperationResult kickMember(@NotNull Player actor, @NotNull UUID targetPlayerUuid);

    @NotNull OperationResult changeRole(@NotNull Player actor,
                                        @NotNull UUID targetPlayerUuid,
                                        @NotNull String roleName);

    @NotNull OperationResult transferOwnership(@NotNull Player actor, @NotNull UUID targetPlayerUuid);

    @NotNull OperationResult renameClan(@NotNull CommandSender actor,
                                        @NotNull UUID clanId,
                                        @NotNull String newName);

    @NotNull OperationResult setOpen(@NotNull Player actor, boolean open);

    @NotNull OperationResult setFriendlyFire(@NotNull Player actor, boolean enabled);

    @NotNull OperationResult setDescription(@NotNull Player actor, @Nullable String description);

    @NotNull OperationResult setHome(@NotNull Player actor, @Nullable Location location);

    @NotNull OperationResult setSpawn(@NotNull Player actor, @Nullable Location location);

    @NotNull OperationResult setBannerData(@NotNull Player actor, @Nullable String bannerData);

    @NotNull OperationResult disband(@NotNull Player actor);

    @NotNull OperationResult deposit(@NotNull Player actor, double amount);

    @NotNull OperationResult withdraw(@NotNull Player actor, double amount);
}
