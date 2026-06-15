package com.shyamstudio.clans.api.event;

import com.shyamstudio.clans.api.model.ClanProfile;
import com.shyamstudio.clans.api.model.UserProfile;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Fired when a player joins a clan.
 */
public class PlayerJoinClanEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    private final UserProfile user;
    private final ClanProfile clan;
    private boolean cancelled;

    public PlayerJoinClanEvent(@NotNull UserProfile user, @NotNull ClanProfile clan) {
        this.user = user;
        this.clan = clan;
        this.cancelled = false;
    }

    public @NotNull UserProfile getUser() {
        return user;
    }

    public @NotNull ClanProfile getClan() {
        return clan;
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
