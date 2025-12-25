package ma.youcode.Al.Baraka.Digital.service.impl;

import lombok.RequiredArgsConstructor;
import ma.youcode.Al.Baraka.Digital.dto.request.DocumentRequest;
import ma.youcode.Al.Baraka.Digital.dto.response.DocumentResponse;
import ma.youcode.Al.Baraka.Digital.entity.Document;
import ma.youcode.Al.Baraka.Digital.mapper.DocumentMapper;
import ma.youcode.Al.Baraka.Digital.mapper.OperationMapper;
import ma.youcode.Al.Baraka.Digital.repository.DocumentRepository;
import ma.youcode.Al.Baraka.Digital.repository.OperationRepository;
import ma.youcode.Al.Baraka.Digital.service.interfaces.IDocumentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class DocumentService implements IDocumentService {

    private final DocumentRepository documentRepository;
    private final OperationRepository operationRepository;
    private final OperationMapper operationMapper;
    private  final CloudinaryService  cloudinaryService;
    private   final DocumentMapper  documentMapper;

    @Override
    public DocumentResponse save(  UUID operation_id, MultipartFile file) {
        DocumentRequest  request  = new DocumentRequest(cloudinaryService.uploadFile(file),operation_id);
       Document document  = documentMapper.toEntity(request);
        Document   documentSaved = documentRepository.save(document);
        return  documentMapper.toDto(documentSaved);
    }

    @Override
    public DocumentResponse getById(UUID id) {
        return null;
    }

    @Override
    public List<DocumentResponse> getByOperationId(UUID operationId) {
        return List.of();
    }

    @Override
    public void delete(UUID id) {

    }

    @Override
    public List<DocumentResponse> getAll() {
        return List.of();
    }


}