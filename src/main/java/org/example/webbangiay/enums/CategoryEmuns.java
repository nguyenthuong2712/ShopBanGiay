package org.example.webbangiay.enums;

public enum CategoryEmuns {
    SU_DUNG(1),
    NGUNG_SU_DUNG(2);

    private final int value;

     CategoryEmuns(int value) {
        this.value = value;
    }
    public int getValue() {
         return value;
    }
}
