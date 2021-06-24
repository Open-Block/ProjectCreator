package org.openblock.creator.code;

import org.jetbrains.annotations.NotNull;

public interface Nameable {

    String getName();
    Nameable setName(@NotNull String name);
}
