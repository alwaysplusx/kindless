package com.kindless.apis.client;

import com.harmony.umbrella.web.Response;
import com.kindless.apis.dto.PaymentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;

/**
 * @author wuxin
 */
@FeignClient(name = "kindless-user", path = "/wallet")
public interface WalletClient {

    @PostMapping("/pay")
    Response<BigDecimal> pay(PaymentDto payment);

}
