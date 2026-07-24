package com.shyamstudio.clans.api.event;

import com.shyamstudio.clans.api.model.ClanProfile;
import com.shyamstudio.clans.api.option.ChatChannel;
import com.shyamstudio.clans.api.option.ChatMessageSource;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Fired before a message is delivered to members of the sender's clan.
 * <p>
 * Listeners may cancel delivery, replace the plain-text message, or modify the
 * mutable recipient set. The event may be asynchronous; listeners must check
 * {@link #isAsynchronous()} before accessing thread-confined Bukkit state.
 */
public class ClanChatMessageEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Player player;
    private final ClanProfile clan;
    private final ChatMessageSource source;
    private final Set<UUID> recipients;
    private String message;
    private boolean cancelled;

    public ClanChatMessageEvent(@NotNull Player player,
                                @NotNull ClanProfile clan,
                                @NotNull String message,
                                @NotNull ChatMessageSource source,
                                @NotNull Set<UUID> recipients) {
        this(player, clan, message, source, recipients, !Bukkit.isPrimaryThread());
    }

    public ClanChatMessageEvent(@NotNull Player player,
                                @NotNull ClanProfile clan,
                                @NotNull String message,
                                @NotNull ChatMessageSource source,
                                @NotNull Set<UUID> recipients,
                                boolean asynchronous) {
        super(asynchronous);
        this.player = Objects.requireNonNull(player, "player");
        this.clan = Objects.requireNonNull(clan, "clan");
        this.message = requireMessage(message);
        this.source = Objects.requireNonNull(source, "source");
        this.recipients = copyRecipients(recipients);
    }

    public @NotNull Player getPlayer() {
        return player;
    }

    public @NotNull ClanProfile getClan() {
        return clan;
    }

    public @NotNull ChatChannel getChannel() {
        return ChatChannel.CLAN;
    }

    public @NotNull ChatMessageSource getSource() {
        return source;
    }

    public @NotNull String getMessage() {
        return message;
    }

    /**
     * Replaces the message that will be formatted and delivered.
     *
     * @param message non-blank plain-text message
     * @throws IllegalArgumentException when the message is blank
     */
    public void setMessage(@NotNull String message) {
        this.message = requireMessage(message);
    }

    /**
     * Returns the mutable recipient UUID set used for delivery.
     */
    public @NotNull Set<UUID> getRecipients() {
        return recipients;
    }

    /**
     * Replaces the recipient set using a defensive copy.
     */
    public void setRecipients(@NotNull Set<UUID> recipients) {
        Set<UUID> copy = copyRecipients(recipients);
        this.recipients.clear();
        this.recipients.addAll(copy);
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

    private static @NotNull String requireMessage(@NotNull String message) {
        Objects.requireNonNull(message, "message");
        if (message.isBlank()) {
            throw new IllegalArgumentException("message must not be blank");
        }
        return message;
    }

    private static @NotNull Set<UUID> copyRecipients(@NotNull Set<UUID> recipients) {
        Objects.requireNonNull(recipients, "recipients");
        LinkedHashSet<UUID> copy = new LinkedHashSet<>(recipients.size());
        for (UUID recipient : recipients) {
            copy.add(Objects.requireNonNull(recipient, "recipients must not contain null"));
        }
        return copy;
    }
}
