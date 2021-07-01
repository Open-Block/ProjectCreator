package org.openblock.creator.code.function;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.clazz.ClassType;
import org.openblock.creator.code.function.caller.ConstructorCaller;

import java.util.stream.Collectors;

public interface IConstructor extends IFunction {

    @Override
    default @NotNull ConstructorCaller createCaller() {
        return new ConstructorCaller(this);
    }

    @Override
    default @NotNull String writeCode(int indent, ClassType type) {
        if (type.equals(ClassType.INTERFACE)) {
            throw new RuntimeException("A constructor is not allowed in a interface");
        }

        StringBuilder builder = new StringBuilder();
        builder.append(" ".repeat(indent * 4));
        builder.append(this.getVisibility().getDisplayName());
        builder.append(" ");
        if (!this.getGenerics().isEmpty()) {
            String generics = this.getGenerics().stream().map(g -> g.writeCode(0)).collect(Collectors.joining(", "));
            builder.append("<");
            builder.append(generics);
            builder.append("> ");
        }
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
        builder.append("){\n");
        builder.append(this.getCodeBlock().stream().map(c -> c.writeCode(indent + 1)).collect(Collectors.joining("\n")));
        builder.append(" ".repeat((indent + 1) * 4));
        builder.append("}");
        return builder.toString();
    }
}
