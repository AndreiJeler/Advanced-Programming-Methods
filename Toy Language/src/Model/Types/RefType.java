package Model.Types;

import Model.Values.RefValue;
import Model.Values.Value;

public class RefType implements IType {
    IType inner;
    public RefType(IType inn){
        inner=inn;
    }

    public IType getInner(){
        return inner;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RefType)
            return inner.equals(((RefType) obj).getInner());
        else
            return false;
    }

    @Override
    public String toString() {
        return "Ref("+inner.toString()+")";
    }

    @Override
    public Value defaultValue() {
        return new RefValue(0,inner);
    }
}
