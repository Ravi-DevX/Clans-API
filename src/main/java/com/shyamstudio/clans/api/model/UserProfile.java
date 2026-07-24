package com.shyamstudio.clans.api.model;

import com.shyamstudio.clans.api.ClansAPI;
import com.shyamstudio.clans.api.option.ProfileMetric;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Represents an online player's data within the clan system.
 */
public interface UserProfile {

    /**
     * @return The player's UUID
     */
    @NotNull UUID getUuid();

    /**
     * @return The player's name
     */
    @NotNull String getName();

    /**
     * @return The UUID of the clan this user belongs to, or null
     */
    @Nullable UUID getClanUuid();

    /**
     * Sets the clan UUID directly.
     *
     * @deprecated Use membership operations exposed through
     *             {@link ClansAPI#getClanService()} so validation, events, and
     *             persistence rules are applied.
     */
    @Deprecated(since = "1.1.0")
    void setClanUuid(@Nullable UUID clanUuid);

    /**
     * @return true if the player is currently in a clan
     */
    default boolean hasClan() {
        return getClanUuid() != null;
    }

    /**
     * Get a specific statistic value.
     */
    int getStatistic(@NotNull ProfileMetric stat);

    /**
     * Sets a statistic directly.
     *
     * @deprecated Use validated plugin operations exposed through
     *             {@link ClansAPI#getClanService()} instead of mutating model state.
     */
    @Deprecated(since = "1.1.0")
    void setStatistic(@NotNull ProfileMetric stat, int value);

    /**
     * Increments a statistic directly.
     *
     * @deprecated Use validated plugin operations exposed through
     *             {@link ClansAPI#getClanService()} instead of mutating model state.
     */
    @Deprecated(since = "1.1.0")
    default void incrementStatistic(@NotNull ProfileMetric stat, int amount) {
        setStatistic(stat, getStatistic(stat) + amount);
    }

    /**
     * @return The maximum members this user's clan can have (based on perks)
     */
    int getMaxMembers();

    /**
     * @return The maximum allies this user's clan can have (based on perks)
     */
    int getMaxAllies();
}
