package org.personal.mason.fl.utils.convert;

/**
 * Created by mason on 6/29/14.
 */
public enum ConvertType {
    FULL(4),
    FLAT(3),
    NO_BINARY(2),
    KEY_ONLY(1);

    private int priority;

    private ConvertType(int priority){
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
