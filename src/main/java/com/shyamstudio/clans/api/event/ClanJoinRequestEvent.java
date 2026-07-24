package com.shyamstudio.clans.api.event;

import com.shyamstudio.clans.api.model.ClanJoinRequest;
import com.shyamstudio.clans.api.model.ClanProfile;
import com.shyamstudio.clans.api.option.PendingAction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;

/**
 * Fired before a clan join request lifecycle action is applied.
 * The actor may be {@code null} when a request expires automatically.
 */
public class ClanJoinRequestEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final @Nullable Player actor;
    private final ClanProfile clan;
    private final ClanJoinRequest request;
    private final PendingAction action;
    private boolean cancelled;

    public ClanJoinRequestEvent(@Nullable Player actor,
                                @NotNull ClanProfile clan,
                                @NotNull ClanJoinRequest request,
                                @NotNull PendingAction action) {
        this(actor, clan, request, action, !Bukkit.isPrimaryThread());
    }

    public ClanJoinRequestEvent(@Nullable Player actor,
                                @NotNull ClanProfile clan,
                                @NotNull ClanJoinRequest request,
                                @NotNull PendingAction action,
                                boolean asynchronous) {
        super(asynchronous);
        this.actor = actor;
        this.clan = Objects.requireNonNull(clan, "clan");
        this.request = Objects.requireNonNull(request, "request");
        this.action = Objects.requireNonNull(action, "action");
    }

    public @Nullable Player getActor() {
        return actor;
    }

    public @NotNull ClanProfile getClan() {
        return clan;
    }

    public @NotNull ClanJoinRequest getRequest() {
        return request;
    }

    public @NotNull UUID getRequesterUuid() {
        return request.playerUuid();
    }

    public @NotNull PendingAction getAction() {
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
