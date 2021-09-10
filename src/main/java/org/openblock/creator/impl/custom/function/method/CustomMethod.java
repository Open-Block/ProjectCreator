package org.openblock.creator.impl.custom.function.method;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.call.returntype.ReturnType;
import org.openblock.creator.code.clazz.ClassType;
import org.openblock.creator.code.clazz.IClass;
import org.openblock.creator.code.function.IMethod;
import org.openblock.creator.impl.custom.function.AbstractCustomFunction;
import org.openblock.creator.impl.custom.function.CustomFunctionBuilder;

import java.util.SortedSet;
import java.util.stream.Collectors;

public class CustomMethod extends AbstractCustomFunction implements IMethod {

    private final @NotNull ReturnType returnType;
    private final boolean isStatic;

    public CustomMethod(@NotNull IClass clazz, CustomFunctionBuilder builder) {
        super(clazz, builder);
        this.isStatic = builder.isStatic();
        this.returnType = builder.getReturnType();
    }

    @Override
    public @NotNull ReturnType getReturnType() {
        return this.returnType;
    }

    @Override
    public String writeCode(int indent, ClassType type) {
        StringBuilder builder = new StringBuilder();
        if (type.equals(ClassType.INTERFACE)) {
            if (!this.getCodeBlock().isEmpty()) {
                builder
                        .append(" ".repeat(indent * 4))
                        .append("default ");
            } else {
                builder
                        .append(" ".repeat(indent * 4));
            }
        } else {
            builder
                    .append(" ".repeat(indent * 4))
                    .append(this.getVisibility().getDisplayName())
                    .append(" ");
        }
        if (this.isStatic()) {
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
        builder.append(" (");
        builder.append(this
                .getParameters()
                .stream()
                .map(p -> (p.isFinal() ? "final " : "")
                        + p.getReturnType().getDisplayText()
                        + " " + p.getName()
                )
                .collect(Collectors.joining(", ")));
        builder.append(")");
        if (type.equals(ClassType.INTERFACE) && this.getCodeBlock().isEmpty()) {
            builder.append(";\n");
        } else {
            builder.append("{\n");
        }
        this.getCodeBlock().forEach(c -> builder.append("\n").append(" ".repeat((indent + 1) * 4)).append(c.writeCode(0)));
        if (!(type.equals(ClassType.INTERFACE) && this.getCodeBlock().isEmpty())) {
            builder.append(" ".repeat(indent * 4)).append("}\n");
        }
        return builder.toString();
    }

    @Override
    public boolean isStatic() {
        return this.isStatic;
    }

    @Override
    public boolean isAbstract() {
        throw new RuntimeException("Not Implemented Yet");
    }

    @Override
    public @NotNull SortedSet<IClass> getImports() {
        throw new RuntimeException("Not Implemented Yet");
    }
}
