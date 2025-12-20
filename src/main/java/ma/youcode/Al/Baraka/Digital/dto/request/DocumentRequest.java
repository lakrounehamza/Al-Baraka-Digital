package ma.youcode.Al.Baraka.Digital.dto.request;

import java.util.UUID;

public record DocumentRequest(
         String fileName,
         String storagePath,
         String fileType,
         UUID operation_id
) {
}
