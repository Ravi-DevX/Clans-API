package com.shyamstudio.clans.api.extension;

/** A removable registration owned by one addon plugin. */
public interface ExtensionRegistration extends AutoCloseable {

    /**
     * Removes this exact registration. This operation is idempotent.
     *
     * @return {@code true} only when this call removed the live registration
     */
    boolean unregister();

    /** @return whether this registration is still active */
    boolean isRegistered();

    @Override
    default void close() {
        unregister();
    }
}
