package com.shyamstudio.clans.api.event;

import com.shyamstudio.clans.api.option.ClanFeature;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Notification fired after the runtime state of a clan feature changes.
 */
public class ClanFeatureStateChangeEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final ClanFeature feature;
    private final boolean oldEnabled;
    private final boolean newEnabled;

    public ClanFeatureStateChangeEvent(@NotNull ClanFeature feature,
                                       boolean oldEnabled,
                                       boolean newEnabled) {
        this(feature, oldEnabled, newEnabled, !Bukkit.isPrimaryThread());
    }

    public ClanFeatureStateChangeEvent(@NotNull ClanFeature feature,
                                       boolean oldEnabled,
                                       boolean newEnabled,
                                       boolean asynchronous) {
        super(asynchronous);
        this.feature = Objects.requireNonNull(feature, "feature");
        this.oldEnabled = oldEnabled;
        this.newEnabled = newEnabled;
    }

    public @NotNull ClanFeature getFeature() {
        return feature;
    }

    public boolean wasEnabled() {
        return oldEnabled;
    }

    public boolean isOldEnabled() {
        return oldEnabled;
    }

    public boolean isEnabled() {
        return newEnabled;
    }

    public boolean isNewEnabled() {
        return newEnabled;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static @NotNull HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
