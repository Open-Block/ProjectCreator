package org.openblock.creator.code.variable.field;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.Visibility;
import org.openblock.creator.code.call.returntype.ReturnType;
import org.openblock.creator.code.call.returntype.StatedReturnType;
import org.openblock.creator.code.variable.VariableCaller;

public class UninitiatedField extends Field {

    protected StatedReturnType state;

    public UninitiatedField(Visibility visibility, StatedReturnType type, String name, boolean isFinal, boolean isStatic) {
        super(visibility, name, isFinal, isStatic);
        this.state = type;
    }

    @Override
    public @NotNull ReturnType getReturnType() {
        return this.state;
    }

    @Override
    public @NotNull VariableCaller<UninitiatedField> createCaller() {
        return new VariableCaller<>(this);
    }
}
