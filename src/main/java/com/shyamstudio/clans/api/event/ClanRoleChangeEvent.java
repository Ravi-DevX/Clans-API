package com.shyamstudio.clans.api.event;

import com.shyamstudio.clans.api.model.ClanProfile;
import com.shyamstudio.clans.api.model.MemberProfile;
import com.shyamstudio.clans.api.model.RoleProfile;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Event called when a member's clan role is changed (e.g., promoted or demoted).
 */
public class ClanRoleChangeEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final ClanProfile clan;
    private final MemberProfile member;
    private final RoleProfile oldRole;
    private RoleProfile newRole;
    private final CommandSender sender;
    private boolean cancelled = false;

    public ClanRoleChangeEvent(@NotNull ClanProfile clan, @NotNull MemberProfile member, @NotNull RoleProfile oldRole, @NotNull RoleProfile newRole, @NotNull CommandSender sender) {
        this.clan = clan;
        this.member = member;
        this.oldRole = oldRole;
        this.newRole = newRole;
        this.sender = sender;
    }

    public @NotNull ClanProfile getClan() {
        return clan;
    }

    public @NotNull MemberProfile getMember() {
        return member;
    }

    public @NotNull RoleProfile getOldRole() {
        return oldRole;
    }

    public @NotNull RoleProfile getNewRole() {
        return newRole;
    }

    public void setNewRole(@NotNull RoleProfile newRole) {
        this.newRole = newRole;
    }

    public @NotNull CommandSender getSender() {
        return sender;
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
