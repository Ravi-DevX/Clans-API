package com.shyamstudio.clans.api.service;

import com.shyamstudio.clans.api.option.ChatChannel;
import com.shyamstudio.clans.api.option.ChatModeChangeCause;
import com.shyamstudio.clans.api.result.OperationResult;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/** Controlled access to clan and ally chat state and delivery. */
public interface ChatService {

    /** Returns the effective channel. Disabled channels are reported as {@link ChatChannel#PUBLIC}. */
    @NotNull ChatChannel getChannel(@NotNull UUID playerUuid);

    default @NotNull OperationResult setChannel(@NotNull Player player,
                                                 @NotNull ChatChannel channel) {
        return setChannel(player, channel, ChatModeChangeCause.API);
    }

    @NotNull OperationResult setChannel(@NotNull Player player,
                                        @NotNull ChatChannel channel,
                                        @NotNull ChatModeChangeCause cause);

    /** Sends an unformatted message through CLAN or ALLY chat. PUBLIC is not accepted. */
    @NotNull OperationResult sendMessage(@NotNull Player sender,
                                         @NotNull ChatChannel channel,
                                         @NotNull String message);
}
