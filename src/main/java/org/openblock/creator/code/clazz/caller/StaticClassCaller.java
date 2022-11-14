package org.openblock.creator.code.clazz.caller;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.call.Caller;
import org.openblock.creator.code.clazz.IClass;
import org.openblock.creator.code.line.CallingLine;
import org.openblock.creator.utils.OpenStringUtils;

import java.util.SortedSet;
import java.util.TreeSet;

public class StaticClassCaller implements CallingLine {

	private final IClass clazz;

	public StaticClassCaller(@NotNull IClass clazz) {
		this.clazz = clazz;
	}

	@Override
	public @NotNull String writeCode(int indent) {
		return OpenStringUtils.repeat(indent, "\t") + this.clazz.getName();
	}

	@Override
	public @NotNull SortedSet<IClass> getImports() {
		SortedSet<IClass> set = new TreeSet<>();
		set.add(this.clazz);
		return set;
	}

	@Override
	public @NotNull StaticClassCallerBuilder toBuilder() {
		return new StaticClassCallerBuilder().setStaticClass(this.clazz);
	}

	public IClass getTargetClass() {
		return this.clazz;
	}

	@Override
	public TreeSet<Caller> getCallers() {
		return this.clazz.getStaticCallers();
	}
}
