package org.openblock.creator.code.call;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.Codeable;
import org.openblock.creator.code.call.returntype.ReturnType;
import org.openblock.creator.code.function.IFunction;

import java.util.List;
import java.util.TreeSet;

public interface Caller extends Returnable.ReturnableLine, CallerProvider {

    interface ParameterCaller extends Caller {

        List<Codeable> getParameters();

        @Override
        IFunction getCallable();
    }

    Callable getCallable();

    @Override
    default @NotNull ReturnType getReturnType() {
        return this.getCallable().getReturnType();
    }

    @Override
    default TreeSet<Caller> getCallers() {
        return getReturnType().getCallers();
    }
}
