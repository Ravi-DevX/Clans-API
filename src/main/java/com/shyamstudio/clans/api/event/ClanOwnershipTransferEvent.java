package com.shyamstudio.clans.api.event;

import com.shyamstudio.clans.api.model.ClanProfile;
import com.shyamstudio.clans.api.model.LeaderProfile;
import com.shyamstudio.clans.api.model.MemberProfile;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Fired before ownership of a clan is transferred to another member.
 */
public class ClanOwnershipTransferEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final CommandSender actor;
    private final ClanProfile clan;
    private final LeaderProfile oldOwner;
    private MemberProfile newOwner;
    private boolean cancelled;

    public ClanOwnershipTransferEvent(@NotNull CommandSender actor,
                                      @NotNull ClanProfile clan,
                                      @NotNull LeaderProfile oldOwner,
                                      @NotNull MemberProfile newOwner) {
        this(actor, clan, oldOwner, newOwner, !Bukkit.isPrimaryThread());
    }

    public ClanOwnershipTransferEvent(@NotNull CommandSender actor,
                                      @NotNull ClanProfile clan,
                                      @NotNull LeaderProfile oldOwner,
                                      @NotNull MemberProfile newOwner,
                                      boolean asynchronous) {
        super(asynchronous);
        this.actor = Objects.requireNonNull(actor, "actor");
        this.clan = Objects.requireNonNull(clan, "clan");
        this.oldOwner = Objects.requireNonNull(oldOwner, "oldOwner");
        this.newOwner = Objects.requireNonNull(newOwner, "newOwner");
    }

    public @NotNull CommandSender getActor() {
        return actor;
    }

    public @NotNull ClanProfile getClan() {
        return clan;
    }

    public @NotNull LeaderProfile getOldOwner() {
        return oldOwner;
    }

    public @NotNull MemberProfile getNewOwner() {
        return newOwner;
    }

    public void setNewOwner(@NotNull MemberProfile newOwner) {
        this.newOwner = Objects.requireNonNull(newOwner, "newOwner");
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
