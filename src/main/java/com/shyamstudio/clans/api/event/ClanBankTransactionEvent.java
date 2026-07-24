package com.shyamstudio.clans.api.event;

import com.shyamstudio.clans.api.model.ClanProfile;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Event called when a player performs a bank transaction (deposit or withdraw) on a clan bank.
 */
public class ClanBankTransactionEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    public enum TransactionType {
        DEPOSIT,
        WITHDRAW
    }

    private final Player player;
    private final ClanProfile clan;
    private double amount;
    private final TransactionType type;
    private boolean cancelled = false;

    public ClanBankTransactionEvent(@NotNull Player player, @NotNull ClanProfile clan, double amount, @NotNull TransactionType type) {
        this.player = player;
        this.clan = clan;
        this.amount = amount;
        this.type = type;
    }

    public @NotNull Player getPlayer() {
        return player;
    }

    public @NotNull ClanProfile getClan() {
        return clan;
    }

    public double getAmount() {
        return amount;
    }

    /**
     * Replaces the amount that will be deposited or withdrawn.
     *
     * @param amount finite amount greater than zero
     */
    public void setAmount(double amount) {
        if (!Double.isFinite(amount) || amount <= 0.0D) {
            throw new IllegalArgumentException("amount must be finite and greater than zero");
        }
        this.amount = amount;
    }

    public @NotNull TransactionType getTransactionType() {
        return type;
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
