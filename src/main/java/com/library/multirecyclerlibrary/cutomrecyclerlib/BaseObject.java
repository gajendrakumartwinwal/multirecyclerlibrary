package com.library.multirecyclerlibrary.cutomrecyclerlib;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class BaseObject extends Object implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
