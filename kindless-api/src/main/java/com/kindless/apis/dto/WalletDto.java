package com.kindless.apis.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wuxin
 */
@Data
public class WalletDto {

    private Long userId;
    private BigDecimal amount;

}
