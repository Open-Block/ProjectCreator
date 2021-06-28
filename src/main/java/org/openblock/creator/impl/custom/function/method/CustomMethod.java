package org.openblock.creator.impl.custom.function.method;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.call.Caller;
import org.openblock.creator.code.call.returntype.ReturnType;
import org.openblock.creator.code.clazz.ClassType;
import org.openblock.creator.code.clazz.IClass;
import org.openblock.creator.code.function.IMethod;
import org.openblock.creator.impl.custom.function.AbstractCustomFunction;
import org.openblock.creator.impl.custom.function.CustomFunctionBuilder;

public class CustomMethod extends AbstractCustomFunction implements IMethod {

    private final @NotNull ReturnType returnType;
    private final boolean isStatic;

    public CustomMethod(@NotNull IClass clazz, CustomFunctionBuilder builder) {
        super(clazz, builder);
        this.isStatic = builder.isStatic();
        this.returnType = builder.getReturnType();
    }

    @Override
    public @NotNull Caller.Parameter createCaller() {
        throw new RuntimeException("Cannot create caller yet");
    }

    @Override
    public @NotNull ReturnType getReturnType() {
        return this.returnType;
    }

    @Override
    public String writeCode(int indent, ClassType type) {
        throw new RuntimeException("Cannot write code yet");
    }

    @Override
    public boolean isStatic() {
        return this.isStatic;
    }
}
