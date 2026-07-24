package com.shyamstudio.clans.api.result;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/** Result of a controlled Clans API mutation. */
public final class OperationResult {

    private static final OperationResult SUCCESS = new OperationResult(OperationStatus.SUCCESS, Map.of());

    private final OperationStatus status;
    private final Map<String, String> details;

    private OperationResult(@NotNull OperationStatus status, @NotNull Map<String, String> details) {
        this.status = status;
        this.details = Collections.unmodifiableMap(new LinkedHashMap<>(details));
    }

    public static @NotNull OperationResult success() {
        return SUCCESS;
    }

    public static @NotNull OperationResult of(@NotNull OperationStatus status) {
        return status == OperationStatus.SUCCESS ? SUCCESS : new OperationResult(status, Map.of());
    }

    public static @NotNull OperationResult of(@NotNull OperationStatus status,
                                               @NotNull Map<String, String> details) {
        return new OperationResult(status, details);
    }

    public @NotNull OperationStatus getStatus() {
        return status;
    }

    public boolean isSuccess() {
        return status == OperationStatus.SUCCESS;
    }

    /** Optional machine-readable details such as {@code limit} or {@code remainingSeconds}. */
    public @NotNull Map<String, String> getDetails() {
        return details;
    }

    public String getDetail(@NotNull String key) {
        return details.get(key);
    }

    @Override
    public String toString() {
        return "OperationResult{status=" + status + ", details=" + details + '}';
    }
}
