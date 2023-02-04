package com.ranull.proxychatbridge.velocity.provider;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;

import java.util.List;
import java.util.UUID;

@DefaultQualifier(NonNull.class)
public class EmptyGroupProvider implements GroupProvider {

    @Override
    public List<String> groups(final UUID uuid) {
        return List.of();
    }

}
