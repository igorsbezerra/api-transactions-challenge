package dev.igor.apitransactions.client;

import dev.igor.apitransactions.config.AccountFeignClientConfig;
import dev.igor.apitransactions.dto.AccountDTO;
import dev.igor.apitransactions.dto.AvailableAccount;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "account", url = "${services.account.url}", configuration = AccountFeignClientConfig.class)
public interface AccountClient {
    @RequestMapping(method = RequestMethod.GET, value = "/accounts/{accountCode}")
    AccountDTO findByAccountCode(@PathVariable("accountCode") String accountCode);

    @RequestMapping(method = RequestMethod.GET, value = "/accounts/available-balance")
    AvailableAccount availableBalance(@RequestParam("accountCode") String accountCode,
                                      @RequestParam("amount") String amount);
}
