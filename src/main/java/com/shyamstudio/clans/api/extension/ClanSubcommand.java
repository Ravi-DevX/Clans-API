package com.shyamstudio.clans.api.extension;

import com.shyamstudio.clans.api.result.OperationResult;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

/** Public contract for one addon-owned {@code /clan} subcommand. */
public interface ClanSubcommand {

    /** @return the primary lowercase command name */
    @NotNull String name();

    /** @return optional aliases; names are matched case-insensitively */
    default @NotNull Set<String> aliases() {
        return Set.of();
    }

    /** @return a Bukkit permission, or an empty string when none is required */
    default @NotNull String permission() {
        return "";
    }

    /** Executes the command and returns a stable, non-localized outcome. */
    @NotNull OperationResult execute(@NotNull ClanCommandContext context);

    /**
     * Supplies cache-only, non-blocking suggestions for the current arguments.
     * Implementations must not perform storage or network calls here.
     */
    default @NotNull List<String> suggest(@NotNull ClanCommandContext context) {
        return List.of();
    }
}
