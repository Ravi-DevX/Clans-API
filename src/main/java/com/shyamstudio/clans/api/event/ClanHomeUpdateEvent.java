package com.shyamstudio.clans.api.event;

import com.shyamstudio.clans.api.model.ClanProfile;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Event called when a clan's home location is updated or deleted.
 */
public class ClanHomeUpdateEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    public enum Action {
        SET,
        DELETE
    }

    private final ClanProfile clan;
    private final @Nullable Location oldLocation;
    private final @Nullable Location newLocation;
    private final Action action;
    private final CommandSender sender;
    private boolean cancelled = false;

    public ClanHomeUpdateEvent(@NotNull ClanProfile clan, @Nullable Location oldLocation, @Nullable Location newLocation, @NotNull Action action, @NotNull CommandSender sender) {
        this.clan = clan;
        this.oldLocation = oldLocation;
        this.newLocation = newLocation;
        this.action = action;
        this.sender = sender;
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

    public @NotNull Action getAction() {
        return action;
    }

    public @NotNull CommandSender getSender() {
        return sender;
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
