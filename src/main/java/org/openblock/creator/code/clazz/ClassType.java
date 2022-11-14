package org.openblock.creator.code.clazz;

import org.jetbrains.annotations.Nullable;

public enum ClassType {

	STANDARD("class"),
	INTERFACE,
	ENUM,
	ANNOTATION,
	RECORD;

	private final @Nullable String codeReference;

	ClassType() {
		this(null);
	}

	ClassType(@Nullable String codeReference) {
		this.codeReference = codeReference;
	}

	public String getCodeReference() {
		if (this.codeReference == null) {
			return this.name().toLowerCase();
		}
		return this.codeReference;
	}
}
