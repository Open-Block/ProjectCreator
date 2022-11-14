package org.openblock.creator.impl.java.function.constructor;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.call.returntype.ReturnType;
import org.openblock.creator.code.call.returntype.StatedReturnType;
import org.openblock.creator.code.clazz.type.BasicType;
import org.openblock.creator.code.function.IConstructor;
import org.openblock.creator.impl.custom.function.CustomFunctionBuilder;
import org.openblock.creator.impl.java.clazz.JavaClass;
import org.openblock.creator.impl.java.function.JavaFunction;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class JavaConstructor<C extends Class<?>> extends JavaFunction implements IConstructor {

    public JavaConstructor(Constructor<C> constructor) {
        super(constructor);
    }

    @Override
    public @NotNull String getName() {
        return this.method.getDeclaringClass().getSimpleName();
    }

    @Override
    public @NotNull ReturnType getReturnType() {
        Class<?> clazz = this.method.getDeclaringClass();
        return new StatedReturnType(new BasicType(new JavaClass(clazz)), false);
    }

    @Override
    public @NotNull CustomFunctionBuilder toBuilder() {
        return new CustomFunctionBuilder()
                .setVisibility(this.getVisibility())
                .setClassFor(this.getTargetClass());
    }
}
