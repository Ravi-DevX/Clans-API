package com.shyamstudio.clans.api.option;

/** Determines how a configured clan shield is delivered to a member. */
public enum ClanShieldDeliveryMode {
    /** Apply the clan flag to the shield currently held in the main hand. */
    APPLY_TO_HELD_SHIELD,

    /** Create a new shield carrying the clan flag. */
    GIVE_NEW_SHIELD
}
