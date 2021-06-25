package org.openblock.creator.code.statement;

import org.openblock.creator.code.Codeable;

import java.util.List;

public interface Statement extends Codeable {

    List<Codeable> getCodeBlock();
}
