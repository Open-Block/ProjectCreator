package org.openblock.creator.code.function.caller;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.call.Caller;
import org.openblock.creator.code.function.IConstructor;

import java.util.stream.Collectors;

public class ConstructorCaller extends FunctionCaller<IConstructor> implements Caller.Parameter {

    public ConstructorCaller(IConstructor constructor) {
        super(constructor);
    }

    @Override
    public @NotNull String writeCode(int indent) {
        return "new " + this.getCallable().getTargetClass().getName() + "(" + this.parameters.stream().map(p -> p.writeCode(0)).collect(Collectors.joining(", ")) + ")";
    }
}
