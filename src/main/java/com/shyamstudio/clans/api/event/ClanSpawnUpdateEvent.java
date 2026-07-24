package com.shyamstudio.clans.api.event;

import com.shyamstudio.clans.api.model.ClanProfile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Fired before a clan spawn is set or deleted.
 */
public class ClanSpawnUpdateEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    public enum Action {
        SET,
        DELETE
    }

    private final ClanProfile clan;
    private final @Nullable Location oldLocation;
    private final CommandSender actor;
    private @Nullable Location newLocation;
    private Action action;
    private boolean cancelled;

    public ClanSpawnUpdateEvent(@NotNull ClanProfile clan,
                                @Nullable Location oldLocation,
                                @Nullable Location newLocation,
                                @NotNull Action action,
                                @NotNull CommandSender actor) {
        this(clan, oldLocation, newLocation, action, actor, !Bukkit.isPrimaryThread());
    }

    public ClanSpawnUpdateEvent(@NotNull ClanProfile clan,
                                @Nullable Location oldLocation,
                                @Nullable Location newLocation,
                                @NotNull Action action,
                                @NotNull CommandSender actor,
                                boolean asynchronous) {
        super(asynchronous);
        this.clan = Objects.requireNonNull(clan, "clan");
        this.oldLocation = oldLocation;
        this.newLocation = newLocation;
        this.action = Objects.requireNonNull(action, "action");
        this.actor = Objects.requireNonNull(actor, "actor");
    }

    public @NotNull ClanProfile getClan() {
        return clan;
    }

    public @Nullable Location getOldLocation() {
        return oldLocation;
    }

    public @Nullable Location getNewLocation() {
        return newLocation;
    }

    public void setNewLocation(@Nullable Location newLocation) {
        this.newLocation = newLocation;
    }

    public @NotNull Action getAction() {
        return action;
    }

    public void setAction(@NotNull Action action) {
        this.action = Objects.requireNonNull(action, "action");
    }

    public @NotNull CommandSender getActor() {
        return actor;
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
