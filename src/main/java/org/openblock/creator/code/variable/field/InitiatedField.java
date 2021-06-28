package org.openblock.creator.code.variable.field;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.Visibility;
import org.openblock.creator.code.call.Caller;
import org.openblock.creator.code.call.returntype.ReturnType;
import org.openblock.creator.code.call.returntype.VoidedReturnType;

public class InitiatedField extends Field {

    protected Caller caller;

    public InitiatedField(Visibility visibility, Caller caller, String name, boolean isFinal, boolean isStatic) {
        super(visibility, name, isFinal, isStatic);
        this.caller = caller;
    }

    @Override
    public @NotNull ReturnType getReturnType() {
        @NotNull ReturnType returnType = this.caller.getCallable().getReturnType();
        if (returnType instanceof VoidedReturnType) {
            throw new RuntimeException("Initiated Field cannot have a caller returning Void");
        }
        return returnType;
    }
}
