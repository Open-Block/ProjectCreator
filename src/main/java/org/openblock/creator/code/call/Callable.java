package org.openblock.creator.code.call;

import org.jetbrains.annotations.NotNull;

public interface Callable extends Returnable {

    @NotNull Caller createCaller();
}
