package ma.youcode.Al.Baraka.Digital.mapper;

import ma.youcode.Al.Baraka.Digital.dto.request.DocumentRequest;
import ma.youcode.Al.Baraka.Digital.dto.response.DocumentResponse;
import ma.youcode.Al.Baraka.Digital.entity.Document;
import ma.youcode.Al.Baraka.Digital.entity.Operation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;
@Mapper(componentModel = "spring")
public interface DocumentMapper {

    @Mapping(target = "fileName", expression = "java(request.documentDetails().filename())")
    @Mapping(target = "storagePath", expression = "java(request.documentDetails().url())")
    @Mapping(target = "extension", expression = "java(request.documentDetails().extension())")
    @Mapping(target = "fileType", expression = "java(request.documentDetails().fileType())")
    @Mapping(target = "operation", source = "operation_id")
    Document toEntity(DocumentRequest request);

    DocumentResponse toDto(Document document);

    default Operation map(UUID operation_id) {
        Operation operation = new Operation();
        operation.setId(operation_id);
        return operation;
    }
}
