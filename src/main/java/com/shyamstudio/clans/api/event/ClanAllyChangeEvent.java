package com.shyamstudio.clans.api.event;

import com.shyamstudio.clans.api.model.ClanProfile;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Event called when an alliance between two clans is changing (formed or broken).
 */
public class ClanAllyChangeEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    public enum Action {
        ALLY,
        UNALLY
    }

    private final ClanProfile clanA;
    private final ClanProfile clanB;
    private final Action action;
    private boolean cancelled = false;

    public ClanAllyChangeEvent(@NotNull ClanProfile clanA, @NotNull ClanProfile clanB, @NotNull Action action) {
        this.clanA = clanA;
        this.clanB = clanB;
        this.action = action;
    }

    public @NotNull ClanProfile getClanA() {
        return clanA;
    }

    public @NotNull ClanProfile getClanB() {
        return clanB;
    }

    public @NotNull Action getAction() {
        return action;
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
