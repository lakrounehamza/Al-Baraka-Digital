package ma.youcode.Al.Baraka.Digital.controller;

import lombok.AllArgsConstructor;
import ma.youcode.Al.Baraka.Digital.dto.response.AccountResponse;
import ma.youcode.Al.Baraka.Digital.service.interfaces.IAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/client")
@AllArgsConstructor
public class ClientController {

    private final IAccountService accountService;
    @GetMapping
    ResponseEntity<AccountResponse> client(){
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getAccountconucted());
    }
}
