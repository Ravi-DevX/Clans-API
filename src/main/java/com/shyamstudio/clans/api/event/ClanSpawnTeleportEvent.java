package com.shyamstudio.clans.api.event;

import com.shyamstudio.clans.api.model.ClanProfile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Fired before a player teleports to a clan spawn.
 */
public class ClanSpawnTeleportEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Player player;
    private final ClanProfile clan;
    private Location destination;
    private boolean cancelled;

    public ClanSpawnTeleportEvent(@NotNull Player player,
                                  @NotNull ClanProfile clan,
                                  @NotNull Location destination) {
        this(player, clan, destination, !Bukkit.isPrimaryThread());
    }

    public ClanSpawnTeleportEvent(@NotNull Player player,
                                  @NotNull ClanProfile clan,
                                  @NotNull Location destination,
                                  boolean asynchronous) {
        super(asynchronous);
        this.player = Objects.requireNonNull(player, "player");
        this.clan = Objects.requireNonNull(clan, "clan");
        this.destination = Objects.requireNonNull(destination, "destination");
    }

    public @NotNull Player getPlayer() {
        return player;
    }

    public @NotNull ClanProfile getClan() {
        return clan;
    }

    public @NotNull Location getDestination() {
        return destination;
    }

    public void setDestination(@NotNull Location destination) {
        this.destination = Objects.requireNonNull(destination, "destination");
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
