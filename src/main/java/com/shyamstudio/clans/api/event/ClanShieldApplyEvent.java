package com.shyamstudio.clans.api.event;

import com.shyamstudio.clans.api.model.ClanProfile;
import com.shyamstudio.clans.api.option.ClanShieldDeliveryMode;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Fired after a clan shield has been prepared and before money or items are changed.
 * The resulting shield can be replaced, but must remain one shield item.
 */
public final class ClanShieldApplyEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Player player;
    private final ClanProfile clan;
    private final ClanShieldDeliveryMode deliveryMode;
    private final ItemStack originalShield;
    private final double price;
    private ItemStack resultShield;
    private boolean cancelled;

    public ClanShieldApplyEvent(@NotNull Player player,
                                @NotNull ClanProfile clan,
                                @NotNull ClanShieldDeliveryMode deliveryMode,
                                @Nullable ItemStack originalShield,
                                @NotNull ItemStack resultShield,
                                double price) {
        this(player, clan, deliveryMode, originalShield, resultShield, price, !Bukkit.isPrimaryThread());
    }

    public ClanShieldApplyEvent(@NotNull Player player,
                                @NotNull ClanProfile clan,
                                @NotNull ClanShieldDeliveryMode deliveryMode,
                                @Nullable ItemStack originalShield,
                                @NotNull ItemStack resultShield,
                                double price,
                                boolean asynchronous) {
        super(asynchronous);
        this.player = Objects.requireNonNull(player, "player");
        this.clan = Objects.requireNonNull(clan, "clan");
        this.deliveryMode = Objects.requireNonNull(deliveryMode, "deliveryMode");
        this.originalShield = originalShield == null ? null : originalShield.clone();
        this.resultShield = Objects.requireNonNull(resultShield, "resultShield").clone();
        this.price = price;
    }

    @NotNull
    public Player getPlayer() {
        return player;
    }

    @NotNull
    public ClanProfile getClan() {
        return clan;
    }

    @NotNull
    public ClanShieldDeliveryMode getDeliveryMode() {
        return deliveryMode;
    }

    @Nullable
    public ItemStack getOriginalShield() {
        return originalShield == null ? null : originalShield.clone();
    }

    @NotNull
    public ItemStack getResultShield() {
        return resultShield.clone();
    }

    public void setResultShield(@NotNull ItemStack resultShield) {
        this.resultShield = Objects.requireNonNull(resultShield, "resultShield").clone();
    }

    public double getPrice() {
        return price;
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
        return HANDLERS;
    }

    public static @NotNull HandlerList getHandlerList() {
        return HANDLERS;
    }
}
