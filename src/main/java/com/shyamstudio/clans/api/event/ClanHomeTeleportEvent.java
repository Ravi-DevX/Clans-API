package com.shyamstudio.clans.api.event;

import com.shyamstudio.clans.api.model.ClanProfile;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Event called when a player is teleporting to their clan's home location.
 */
public class ClanHomeTeleportEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Player player;
    private final ClanProfile clan;
    private Location homeLocation;
    private boolean cancelled = false;

    public ClanHomeTeleportEvent(@NotNull Player player, @NotNull ClanProfile clan, @NotNull Location homeLocation) {
        this.player = player;
        this.clan = clan;
        this.homeLocation = homeLocation;
    }

    public @NotNull Player getPlayer() {
        return player;
    }

    public @NotNull ClanProfile getClan() {
        return clan;
    }

    public @NotNull Location getHomeLocation() {
        return homeLocation;
    }

    /**
     * Replaces the destination used by the pending teleport.
     */
    public void setHomeLocation(@NotNull Location homeLocation) {
        this.homeLocation = java.util.Objects.requireNonNull(homeLocation, "homeLocation");
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
}
