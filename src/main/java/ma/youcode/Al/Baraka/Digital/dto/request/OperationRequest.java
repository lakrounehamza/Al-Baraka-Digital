package ma.youcode.Al.Baraka.Digital.dto.request;

import ma.youcode.Al.Baraka.Digital.enums.OperationType;

import java.math.BigDecimal;

public record OperationRequest(
        OperationType type,
        BigDecimal amount,
        Long accountSource_id,
        Long accountDestination_id
) {
}
