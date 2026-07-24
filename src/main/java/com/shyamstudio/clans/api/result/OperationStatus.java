package com.shyamstudio.clans.api.result;

/** Stable, non-localized outcome codes returned by controlled API operations. */
public enum OperationStatus {
    SUCCESS,
    CANCELLED,
    FEATURE_DISABLED,
    PLAYER_NOT_FOUND,
    CLAN_NOT_FOUND,
    NO_CLAN,
    ALREADY_IN_CLAN,
    NOT_MEMBER,
    NOT_OWNER,
    NOT_ALLOWED,
    ALREADY_EXISTS,
    NOT_FOUND,
    LIMIT_REACHED,
    INVALID_INPUT,
    SAME_VALUE,
    COOLDOWN,
    ECONOMY_UNAVAILABLE,
    INSUFFICIENT_FUNDS,
    FAILED
}
