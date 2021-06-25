package org.openblock.creator.impl.java.function.method;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.Codeable;
import org.openblock.creator.code.Visibility;
import org.openblock.creator.code.call.Caller;
import org.openblock.creator.code.call.ReturnType;
import org.openblock.creator.code.clazz.IClass;
import org.openblock.creator.code.clazz.generic.IGeneric;
import org.openblock.creator.code.function.IMethod;
import org.openblock.creator.code.variable.parameter.Parameter;

import java.lang.reflect.Method;
import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;

public class JavaMethod implements IMethod {

    private final Method method;

    public JavaMethod(Method method) {
        this.method = method;
    }

    @Override
    public String writeCode() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getVisibility().name().toLowerCase());
        builder.append(" ");
        if (this.isStatic()) {
            builder.append("static ");
        }
        if (!this.getGenerics().isEmpty()) {
            String generics = this.getGenerics().stream().map(Codeable::writeCode).collect(Collectors.joining(", "));
            builder.append("<");
            builder.append(generics);
            builder.append("> ");
        }
        builder.append(this.getReturnType().getDisplayText());
        builder.append(" ");
        builder.append(this.getName());
        builder.append(" (");
        builder.append(this
                .getParameters()
                .stream()
                .map(p -> (p.isFinal() ? "final " : "")
                        + p.getReturnType().getDisplayText()
                        + " " + p.getName()
                )
                .collect(Collectors.joining(", ")));
        builder.append(");\n");
        return builder.toString();
    }

    @Override
    public SortedSet<IClass> getImports() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Caller createCaller() {
        return null;
    }

    @Override
    public @NotNull ReturnType getReturnType() {
        return null;
    }

    @Override
    public IClass getTargetClass() {
        return null;
    }

    @Override
    public List<Parameter> getParameters() {
        return null;
    }

    @Override
    public Visibility getVisibility() {
        return null;
    }

    @Override
    public List<IGeneric> getGenerics() {
        return null;
    }

    @Override
    public boolean isStatic() {
        return false;
    }

    @Override
    public List<Codeable> getCodeBlock() {
        return null;
    }
}
