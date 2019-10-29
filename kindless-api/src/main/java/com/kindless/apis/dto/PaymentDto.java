package com.kindless.apis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author wuxin
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PaymentDto {

    private Long orderId;
    private String orderType;
    private BigDecimal amount;
    private String balanceType;

}
