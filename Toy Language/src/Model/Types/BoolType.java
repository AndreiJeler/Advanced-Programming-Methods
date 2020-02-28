package Model.Types;

import Model.Values.BoolValue;
import Model.Values.Value;

public class BoolType implements IType {
    @Override
    public boolean equals(Object obj) {
        return obj instanceof BoolType;
    }

    @Override
    public String toString() {
        return "bool";
    }

    @Override
    public Value defaultValue() {
        return new BoolValue(false);
    }
}
