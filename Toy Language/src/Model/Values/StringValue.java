package Model.Values;

import Model.Types.IType;
import Model.Types.StringType;

public class StringValue implements Value<String> {
    private String value;

    public StringValue(String s) {
        value = s;
    }
    public String getVal() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public IType getType() {
        return new StringType();
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public Value<String> copy() {
        return new StringValue(value);
    }
}
