package org.openblock.creator.code.clazz.caller.thiscaller;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.call.Callable;
import org.openblock.creator.code.call.Caller;
import org.openblock.creator.code.call.returntype.ReturnType;
import org.openblock.creator.code.call.returntype.StatedReturnType;
import org.openblock.creator.code.clazz.type.BasicType;
import org.openblock.creator.impl.custom.clazz.AbstractCustomClass;

public class ThisCallable implements Callable {

    private final AbstractCustomClass clazz;


    public ThisCallable(AbstractCustomClass clazz) {
        this.clazz = clazz;
    }

    @Override
    public @NotNull Caller createCaller() {
        return new ThisCaller(this);
    }

    @Override
    public String getName() {
        return "this";
    }

    @Override
    public @NotNull ReturnType getReturnType() {
        return new StatedReturnType(new BasicType(this.clazz), false);
    }
}
