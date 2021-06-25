package org.openblock.creator.code.call;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.clazz.type.IType;

public class ReturnType {

    private final @NotNull
    IType type;
    private final boolean isArray;

    public ReturnType(@NotNull IType type, boolean isArray) {
        this.type = type;
        this.isArray = isArray;
    }

    public @NotNull
    IType getType() {
        return type;
    }

    public boolean isArray() {
        return isArray;
    }

    public String getDisplayText(){
        return this.getType().getName() + (this.isArray ? "[]" : "");
    }
}
