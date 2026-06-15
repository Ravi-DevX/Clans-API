package com.shyamstudio.clans.api.event;

import com.shyamstudio.clans.api.model.ClanProfile;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Event called when a clan is being renamed (tag or name changed).
 */
public class ClanRenameEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    
    private final ClanProfile clan;
    private final String oldTag;
    private String newTag;
    private final String oldName;
    private String newName;
    private final boolean tagChange;
    private final @Nullable CommandSender sender;
    private boolean cancelled = false;

    public ClanRenameEvent(@NotNull ClanProfile clan, @NotNull String oldTag, @NotNull String newTag, @NotNull String oldName, @NotNull String newName, boolean tagChange, @Nullable CommandSender sender) {
        this.clan = clan;
        this.oldTag = oldTag;
        this.newTag = newTag;
        this.oldName = oldName;
        this.newName = newName;
        this.tagChange = tagChange;
        this.sender = sender;
    }

    @Deprecated
    public ClanRenameEvent(@NotNull ClanProfile clan, @NotNull String oldTag, @NotNull String newTag, @Nullable CommandSender sender) {
        this(clan, oldTag, newTag, clan.getDisplayName(), clan.getDisplayName(), true, sender);
    }

    public @NotNull ClanProfile getClan() {
        return clan;
    }

    public @NotNull String getOldTag() {
        return oldTag;
    }

    public @NotNull String getNewTag() {
        return newTag;
    }

    public void setNewTag(@NotNull String newTag) {
        this.newTag = newTag;
    }

    public @NotNull String getOldName() {
        return oldName;
    }

    public @NotNull String getNewName() {
        return newName;
    }

    public void setNewName(@NotNull String newName) {
        this.newName = newName;
    }

    public boolean isTagChange() {
        return tagChange;
    }

    public boolean isNameChange() {
        return !tagChange;
    }

    public @Nullable CommandSender getSender() {
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
