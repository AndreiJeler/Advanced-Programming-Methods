package Model.Values;

import Model.Types.IType;
import Model.Types.RefType;

public class RefValue implements Value {
    int address;
    IType locationType;
    public RefValue(int addr,IType type){
        address=addr;
        locationType=type;
    }
    @Override
    public IType getType() {
        return new RefType(locationType);
    }

    public int getAddress(){return this.address;}

    @Override
    public Object getValue() {
        return address;
    }

    @Override
    public Value copy() {
        return new RefValue(address,locationType);
    }

    @Override
    public String toString() {
        return "RefValue("+address+","+locationType.toString()+")";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof RefValue && ((RefValue) obj).getAddress()==address;
    }
}
