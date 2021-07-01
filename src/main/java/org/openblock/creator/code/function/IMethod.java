package org.openblock.creator.code.function;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.function.caller.MethodCaller;

public interface IMethod extends IFunction {

    boolean isStatic();

    boolean isAbstract();

    @Override
    default @NotNull MethodCaller createCaller() {
        return new MethodCaller(this);
    }
}
