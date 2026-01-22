package ma.youcode.Al.Baraka.Digital.controller;

import lombok.AllArgsConstructor;
import ma.youcode.Al.Baraka.Digital.dto.response.DocumentResponse;
import ma.youcode.Al.Baraka.Digital.dto.response.OperationResponse;
import ma.youcode.Al.Baraka.Digital.entity.Operation;
import ma.youcode.Al.Baraka.Digital.enums.OperationStatus;
import ma.youcode.Al.Baraka.Digital.service.interfaces.IDocumentService;
import ma.youcode.Al.Baraka.Digital.service.interfaces.IOperationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/agent")
@AllArgsConstructor

public class AgentController {

    private final IOperationService operationService;
    private final IDocumentService documentService;

    @GetMapping("/operations/pending")
    ResponseEntity<Page<OperationResponse>> getOperationsPending(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(operationService.getOperationByStatus(OperationStatus.PENDING, pageable));
    }

    @GetMapping("/operations/{id}/document")
    ResponseEntity<DocumentResponse> getDocument(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(documentService.getByOperationId(id));
    }

    @PatchMapping("/operations/{id}/approve")
    ResponseEntity<OperationResponse> acceptation(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(operationService.updateStatus(id,OperationStatus.VALIDATED));
    }

    @PatchMapping("/operations/{id}/reject")
    ResponseEntity<OperationResponse> refusation(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(operationService.updateStatus(id,OperationStatus.REJECTED));
    }

}
