package com.shyamstudio.clans.api.event;

import com.shyamstudio.clans.api.ClansAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/** Fired once after the Clans runtime is fully initialized and ready for gameplay calls. */
public final class ClansReadyEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private final ClansAPI api;

    public ClansReadyEvent(@NotNull ClansAPI api) {
        super(!Bukkit.isPrimaryThread());
        this.api = Objects.requireNonNull(api, "api");
    }

    public @NotNull ClansAPI getApi() {
        return api;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static @NotNull HandlerList getHandlerList() {
        return HANDLERS;
    }
}
