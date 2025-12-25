package ma.youcode.Al.Baraka.Digital.controller;

import lombok.AllArgsConstructor;
import ma.youcode.Al.Baraka.Digital.dto.response.OperationResponse;
import ma.youcode.Al.Baraka.Digital.enums.OperationStatus;
import ma.youcode.Al.Baraka.Digital.service.interfaces.IOperationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/agent")
@AllArgsConstructor
public class AgentController {

    private   final IOperationService  operationService;

    @GetMapping("/operations/pending")
    ResponseEntity<Page<OperationResponse>> agent(Pageable  pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(operationService.getOperationByStatus(OperationStatus.PENDING,pageable));
    }

}
