package org.openblock.creator.code.call;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.call.returntype.ReturnType;
import org.openblock.creator.code.line.CallingLine;

public interface Returnable {

    interface ReturnableLine extends Returnable, CallingLine {

    }

    @NotNull
    ReturnType getReturnType();
}
