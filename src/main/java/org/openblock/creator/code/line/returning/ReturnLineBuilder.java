package org.openblock.creator.code.line.returning;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.openblock.creator.code.line.CallingLine;
import org.openblock.creator.code.line.LineBuilder;

public class ReturnLineBuilder implements LineBuilder<ReturnLineBuilder> {

	private CallingLine lineToReturn;

	public CallingLine getLine() {
		return this.lineToReturn;
	}

	public ReturnLineBuilder setLine(@Nullable CallingLine line) {
		this.lineToReturn = line;
		return this;
	}

	@Override
	public @NotNull ReturnLine build() {
		return new ReturnLine(this.lineToReturn);
	}
}
