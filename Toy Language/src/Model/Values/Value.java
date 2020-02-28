package Model.Values;

import Model.Types.IType;

public interface Value<T> {
    public IType getType();
    T getValue();
    Value<T> copy();
}
