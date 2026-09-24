package com.shyamstudio.clans.api.extension;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

/** Immutable input for an addon-owned {@code /clan} subcommand invocation. */
public record ClanCommandContext(@NotNull Player player,
                                 @NotNull String label,
                                 @NotNull String subcommand,
                                 @NotNull List<String> arguments) {

    public ClanCommandContext {
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(label, "label");
        Objects.requireNonNull(subcommand, "subcommand");
        arguments = List.copyOf(Objects.requireNonNull(arguments, "arguments"));
    }
}
