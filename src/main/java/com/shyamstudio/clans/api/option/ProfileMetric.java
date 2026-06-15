package com.shyamstudio.clans.api.option;

/**
 * Custom statistics tracked per-player for scoring.
 * Points from the settings GUI mockup:
 *   Kill: +10, Death: -5, Diamond mined: +1, Scrap smelted: +2
 */
public enum ProfileMetric {

    KILLS,
    DEATHS,
    DIAMONDS_MINED,
    SCRAP_SMELTED,
    BLOCKS_PLACED,
    BLOCKS_BROKEN,
    MOBS_KILLED
}
