package org.openblock.creator.code.function;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.Nameable;
import org.openblock.creator.code.Visibility;
import org.openblock.creator.code.call.Callable;
import org.openblock.creator.code.clazz.ClassPart;
import org.openblock.creator.code.clazz.ClassType;
import org.openblock.creator.code.clazz.generic.IGeneric;
import org.openblock.creator.code.statement.Statement;
import org.openblock.creator.code.variable.parameter.Parameter;

import java.util.List;
import java.util.stream.Collectors;

public interface IFunction extends Nameable, Statement, Callable, ClassPart, Comparable<IFunction> {

    List<Parameter> getParameters();

    @NotNull Visibility getVisibility();

    List<IGeneric> getGenerics();

    String writeCode(int indent, ClassType type);

    @Override
    @Deprecated
    default @NotNull String writeCode(int indent) {
        return writeCode(indent, ClassType.STANDARD);
    }

    @Override
    default int compareTo(@NotNull IFunction o) {
        if (this instanceof IMethod && !(o instanceof IMethod)) {
            return 1;
        }
        if (!(this instanceof IMethod) && o instanceof IMethod) {
            return -1;
        }
        if (this instanceof IMethod m1 && o instanceof IMethod m2) {
            if (m1.isStatic() && !m2.isStatic()) {
                return 1;
            }
            if (m2.isStatic() && !m1.isStatic()) {
                return -1;
            }
        }
        int nameCompare = this.getName().compareTo(o.getName());
        if (nameCompare != 0) {
            return nameCompare;
        }
        String thisParameters = this.getParameters().stream().map(p -> p.getReturnType().getDisplayText()).collect(Collectors.joining());
        String functionParameters = this.getParameters().stream().map(p -> p.getReturnType().getDisplayText()).collect(Collectors.joining());
        return thisParameters.compareTo(functionParameters);
    }
}
