package com.shyamstudio.clans.api.event;

import com.shyamstudio.clans.api.model.ClanProfile;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Fired before a clan's open-join state changes.
 */
public class ClanOpenChangeEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final CommandSender actor;
    private final ClanProfile clan;
    private final boolean oldOpen;
    private boolean newOpen;
    private boolean cancelled;

    public ClanOpenChangeEvent(@NotNull CommandSender actor,
                               @NotNull ClanProfile clan,
                               boolean oldOpen,
                               boolean newOpen) {
        this(actor, clan, oldOpen, newOpen, !Bukkit.isPrimaryThread());
    }

    public ClanOpenChangeEvent(@NotNull CommandSender actor,
                               @NotNull ClanProfile clan,
                               boolean oldOpen,
                               boolean newOpen,
                               boolean asynchronous) {
        super(asynchronous);
        this.actor = Objects.requireNonNull(actor, "actor");
        this.clan = Objects.requireNonNull(clan, "clan");
        this.oldOpen = oldOpen;
        this.newOpen = newOpen;
    }

    public @NotNull CommandSender getActor() {
        return actor;
    }

    public @NotNull ClanProfile getClan() {
        return clan;
    }

    public boolean isOldOpen() {
        return oldOpen;
    }

    public boolean isNewOpen() {
        return newOpen;
    }

    public void setNewOpen(boolean newOpen) {
        this.newOpen = newOpen;
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
