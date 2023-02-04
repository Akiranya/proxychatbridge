package com.ranull.proxychatbridge.velocity.tag;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

public interface MetaProvider {

    /**
     * Returns a "meta value" which the "meta key" maps to.
     *
     * @param uuid the uuid of a player
     *
     * @return a "meta value" which the "meta key" maps to
     */
    @NonNull String meta(final @NonNull UUID uuid, String metaKey);

}
