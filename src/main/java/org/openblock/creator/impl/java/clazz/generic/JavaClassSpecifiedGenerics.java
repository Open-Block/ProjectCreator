package org.openblock.creator.impl.java.clazz.generic;

import org.openblock.creator.code.clazz.generic.IGeneric;
import org.openblock.creator.code.clazz.generic.SpecifiedGenerics;
import org.openblock.creator.impl.java.clazz.JavaClass;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaClassSpecifiedGenerics implements SpecifiedGenerics {

    private final JavaClass clazz;

    public JavaClassSpecifiedGenerics(JavaClass clazz) {
        this.clazz = clazz;
    }

    public JavaClassSpecifiedGenerics(Class<?> clazz) {
        this(new JavaClass(clazz));
    }

    @Override
    public List<IGeneric> getGenerics() {
        return this.clazz.getGenerics();
    }

    @Override
    public List<SpecifiedGenerics> getSpecifiedGenericClass() {
        Class<?> targetClass = this.clazz.getTargetClass();
        Type[] types = targetClass.getGenericInterfaces();
        return Stream.of(types).map(JavaTypeSpecifiedGenerics::new).collect(Collectors.toList());
    }

    @Override
    public JavaClass getTargetReference() {
        return this.clazz;
    }
}
