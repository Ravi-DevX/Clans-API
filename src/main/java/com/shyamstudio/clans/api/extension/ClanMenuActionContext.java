package com.shyamstudio.clans.api.extension;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/** Immutable input for a registered action dispatched by a Clans menu. */
public record ClanMenuActionContext(@NotNull Player player,
                                    @NotNull String menuId,
                                    @Nullable UUID clanId,
                                    @NotNull Map<String, String> attributes) {

    public ClanMenuActionContext {
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(menuId, "menuId");
        attributes = Map.copyOf(Objects.requireNonNull(attributes, "attributes"));
    }
}
