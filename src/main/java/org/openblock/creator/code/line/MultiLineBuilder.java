package org.openblock.creator.code.line;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.call.Returnable;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class MultiLineBuilder implements LineBuilder<MultiLineBuilder> {

	private final List<CallingLine> lines = new LinkedList<>();

	public Collection<CallingLine> getLines(){
		return this.lines;
	}

	public MultiLineBuilder addLines(Collection<CallingLine> lines){
		this.lines.addAll(lines);
		return this;
	}

	@Override
	public @NotNull Line build() {
		CallingLine line = lines.get(lines.size() - 1);
		if(line instanceof Returnable){
			return new ReturnableMultiLine(this.lines);
		}
		return new MultiLine(lines);
	}
}
