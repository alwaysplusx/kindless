package com.kindless.apis.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wuxin
 */
@Builder
@Data
public class PaymentDto {

    private Long orderId;
    private String orderType;
    private BigDecimal amount;
    private String balanceType;

}
