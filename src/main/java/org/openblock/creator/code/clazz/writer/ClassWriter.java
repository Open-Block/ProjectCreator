package org.openblock.creator.code.clazz.writer;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.clazz.IClass;

public interface ClassWriter<C extends IClass> {

    @NotNull String write(C clazz);
}
