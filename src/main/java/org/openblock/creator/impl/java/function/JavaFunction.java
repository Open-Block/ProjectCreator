package org.openblock.creator.impl.java.function;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.Codeable;
import org.openblock.creator.code.Visibility;
import org.openblock.creator.code.call.Caller;
import org.openblock.creator.code.call.ReturnType;
import org.openblock.creator.code.clazz.IClass;
import org.openblock.creator.code.clazz.generic.CollectedGeneric;
import org.openblock.creator.code.clazz.generic.IGeneric;
import org.openblock.creator.code.clazz.generic.specified.SpecifiedGenerics;
import org.openblock.creator.code.clazz.type.BasicType;
import org.openblock.creator.code.clazz.type.GenericType;
import org.openblock.creator.code.clazz.type.IType;
import org.openblock.creator.code.clazz.type.SpecifiedGenericType;
import org.openblock.creator.code.function.IFunction;
import org.openblock.creator.code.variable.parameter.Parameter;
import org.openblock.creator.impl.java.clazz.JavaClass;
import org.openblock.creator.impl.java.clazz.generic.JavaGenerics;
import org.openblock.creator.impl.java.function.method.JavaMethod;

import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class JavaFunction implements IFunction {

    protected final Executable method;

    public JavaFunction(Method method) {
        this.method = method;
    }

    public String writeCode(int indent) {
        StringBuilder builder = new StringBuilder();
        builder.append(" ".repeat(indent * 4));
        builder.append(this.getVisibility().name().toLowerCase());
        builder.append(" ");
        if (this instanceof JavaMethod m && m.isStatic()) {
            builder.append("static ");
        }
        if (!this.getGenerics().isEmpty()) {
            String generics = this.getGenerics().stream().map(g -> g.writeCode(0)).collect(Collectors.joining(", "));
            builder.append("<");
            builder.append(generics);
            builder.append("> ");
        }
        builder.append(this.getReturnType().getDisplayText());
        builder.append(" ");
        builder.append(this.getName());
        builder.append("(");
        builder.append(this
                .getParameters()
                .stream()
                .map(p -> (p.isFinal() ? "final " : "")
                        + p.getReturnType().getDisplayText()
                        + " " + p.getName()
                )
                .collect(Collectors.joining(", ")));
        builder.append(");");
        return builder.toString();
    }

    @Override
    public SortedSet<IClass> getImports() {
        TreeSet<IClass> set = new TreeSet<>();
        set.addAll(this.getReturnType().getType().getClasses());
        set.addAll(this.getParameters().parallelStream().flatMap(p -> p.getReturnType().getType().getClasses().parallelStream()).collect(Collectors.toSet()));
        return set;
    }

    @Override
    public @NotNull String getName() {
        return this.method.getName();
    }

    @Override
    public Caller createCaller() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public @NotNull JavaClass getTargetClass() {
        return new JavaClass(this.method.getDeclaringClass());
    }

    @Override
    public List<Parameter> getParameters() {
        return Stream.of(this.method.getParameters()).map(p -> {
            Type parameterType = p.getParameterizedType();
            Class<?> clazz = p.getType();
            SpecifiedGenerics generics = JavaGenerics.specified(new JavaClass(clazz), parameterType);
            SpecifiedGenericType type = new SpecifiedGenericType(generics);
            return new Parameter(new ReturnType(type, clazz.isArray()), p.getName(), Modifier.isFinal(p.getModifiers()));
        }).collect(Collectors.toList());
    }

    @Override
    public @NotNull Visibility getVisibility() {
        int modifier = this.method.getModifiers();
        if (Modifier.isPublic(modifier)) {
            return Visibility.PUBLIC;
        }
        if (Modifier.isProtected(modifier)) {
            return Visibility.PROTECTED;
        }
        if (Modifier.isPrivate(modifier)) {
            return Visibility.PRIVATE;
        }
        return Visibility.PACKAGED;
    }

    @Override
    public List<IGeneric> getGenerics() {
        return Collections.emptyList();
    }

    @Override
    public List<Codeable> getCodeBlock() {
        return Collections.emptyList();
    }
}
