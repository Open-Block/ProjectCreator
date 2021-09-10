package org.openblock.creator.code.call;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.Codeable;
import org.openblock.creator.code.call.returntype.ReturnType;

import java.util.List;

public interface Caller extends Returnable.ReturnableLine {

    interface ParameterCaller extends Caller {

        List<Codeable> getParameters();

    }

    Callable getCallable();

    @Override
    default @NotNull ReturnType getReturnType() {
        return this.getCallable().getReturnType();
    }
}
