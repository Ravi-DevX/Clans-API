package com.shyamstudio.clans.api.event;

import com.shyamstudio.clans.api.option.ChatChannel;
import com.shyamstudio.clans.api.option.ChatModeChangeCause;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Fired before a player's active chat channel changes.
 * <p>
 * Cancelling the event keeps the old channel. Listeners may replace the
 * proposed channel through {@link #setNewChannel(ChatChannel)}.
 */
public class ClanChatModeChangeEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Player player;
    private final ChatChannel oldChannel;
    private final ChatModeChangeCause cause;
    private ChatChannel newChannel;
    private boolean cancelled;

    public ClanChatModeChangeEvent(@NotNull Player player,
                                   @NotNull ChatChannel oldChannel,
                                   @NotNull ChatChannel newChannel,
                                   @NotNull ChatModeChangeCause cause) {
        this(player, oldChannel, newChannel, cause, !Bukkit.isPrimaryThread());
    }

    public ClanChatModeChangeEvent(@NotNull Player player,
                                   @NotNull ChatChannel oldChannel,
                                   @NotNull ChatChannel newChannel,
                                   @NotNull ChatModeChangeCause cause,
                                   boolean asynchronous) {
        super(asynchronous);
        this.player = Objects.requireNonNull(player, "player");
        this.oldChannel = Objects.requireNonNull(oldChannel, "oldChannel");
        this.newChannel = Objects.requireNonNull(newChannel, "newChannel");
        this.cause = Objects.requireNonNull(cause, "cause");
    }

    public @NotNull Player getPlayer() {
        return player;
    }

    public @NotNull ChatChannel getOldChannel() {
        return oldChannel;
    }

    public @NotNull ChatChannel getNewChannel() {
        return newChannel;
    }

    public void setNewChannel(@NotNull ChatChannel newChannel) {
        this.newChannel = Objects.requireNonNull(newChannel, "newChannel");
    }

    public @NotNull ChatModeChangeCause getCause() {
        return cause;
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
