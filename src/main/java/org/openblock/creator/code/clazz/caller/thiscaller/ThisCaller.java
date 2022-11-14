package org.openblock.creator.code.clazz.caller.thiscaller;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.CodeBuilder;
import org.openblock.creator.code.call.Callable;
import org.openblock.creator.code.call.Caller;
import org.openblock.creator.code.clazz.IClass;
import org.openblock.creator.code.clazz.caller.CallerBuilder;
import org.openblock.creator.utils.OpenStringUtils;

import java.util.SortedSet;
import java.util.TreeSet;

public class ThisCaller implements Caller {

	private final ThisCallable callable;

	public ThisCaller(ThisCallable callable) {
		this.callable = callable;
	}

	@Override
	public @NotNull String writeCode(int indent) {
		return OpenStringUtils.repeat(indent, "\t") + "this";
	}

	@Override
	public @NotNull SortedSet<IClass> getImports() {
		return new TreeSet<>();
	}

	@Override
	public @NotNull CodeBuilder<?> toBuilder() {
		return new CallerBuilder().setCaller(this.callable);
	}

	@Override
	public Callable getCallable() {
		return this.callable;
	}
}
