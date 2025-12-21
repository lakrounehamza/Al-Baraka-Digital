package ma.youcode.Al.Baraka.Digital.dto.response;

import jakarta.persistence.OneToOne;
import ma.youcode.Al.Baraka.Digital.entity.Operation;

import java.time.LocalDateTime;
import java.util.UUID;

public record DocumentResponse(
        UUID id,
        String fileName,
        String storagePath,
        String fileType,
        LocalDateTime uploaded_at,
        OperationResponse operation
) {
}
