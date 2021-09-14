package org.openblock.creator.code.call;

import java.util.TreeSet;

public interface CallerProvider {

    TreeSet<Caller> getCallers();
}
