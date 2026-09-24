package com.shyamstudio.clans.api.service;

import com.shyamstudio.clans.api.extension.ClanMenuAction;
import com.shyamstudio.clans.api.extension.ClanSubcommand;
import com.shyamstudio.clans.api.extension.ExtensionRegistration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/** Supported registration surface for addon command and menu integration. */
public interface ClansExtensionService {

    /**
     * Requests Clans to open its main menu for a player.
     *
     * <p>The main menu matches {@code /clan}: members are taken to their clan details,
     * while players without a clan are taken to the clan list. Clans performs the
     * inventory operation on the player's owning thread. A {@code true} return value
     * means the request was accepted for scheduling, not that the inventory is already
     * visible when this method returns.</p>
     *
     * <p>The default implementation keeps addons binary-compatible with older Clans
     * runtimes and reports that menu opening is unsupported.</p>
     *
     * @return {@code true} when Clans accepted the request; {@code false} when the
     * runtime is unavailable, the player cannot use the menu, or the implementation
     * does not support reverse menu navigation
     */
    default boolean openClanMenu(@NotNull Player player) {
        return false;
    }

    /**
     * Registers one owner-scoped {@code /clan} subcommand.
     *
     * @throws IllegalArgumentException when a name is invalid or collides
     * @throws IllegalStateException when the owner is disabled
     */
    @NotNull ExtensionRegistration registerSubcommand(@NotNull Plugin owner,
                                                       @NotNull ClanSubcommand subcommand);

    /**
     * Registers one owner-scoped menu action identifier.
     *
     * @throws IllegalArgumentException when the identifier is invalid or collides
     * @throws IllegalStateException when the owner is disabled
     */
    @NotNull ExtensionRegistration registerMenuAction(@NotNull Plugin owner,
                                                       @NotNull String actionId,
                                                       @NotNull ClanMenuAction action);
}
