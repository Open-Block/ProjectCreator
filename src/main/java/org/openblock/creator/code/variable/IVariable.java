package org.openblock.creator.code.variable;

import org.openblock.creator.code.Codeable;
import org.openblock.creator.code.Nameable;
import org.openblock.creator.code.call.Returnable;

public interface IVariable extends Nameable, Codeable, Returnable {

    boolean isFinal();

}
