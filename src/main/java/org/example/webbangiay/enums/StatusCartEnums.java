package org.example.webbangiay.enums;

public enum StatusCartEnums {
    CHUA_CO_SAN_PHAM(1),   // chưa có sản phẩm
    DANG_CO_SAN_PHAM(2),   // trong giỏ đang có sản phẩm
    DA_THANH_TOAN(3),      // đã thanh toán
    HET_HAN(4),            // hết hạn
    DA_HUY(5);             // đã hủy

    private final int value;

    StatusCartEnums(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static StatusCartEnums fromValue(int value) {
        for (StatusCartEnums status : values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid StatusCartEnums value: " + value);
    }
}
