package org.openblock.creator.code.clazz.generic;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.Codeable;
import org.openblock.creator.code.Nameable;
import org.openblock.creator.code.clazz.type.IType;

import java.util.List;

public interface IGeneric extends Nameable, Codeable {

    List<IType> getClasses();

    boolean isExtending();

    @NotNull String writeCode(boolean genericExtends);

    default @NotNull String writeCode() {
        return writeCode(true);
    }

    @Override
    default @NotNull String writeCode(int indent) {
        return writeCode();
    }
}
