package org.openblock.creator.code.line.primitive;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.line.Line;
import org.openblock.creator.code.line.LineBuilder;

public class PrimitiveValueBuilder implements LineBuilder<PrimitiveValueBuilder> {

	private Object value;

	public Object getValue() {
		return this.value;
	}

	public PrimitiveValueBuilder setValue(@NotNull Object value) {
		if (value.getClass().isPrimitive()) {
			this.value = value;
			return this;
		}
		if (value instanceof String) {
			this.value = value;
		}
		throw new RuntimeException("Value is not a primitive value");
	}

	@Override
	public @NotNull Line build() {
		if (value instanceof String str) {
			return new StringConstructor(str);
		}
		throw new RuntimeException(this.getValue().getClass().getSimpleName() + " is not implemented yet");
	}
}
