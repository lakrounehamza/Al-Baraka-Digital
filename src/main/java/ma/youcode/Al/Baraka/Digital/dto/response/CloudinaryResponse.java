package ma.youcode.Al.Baraka.Digital.dto.response;

import lombok.*;


public record CloudinaryResponse(
        String publicId,
        String url,
        String filename,
        String extension,
        String fileType
) {
}
