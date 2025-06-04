package org.example.webbangiay.enums;

public enum StatusCartDetailEnums {
    DANG_CO_SAN_PHAM(1),   //  đang có sản phẩm trong giỏ
    DA_THANH_TOAN(2),      //  đã thanh toán (nếu bạn dùng để track từng item)
    HET_HAN(3),            //  hết hạn
    DA_HUY(4);             //  đã hủy

    private final int value;

    StatusCartDetailEnums(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static StatusCartDetailEnums fromValue(int value) {
        for (StatusCartDetailEnums status : values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid StatusCartDetailEnums value: " + value);
    }
}
