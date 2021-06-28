package org.openblock.creator.code.call;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.call.returntype.ReturnType;

public interface Returnable {

    @NotNull
    ReturnType getReturnType();
}
