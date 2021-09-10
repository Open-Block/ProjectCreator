package org.openblock.creator.code.function.caller;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.call.Caller;
import org.openblock.creator.code.function.IMethod;

import java.util.stream.Collectors;

public class MethodCaller extends FunctionCaller<IMethod> implements Caller.ParameterCaller {

    public MethodCaller(IMethod function) {
        super(function);
    }

    @Override
    public @NotNull String writeCode(int indent) {
        return this.getCallable().getTargetClass().getName() + "(" + this.parameters.stream().map(p -> p.writeCode(0)).collect(Collectors.joining(", ")) + ")";
    }
}
