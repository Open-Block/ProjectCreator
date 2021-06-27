package org.openblock.creator.code;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum Visibility {

    PUBLIC,
    PACKAGED(""),
    PROTECTED,
    PRIVATE;

    private final @Nullable String display;

    Visibility() {
        this(null);
    }

    Visibility(@Nullable String display) {
        this.display = display;
    }

    public @NotNull String getDisplayName() {
        if (this.display == null) {
            return this.name().toLowerCase();
        }
        return this.display;
    }


}
