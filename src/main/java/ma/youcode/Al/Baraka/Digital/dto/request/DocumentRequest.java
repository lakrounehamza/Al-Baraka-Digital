package ma.youcode.Al.Baraka.Digital.dto.request;

import ma.youcode.Al.Baraka.Digital.dto.response.CloudinaryResponse;

import java.util.UUID;

public record DocumentRequest(
        CloudinaryResponse  documentDetails ,
         UUID operation_id
) {
}
