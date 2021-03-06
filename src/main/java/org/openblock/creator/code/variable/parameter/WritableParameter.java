package org.openblock.creator.code.variable.parameter;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.Writable;
import org.openblock.creator.code.call.returntype.StatedReturnType;

public class WritableParameter extends Parameter implements Writable {

    public WritableParameter(StatedReturnType type, String name, boolean isFinal) {
        super(type, name, isFinal);
    }

    public WritableParameter setFinal(boolean isFinal) {
        this.isFinal = isFinal;
        return this;
    }

    public WritableParameter setReturnType(StatedReturnType type) {
        this.returnType = type;
        return this;
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    public WritableParameter setName(@NotNull String name) {
        isValidName(name);
        this.name = name;
        return this;
    }
}
