package org.openblock.creator.code.call;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.Nameable;

public interface Callable extends Returnable, Nameable {

    @NotNull Caller createCaller();
}
