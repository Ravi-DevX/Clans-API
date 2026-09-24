package com.shyamstudio.clans.api.extension;

import com.shyamstudio.clans.api.result.OperationResult;
import org.jetbrains.annotations.NotNull;

/** Handler for a validated addon action referenced by a Clans menu. */
@FunctionalInterface
public interface ClanMenuAction {

    @NotNull OperationResult execute(@NotNull ClanMenuActionContext context);
}
