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
 * Fired before a clan's friendly-fire state changes.
 */
public class ClanFriendlyFireChangeEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final CommandSender actor;
    private final ClanProfile clan;
    private final boolean oldEnabled;
    private boolean newEnabled;
    private boolean cancelled;

    public ClanFriendlyFireChangeEvent(@NotNull CommandSender actor,
                                       @NotNull ClanProfile clan,
                                       boolean oldEnabled,
                                       boolean newEnabled) {
        this(actor, clan, oldEnabled, newEnabled, !Bukkit.isPrimaryThread());
    }

    public ClanFriendlyFireChangeEvent(@NotNull CommandSender actor,
                                       @NotNull ClanProfile clan,
                                       boolean oldEnabled,
                                       boolean newEnabled,
                                       boolean asynchronous) {
        super(asynchronous);
        this.actor = Objects.requireNonNull(actor, "actor");
        this.clan = Objects.requireNonNull(clan, "clan");
        this.oldEnabled = oldEnabled;
        this.newEnabled = newEnabled;
    }

    public @NotNull CommandSender getActor() {
        return actor;
    }

    public @NotNull ClanProfile getClan() {
        return clan;
    }

    public boolean wasEnabled() {
        return oldEnabled;
    }

    public boolean isOldEnabled() {
        return oldEnabled;
    }

    public boolean isEnabled() {
        return newEnabled;
    }

    public boolean isNewEnabled() {
        return newEnabled;
    }

    public void setEnabled(boolean enabled) {
        this.newEnabled = enabled;
    }

    public void setNewEnabled(boolean enabled) {
        this.newEnabled = enabled;
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
