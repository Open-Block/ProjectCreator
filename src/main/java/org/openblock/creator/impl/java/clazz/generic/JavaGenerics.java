package org.openblock.creator.impl.java.clazz.generic;

import org.openblock.creator.code.Nameable;
import org.openblock.creator.code.clazz.generic.CollectedGeneric;
import org.openblock.creator.code.clazz.generic.IGeneric;
import org.openblock.creator.code.clazz.generic.UnknownGeneric;
import org.openblock.creator.code.clazz.generic.specified.NoGenerics;
import org.openblock.creator.code.clazz.generic.specified.SpecifiedGenerics;
import org.openblock.creator.code.clazz.type.BasicType;
import org.openblock.creator.code.clazz.type.GenericType;
import org.openblock.creator.code.clazz.type.IType;
import org.openblock.creator.impl.java.clazz.JavaClass;
import org.openblock.creator.impl.java.clazz.generic.specified.JavaParameterizedGenerics;
import org.openblock.creator.impl.java.clazz.generic.specified.JavaVariableGenerics;
import org.openblock.creator.impl.java.clazz.generic.specified.JavaWildcardGenerics;

import java.lang.reflect.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface JavaGenerics {

    static SpecifiedGenerics specified(Nameable nameable, Type type) {
        if (type instanceof TypeVariable<?> v) {
            return new JavaVariableGenerics<>(nameable, v);
        }
        if (type instanceof Class<?> c) {
            return new NoGenerics(new JavaClass(c));
        }
        if (type instanceof ParameterizedType p) {
            return new JavaParameterizedGenerics(nameable, p);
        }
        if (type instanceof GenericArrayType ga) {
            return specified(nameable, ga.getGenericComponentType());
        }
        if (type instanceof WildcardType w) {
            return new JavaWildcardGenerics(nameable, w);
        }
        throw new RuntimeException("Unknown specified type for " + type.getClass().getName());
    }

    static List<IGeneric> type(Type type) {
        if (type instanceof TypeVariable<?> v) {
            return Collections.singletonList(typeVariable(v));
        }
        if (type instanceof WildcardType w) {
            return Collections.singletonList(typeWildcard(w));
        }
        return Collections.emptyList();
    }

    static IType asType(Type type) {
        if (type instanceof Class<?> c) {
            return new BasicType(new JavaClass(c));
        }
        List<IGeneric> generics = type(type);
        if (generics.size() == 1) {
            return new GenericType(generics.get(0));
        }
        UnknownGeneric generic = new UnknownGeneric(true, generics.stream().map(GenericType::new).collect(Collectors.toList()));
        return new GenericType(generic);
    }

    static IGeneric typeWildcard(WildcardType type) {
        Type[] bounds = type.getLowerBounds();
        boolean extended = false;
        if (bounds.length == 0) {
            bounds = type.getUpperBounds();
            extended = true;
        }
        return new CollectedGeneric("?", extended, Stream.of(bounds).map(JavaGenerics::asType).collect(Collectors.toList()));
    }

    static CollectedGeneric typeVariable(TypeVariable<?> type) {
        Type[] types = type.getBounds();
        return
                new CollectedGeneric(
                        type.getName(),
                        true,
                        Stream
                                .of(types)
                                .map(JavaGenerics::asType)
                                .collect(Collectors.toList()));
    }
}
