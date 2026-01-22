package ma.youcode.Al.Baraka.Digital.service.interfaces;

import ma.youcode.Al.Baraka.Digital.dto.request.DocumentRequest;
import ma.youcode.Al.Baraka.Digital.dto.response.DocumentResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;


public interface IDocumentService {
    DocumentResponse save(UUID operation_id,MultipartFile file);
    DocumentResponse getById(UUID id);
     DocumentResponse getByOperationId(UUID operationId);
    void delete(UUID id);
    List<DocumentResponse> getAll();
}
