package ma.youcode.Al.Baraka.Digital.controller;

import lombok.AllArgsConstructor;
import ma.youcode.Al.Baraka.Digital.dto.request.OperationRequest;
import ma.youcode.Al.Baraka.Digital.dto.response.AccountResponse;
import ma.youcode.Al.Baraka.Digital.dto.response.OperationResponse;
import ma.youcode.Al.Baraka.Digital.service.interfaces.IAccountService;
import ma.youcode.Al.Baraka.Digital.service.interfaces.IDocumentService;
import ma.youcode.Al.Baraka.Digital.service.interfaces.IOperationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/client")
@AllArgsConstructor
public class ClientController {

    private final IAccountService accountService;
    private final IOperationService operationService;
    private final IDocumentService documentService;

    @GetMapping
    ResponseEntity<AccountResponse> client() {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getAccountconucted());
    }

    @PostMapping("operations")
    ResponseEntity<OperationResponse> createOperation(@RequestBody OperationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(operationService.save(request));
    }

    @PostMapping("operations/{id}/document")
    ResponseEntity<?> uplodDonument(@PathVariable UUID id, @RequestParam(name = "file") MultipartFile file) {
        return ResponseEntity.status(HttpStatus.CREATED).body(documentService.save(id, file));
    }

    @GetMapping("/operations")
    public ResponseEntity<Page<OperationResponse>> getAll(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(operationService.getAll(pageable));
    }


}
