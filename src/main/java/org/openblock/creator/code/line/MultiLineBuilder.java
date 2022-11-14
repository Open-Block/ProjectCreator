package org.openblock.creator.code.line;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class MultiLineBuilder implements LineBuilder<MultiLineBuilder> {

	private Collection<CallingLine> lines;

	public Collection<CallingLine> getLines(){
		return this.lines;
	}

	public MultiLineBuilder addLines(Collection<CallingLine> lines){
		this.lines.addAll(lines);
		return this;
	}

	@Override
	public @NotNull Line build() {
		return new MultiLine(lines);
	}
}
