package org.openblock.creator.code;

public interface CodeBuilder<Self extends CodeBuilder<Self>> {

	Codeable build();
}
