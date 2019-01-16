package dz_spring7.model;

import java.util.StringJoiner;

public enum CategoryType {

    SALE,
    BUY

    /*SALE("sale"),
    BUY("buy");

    private final String value;

    CategoryType(final String type){
        value = type;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CategoryType.class.getSimpleName() + "[", "]")
                .add("value='" + value + "'")
                .toString();
    }*/
}
