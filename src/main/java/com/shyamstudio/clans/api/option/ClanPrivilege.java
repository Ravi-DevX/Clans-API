package com.shyamstudio.clans.api.option;

/**
 * Permissions that can be granted per role within a clan.
 * Each role has a set of these permissions.
 */
public enum ClanPrivilege {

    /** Invite new members to the clan */
    INVITE_MEMBERS,

    /** Kick members from the clan (below own rank) */
    KICK_MEMBERS,

    /** Promote members (below own rank) */
    PROMOTE_MEMBERS,

    /** Demote members (below own rank) */
    DEMOTE_MEMBERS,

    /** Set clan name / display name */
    RENAME_CLAN,

    /** Change clan tag */
    SET_TAG,

    /** Set the clan home location */
    DEFINE_HOME,

    /** Delete the clan home location */
    DELETE_HOME,

    /** Use /clan home to teleport */
    TELEPORT_HOME,

    /** Access the shared clan chest */
    OPEN_VAULT,

    /** Send/accept/deny alliance requests */
    ALLIANCE_CONTROL,

    /** Toggle friendly fire */
    MODIFY_FRIENDLY_FIRE,

    /** Toggle open/closed clan */
    TOGGLE_OPEN_CLOSED,

    /** Edit the clan banner/flag */
    EDIT_FLAG,

    /** Accept join requests */
    ACCEPT_REQUESTS,

    /** Use clan chat */
    USE_CLAN_CHAT,

    /** Use ally chat */
    USE_ALLY_CHAT,

    /** Set the clan spawn location */
    SET_SPAWN,

    /** Use /clan spawn to teleport */
    USE_SPAWN,

    /** Set/update the clan description */
    SET_DESCRIPTION,

    /** Deposit money into the clan bank */
    BANK_DEPOSIT,

    /** Withdraw money from the clan bank */
    BANK_WITHDRAW,

    /** View clan-wide statistics and leaderboards */
    VIEW_STATS
}
