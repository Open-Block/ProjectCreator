package org.openblock.creator.code.line;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.CodeBuilder;
import org.openblock.creator.code.call.Caller;
import org.openblock.creator.code.call.Returnable;
import org.openblock.creator.code.call.returntype.ReturnType;
import org.openblock.creator.code.call.returntype.StatedReturnType;
import org.openblock.creator.code.clazz.IClass;
import org.openblock.creator.code.clazz.type.IType;

import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class NullValue implements Returnable.ReturnableLine, IType {
	@Override
	public @NotNull String writeCode(int indent) {
		return "null";
	}

	@Override
	public @NotNull SortedSet<IClass> getImports() {
		return new TreeSet<>();
	}

	@Override
	public @NotNull CodeBuilder<?> toBuilder() {
		throw new RuntimeException("Not implemented yet");
	}

	@Override
	public List<IClass> getClasses() {
		return Collections.emptyList();
	}

	@Override
	public TreeSet<Caller> getCallers() {
		return new TreeSet<>();
	}

	@Override
	public @NotNull ReturnType getReturnType() {
		return new StatedReturnType(this, false);
	}

	@Override
	public @NotNull String getName() {
		return "null";
	}
}
