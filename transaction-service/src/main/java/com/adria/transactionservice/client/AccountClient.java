package com.adria.transactionservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.UUID;

@FeignClient(name = "account-service")
public interface AccountClient {
    @GetMapping("/accounts/{id}")
    Map<String, Object> getAccount(@PathVariable UUID id);

    @PutMapping("/accounts/{id}/balance")
    Map<String, Object> updateBalance(@PathVariable UUID id, @RequestParam double balance);
}