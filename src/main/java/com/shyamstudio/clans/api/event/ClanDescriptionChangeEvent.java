package com.shyamstudio.clans.api.event;

import com.shyamstudio.clans.api.model.ClanProfile;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Fired before a clan description is changed or cleared.
 */
public class ClanDescriptionChangeEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final CommandSender actor;
    private final ClanProfile clan;
    private final @Nullable String oldDescription;
    private @Nullable String newDescription;
    private boolean cancelled;

    public ClanDescriptionChangeEvent(@NotNull CommandSender actor,
                                      @NotNull ClanProfile clan,
                                      @Nullable String oldDescription,
                                      @Nullable String newDescription) {
        this(actor, clan, oldDescription, newDescription, !Bukkit.isPrimaryThread());
    }

    public ClanDescriptionChangeEvent(@NotNull CommandSender actor,
                                      @NotNull ClanProfile clan,
                                      @Nullable String oldDescription,
                                      @Nullable String newDescription,
                                      boolean asynchronous) {
        super(asynchronous);
        this.actor = Objects.requireNonNull(actor, "actor");
        this.clan = Objects.requireNonNull(clan, "clan");
        this.oldDescription = oldDescription;
        this.newDescription = newDescription;
    }

    public @NotNull CommandSender getActor() {
        return actor;
    }

    public @NotNull ClanProfile getClan() {
        return clan;
    }

    public @Nullable String getOldDescription() {
        return oldDescription;
    }

    public @Nullable String getNewDescription() {
        return newDescription;
    }

    public void setNewDescription(@Nullable String newDescription) {
        this.newDescription = newDescription;
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
