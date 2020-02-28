package Model.Values;

import Model.Types.IType;
import Model.Types.BoolType;

public class BoolValue implements Value<Boolean> {
    private boolean val;

    public BoolValue(boolean v) {
        val = v;
    }

    public boolean getVal() {
        return val;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return String.valueOf(val);
    }

    public IType getType() {
        return new BoolType();
    }

    @Override
    public Boolean getValue() {
        return val;
    }

    @Override
    public Value<Boolean> copy() {
        return new BoolValue(val);
    }
}
