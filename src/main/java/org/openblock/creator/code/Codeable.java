package org.openblock.creator.code;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.clazz.IClass;

import java.util.SortedSet;

public interface Codeable {

	@NotNull String writeCode(int indent);

	@NotNull SortedSet<IClass> getImports();

	@NotNull CodeBuilder<?> toBuilder();
}
