package org.openblock.creator.code.clazz.caller;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.CodeBuilder;
import org.openblock.creator.code.clazz.IClass;

public class StaticClassCallerBuilder implements CodeBuilder<StaticClassCallerBuilder> {

	private IClass clazz;

	public IClass getStaticClass() {
		return this.clazz;
	}

	public StaticClassCallerBuilder setStaticClass(@NotNull IClass clazz) {
		this.clazz = clazz;
		return this;
	}

	@Override
	public StaticClassCaller build() {
		return new StaticClassCaller(this.clazz);
	}
}
