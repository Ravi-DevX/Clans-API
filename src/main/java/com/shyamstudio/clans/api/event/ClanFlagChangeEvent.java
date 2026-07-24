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
 * Fired before a clan's serialized banner or flag data is changed or cleared.
 */
public class ClanFlagChangeEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final CommandSender actor;
    private final ClanProfile clan;
    private final @Nullable String oldFlagData;
    private @Nullable String newFlagData;
    private boolean cancelled;

    public ClanFlagChangeEvent(@NotNull CommandSender actor,
                               @NotNull ClanProfile clan,
                               @Nullable String oldFlagData,
                               @Nullable String newFlagData) {
        this(actor, clan, oldFlagData, newFlagData, !Bukkit.isPrimaryThread());
    }

    public ClanFlagChangeEvent(@NotNull CommandSender actor,
                               @NotNull ClanProfile clan,
                               @Nullable String oldFlagData,
                               @Nullable String newFlagData,
                               boolean asynchronous) {
        super(asynchronous);
        this.actor = Objects.requireNonNull(actor, "actor");
        this.clan = Objects.requireNonNull(clan, "clan");
        this.oldFlagData = oldFlagData;
        this.newFlagData = newFlagData;
    }

    public @NotNull CommandSender getActor() {
        return actor;
    }

    public @NotNull ClanProfile getClan() {
        return clan;
    }

    public @Nullable String getOldFlagData() {
        return oldFlagData;
    }

    public @Nullable String getNewFlagData() {
        return newFlagData;
    }

    public void setNewFlagData(@Nullable String newFlagData) {
        this.newFlagData = newFlagData;
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
