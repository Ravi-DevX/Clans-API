package com.shyamstudio.clans.api.option;

/** Reason a player's selected chat channel changed. */
public enum ChatModeChangeCause {
    COMMAND,
    MENU,
    API,
    FEATURE_DISABLED,
    CLAN_LEFT,
    PLUGIN_RELOAD
}
