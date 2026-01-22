package ma.youcode.Al.Baraka.Digital.service.interfaces;

import ma.youcode.Al.Baraka.Digital.dto.request.OperationRequest;
import ma.youcode.Al.Baraka.Digital.dto.response.OperationResponse;
import ma.youcode.Al.Baraka.Digital.enums.OperationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IOperationService {
    OperationResponse save(OperationRequest request);
    Page<OperationResponse>  getAll(Pageable  pageable);
    Page<OperationResponse>   getOperationByStatus(OperationStatus status , Pageable  pageable);
    OperationResponse updateStatus(UUID  id ,OperationStatus status);
}
