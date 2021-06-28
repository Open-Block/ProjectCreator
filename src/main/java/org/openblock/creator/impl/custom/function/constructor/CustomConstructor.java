package org.openblock.creator.impl.custom.function.constructor;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.call.returntype.StatedReturnType;
import org.openblock.creator.code.clazz.IClass;
import org.openblock.creator.code.clazz.type.BasicType;
import org.openblock.creator.code.function.IConstructor;
import org.openblock.creator.impl.custom.function.AbstractCustomFunction;
import org.openblock.creator.impl.custom.function.CustomFunctionBuilder;

public class CustomConstructor extends AbstractCustomFunction implements IConstructor {

    public CustomConstructor(@NotNull IClass clazz, CustomFunctionBuilder builder) {
        super(clazz, builder);
    }

    @Override
    public @NotNull ConstructorCaller createCaller() {
        return new ConstructorCaller(this);
    }

    @Override
    public @NotNull StatedReturnType getReturnType() {
        return new StatedReturnType(new BasicType(this.getTargetClass()), false);
    }
}
