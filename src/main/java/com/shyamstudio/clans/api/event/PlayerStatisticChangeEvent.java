package com.shyamstudio.clans.api.event;

import com.shyamstudio.clans.api.model.UserProfile;
import com.shyamstudio.clans.api.option.ProfileMetric;
import com.shyamstudio.clans.api.option.StatisticChangeCause;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;

/**
 * Fired before a tracked player statistic changes.
 */
public class PlayerStatisticChangeEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final UserProfile user;
    private final ProfileMetric statistic;
    private final int oldValue;
    private final StatisticChangeCause cause;
    private final @Nullable CommandSender actor;
    private int newValue;
    private boolean cancelled;

    public PlayerStatisticChangeEvent(@NotNull UserProfile user,
                                      @NotNull ProfileMetric statistic,
                                      int oldValue,
                                      int newValue,
                                      @NotNull StatisticChangeCause cause) {
        this(user, statistic, oldValue, newValue, cause, null, !Bukkit.isPrimaryThread());
    }

    public PlayerStatisticChangeEvent(@NotNull UserProfile user,
                                      @NotNull ProfileMetric statistic,
                                      int oldValue,
                                      int newValue,
                                      @NotNull StatisticChangeCause cause,
                                      @Nullable CommandSender actor) {
        this(user, statistic, oldValue, newValue, cause, actor, !Bukkit.isPrimaryThread());
    }

    public PlayerStatisticChangeEvent(@NotNull UserProfile user,
                                      @NotNull ProfileMetric statistic,
                                      int oldValue,
                                      int newValue,
                                      @NotNull StatisticChangeCause cause,
                                      @Nullable CommandSender actor,
                                      boolean asynchronous) {
        super(asynchronous);
        this.user = Objects.requireNonNull(user, "user");
        this.statistic = Objects.requireNonNull(statistic, "statistic");
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.cause = Objects.requireNonNull(cause, "cause");
        this.actor = actor;
    }

    public @NotNull UserProfile getUser() {
        return user;
    }

    public @NotNull UUID getPlayerUuid() {
        return user.getUuid();
    }

    public @NotNull ProfileMetric getStatistic() {
        return statistic;
    }

    public int getOldValue() {
        return oldValue;
    }

    public int getNewValue() {
        return newValue;
    }

    public void setNewValue(int newValue) {
        this.newValue = newValue;
    }

    public @NotNull StatisticChangeCause getCause() {
        return cause;
    }

    public @Nullable CommandSender getActor() {
        return actor;
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
