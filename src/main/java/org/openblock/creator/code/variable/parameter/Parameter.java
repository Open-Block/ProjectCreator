package org.openblock.creator.code.variable.parameter;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.variable.IVariable;

public class Parameter implements IVariable {

    private boolean isFinal;
    private String name;

    public Parameter(@NotNull String name) {
        this.setName(name);
    }

    public boolean isFinal() {
        return this.isFinal;
    }

    public Parameter setFinal(boolean isFinal) {
        this.isFinal = isFinal;
        return this;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Parameter setName(@NotNull String name) {
        if (name.contains(" ") || name.contains("\t") || name.contains("\n")) {
            throw new IllegalArgumentException("'" + name + "' cannot contain whitespace");
        }
        this.name = name;
        return this;
    }
}
