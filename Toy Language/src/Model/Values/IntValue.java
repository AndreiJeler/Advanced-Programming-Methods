package Model.Values;

import Model.Types.IType;
import Model.Types.IntType;

public class IntValue implements Value<Integer>{
    private int val;
    public IntValue(int v){val=v;}

    public int getVal() {return val;}

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return String.valueOf(val);
    }

    public IType getType() { return new IntType();}

    @Override
    public Integer getValue() {
        return val;
    }

    @Override
    public Value<Integer> copy() {
        return new IntValue(val);
    }
}
