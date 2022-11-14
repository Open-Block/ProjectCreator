package org.openblock.creator.code.variable.parameter;

import org.openblock.creator.code.call.returntype.ReturnType;
import org.openblock.creator.code.variable.VariableBuilder;

public class ParameterBuilder implements VariableBuilder<ParameterBuilder> {

	private ReturnType type;
	private String name;
	private boolean isFinal;

	@Override
	public Parameter build() {
		return new Parameter(this);
	}

	@Override
	public ReturnType getReturnType() {
		return this.type;
	}

	@Override
	public ParameterBuilder setReturnType(ReturnType type) {
		this.type = type;
		return this;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public ParameterBuilder setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public boolean isFinal() {
		return this.isFinal;
	}

	@Override
	public ParameterBuilder setFinal(boolean fin) {
		this.isFinal = fin;
		return this;
	}
}
