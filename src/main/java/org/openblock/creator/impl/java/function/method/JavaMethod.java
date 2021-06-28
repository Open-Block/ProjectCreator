package org.openblock.creator.impl.java.function.method;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.call.returntype.StatedReturnType;
import org.openblock.creator.code.clazz.generic.specified.SpecifiedGenerics;
import org.openblock.creator.code.clazz.type.SpecifiedGenericType;
import org.openblock.creator.code.function.IMethod;
import org.openblock.creator.impl.java.clazz.JavaClass;
import org.openblock.creator.impl.java.clazz.generic.JavaGenerics;
import org.openblock.creator.impl.java.function.JavaFunction;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

public class JavaMethod extends JavaFunction implements IMethod {

    public JavaMethod(Method method) {
        super(method);
    }

    @Override
    public boolean isStatic() {
        return Modifier.isStatic(this.method.getModifiers());
    }

    @Override
    public @NotNull StatedReturnType getReturnType() {
        Method method = ((Method)this.method);
        Type returnType = method.getGenericReturnType();
        Class<?> returnClassType = method.getReturnType();
        SpecifiedGenerics generics = JavaGenerics.specified(new JavaClass(returnClassType), returnType);
        return new StatedReturnType(new SpecifiedGenericType(generics), returnClassType.isArray());
    }
}
