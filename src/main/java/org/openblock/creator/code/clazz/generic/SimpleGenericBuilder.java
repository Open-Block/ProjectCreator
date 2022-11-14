package org.openblock.creator.code.clazz.generic;

import org.jetbrains.annotations.Nullable;
import org.openblock.creator.code.CodeBuilder;
import org.openblock.creator.code.Codeable;
import org.openblock.creator.code.clazz.type.IType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SimpleGenericBuilder implements CodeBuilder<SimpleGenericBuilder> {

	private String name;
	private boolean extended;
	private List<IType> classes = new ArrayList<>();

	public String getName(){
		return this.name;
	}

	public SimpleGenericBuilder setName(@Nullable String name){
		this.name = name;
		return this;
	}

	public boolean isExtending() {
		return this.extended;
	}

	public boolean isSupering() {
		return !this.extended;
	}

	public SimpleGenericBuilder setExtending(boolean check) {
		this.extended = check;
		return this;
	}

	public SimpleGenericBuilder setExtending() {
		this.extended = true;
		return this;
	}

	public SimpleGenericBuilder setSupering() {
		this.extended = false;
		return this;
	}

	public List<IType> getTypes() {
		return this.classes;
	}

	public SimpleGenericBuilder addTypes(Collection<IType> collection) {
		this.classes.addAll(collection);
		return this;
	}

	@Override
	public Codeable build() {
		if (name == null) {
			return new UnknownGeneric(this);
		}
		return new CollectedGeneric(this);
	}
}
