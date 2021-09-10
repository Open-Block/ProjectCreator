package org.openblock.creator.code.call;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.call.returntype.ReturnType;
import org.openblock.creator.code.line.Line;

public interface Returnable {

    interface ReturnableLine extends Returnable, Line {

    }

    @NotNull
    ReturnType getReturnType();
}
