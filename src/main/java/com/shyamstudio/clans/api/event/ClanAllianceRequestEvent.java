package com.shyamstudio.clans.api.event;

import com.shyamstudio.clans.api.model.ClanAllianceRequest;
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

/**
 * Fired before an alliance request lifecycle action is applied.
 * The actor may be {@code null} when a request expires automatically.
 */
public class ClanAllianceRequestEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final @Nullable Player actor;
    private final ClanProfile sourceClan;
    private final ClanProfile targetClan;
    private final ClanAllianceRequest request;
    private final PendingAction action;
    private boolean cancelled;

    public ClanAllianceRequestEvent(@Nullable Player actor,
                                    @NotNull ClanProfile sourceClan,
                                    @NotNull ClanProfile targetClan,
                                    @NotNull ClanAllianceRequest request,
                                    @NotNull PendingAction action) {
        this(actor, sourceClan, targetClan, request, action, !Bukkit.isPrimaryThread());
    }

    public ClanAllianceRequestEvent(@Nullable Player actor,
                                    @NotNull ClanProfile sourceClan,
                                    @NotNull ClanProfile targetClan,
                                    @NotNull ClanAllianceRequest request,
                                    @NotNull PendingAction action,
                                    boolean asynchronous) {
        super(asynchronous);
        this.actor = actor;
        this.sourceClan = Objects.requireNonNull(sourceClan, "sourceClan");
        this.targetClan = Objects.requireNonNull(targetClan, "targetClan");
        this.request = Objects.requireNonNull(request, "request");
        this.action = Objects.requireNonNull(action, "action");
    }

    public @Nullable Player getActor() {
        return actor;
    }

    public @NotNull ClanProfile getSourceClan() {
        return sourceClan;
    }

    public @NotNull ClanProfile getTargetClan() {
        return targetClan;
    }

    public @NotNull ClanAllianceRequest getRequest() {
        return request;
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
