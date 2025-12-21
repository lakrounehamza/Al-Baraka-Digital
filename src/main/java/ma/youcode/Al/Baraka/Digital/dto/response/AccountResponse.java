package ma.youcode.Al.Baraka.Digital.dto.response;

import ma.youcode.Al.Baraka.Digital.entity.Operation;
import ma.youcode.Al.Baraka.Digital.entity.User;

import java.math.BigDecimal;
import java.util.List;

public record AccountResponse(
        UserResponseDto onwer,
        String numer,
        BigDecimal balance,
        List<Operation> operationsSource,
        List<Operation> operationsDestination
) {
}
