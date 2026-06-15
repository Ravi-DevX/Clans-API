package com.shyamstudio.clans.api.event;

import com.shyamstudio.clans.api.model.ClanProfile;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Fired when a player leaves a clan (kick, leave, or disband).
 */
public class PlayerLeaveClanEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    private final UUID playerUuid;
    private final String playerName;
    private final ClanProfile clan;
    private final Reason reason;
    private boolean cancelled;

    public PlayerLeaveClanEvent(@NotNull UUID playerUuid, @NotNull String playerName,
                                @NotNull ClanProfile clan, @NotNull Reason reason) {
        this.playerUuid = playerUuid;
        this.playerName = playerName;
        this.clan = clan;
        this.reason = reason;
        this.cancelled = false;
    }

    public @NotNull UUID getPlayerUuid() {
        return playerUuid;
    }

    public @NotNull String getPlayerName() {
        return playerName;
    }

    public @NotNull ClanProfile getClan() {
        return clan;
    }

    public @NotNull Reason getReason() {
        return reason;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static @NotNull HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    public enum Reason {
        LEAVE,
        KICKED,
        CLAN_DISBANDED
    }
}
