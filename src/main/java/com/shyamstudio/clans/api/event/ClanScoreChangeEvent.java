package com.shyamstudio.clans.api.event;

import com.shyamstudio.clans.api.model.ClanProfile;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Notification fired after a clan's calculated score changes.
 */
public class ClanScoreChangeEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final ClanProfile clan;
    private final double oldScore;
    private final double newScore;

    public ClanScoreChangeEvent(@NotNull ClanProfile clan, double oldScore, double newScore) {
        this(clan, oldScore, newScore, !Bukkit.isPrimaryThread());
    }

    public ClanScoreChangeEvent(@NotNull ClanProfile clan,
                                double oldScore,
                                double newScore,
                                boolean asynchronous) {
        super(asynchronous);
        this.clan = Objects.requireNonNull(clan, "clan");
        this.oldScore = oldScore;
        this.newScore = newScore;
    }

    public @NotNull ClanProfile getClan() {
        return clan;
    }

    public double getOldScore() {
        return oldScore;
    }

    public double getNewScore() {
        return newScore;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static @NotNull HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
