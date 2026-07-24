package com.shyamstudio.clans.api.event;

import com.shyamstudio.clans.api.model.ClanInvite;
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
 * Fired before a clan invitation lifecycle action is applied.
 * The actor may be {@code null} when an invitation expires automatically.
 */
public class ClanInviteEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final @Nullable Player actor;
    private final ClanProfile clan;
    private final ClanInvite invite;
    private final PendingAction action;
    private boolean cancelled;

    public ClanInviteEvent(@Nullable Player actor,
                           @NotNull ClanProfile clan,
                           @NotNull ClanInvite invite,
                           @NotNull PendingAction action) {
        this(actor, clan, invite, action, !Bukkit.isPrimaryThread());
    }

    public ClanInviteEvent(@Nullable Player actor,
                           @NotNull ClanProfile clan,
                           @NotNull ClanInvite invite,
                           @NotNull PendingAction action,
                           boolean asynchronous) {
        super(asynchronous);
        this.actor = actor;
        this.clan = Objects.requireNonNull(clan, "clan");
        this.invite = Objects.requireNonNull(invite, "invite");
        this.action = Objects.requireNonNull(action, "action");
    }

    public @Nullable Player getActor() {
        return actor;
    }

    public @NotNull ClanProfile getClan() {
        return clan;
    }

    public @NotNull ClanInvite getInvite() {
        return invite;
    }

    public @NotNull UUID getTargetPlayerUuid() {
        return invite.targetPlayerUuid();
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
