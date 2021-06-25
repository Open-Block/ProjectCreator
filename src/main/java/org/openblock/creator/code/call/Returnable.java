package org.openblock.creator.code.call;

import org.jetbrains.annotations.NotNull;

public interface Returnable {

    @NotNull
    ReturnType getReturnType();
}
