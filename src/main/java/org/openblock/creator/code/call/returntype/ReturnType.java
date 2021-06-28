package org.openblock.creator.code.call.returntype;

import org.openblock.creator.code.clazz.type.IType;

public interface ReturnType {

    IType getType();

    boolean isArray();

    default String getDisplayText() {
        return this.getType().getName() + (this.isArray() ? "[]" : "");
    }
}
