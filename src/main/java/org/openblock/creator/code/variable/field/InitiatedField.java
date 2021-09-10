package org.openblock.creator.code.variable.field;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.Visibility;
import org.openblock.creator.code.call.returntype.ReturnType;
import org.openblock.creator.code.call.returntype.VoidedReturnType;
import org.openblock.creator.code.variable.VariableCaller;

public class InitiatedField extends Field {

    protected ReturnableLine caller;

    public InitiatedField(Visibility visibility, ReturnableLine line, String name, boolean isFinal, boolean isStatic) {
        super(visibility, name, isFinal, isStatic);
        this.caller = line;
    }

    @Override
    public @NotNull String writeCode(int indent) {
        return super.writeCode(indent) + " = " + caller.writeCode(0);
    }

    public ReturnableLine getCode() {
        return this.caller;
    }

    @Override
    public @NotNull ReturnType getReturnType() {
        @NotNull ReturnType returnType = this.caller.getReturnType();
        if (returnType instanceof VoidedReturnType) {
            throw new RuntimeException("Initiated Field cannot have a caller returning Void");
        }
        return returnType;
    }

    @Override
    public @NotNull VariableCaller<InitiatedField> createCaller() {
        return new VariableCaller<>(this);
    }
}
