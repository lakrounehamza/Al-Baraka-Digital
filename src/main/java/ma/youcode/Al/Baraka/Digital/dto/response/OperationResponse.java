package ma.youcode.Al.Baraka.Digital.dto.response;

import ma.youcode.Al.Baraka.Digital.entity.Account;
import ma.youcode.Al.Baraka.Digital.enums.OperationStatus;
import ma.youcode.Al.Baraka.Digital.enums.OperationType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record OperationResponse(
         UUID id  ,
         OperationType type,
         BigDecimal amount,
         OperationStatus status,
         LocalDateTime created_at,
         LocalDateTime  validated_at ,
         AccountResponse accountSource,
         AccountResponse accountDestination
) {
}
