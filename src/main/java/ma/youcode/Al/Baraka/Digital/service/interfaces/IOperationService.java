package ma.youcode.Al.Baraka.Digital.service.interfaces;

import ma.youcode.Al.Baraka.Digital.dto.request.OperationRequest;
import ma.youcode.Al.Baraka.Digital.dto.response.OperationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOperationService {
    OperationResponse save(OperationRequest request);
    Page<OperationResponse>  getAll(Pageable  pageable);
}
