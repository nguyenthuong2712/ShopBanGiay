package org.example.webbangiay.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductSearchForm {

    private int page;

    private int size;
}
