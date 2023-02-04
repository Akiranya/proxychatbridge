package com.ranull.proxychatbridge.velocity.provider;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

public class EmptyMetaProvider implements MetaProvider {

    @Override public @NonNull String meta(final @NonNull UUID uuid, final String metaKey) {
        return "";
    }

}
