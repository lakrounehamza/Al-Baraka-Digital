package ma.youcode.Al.Baraka.Digital.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.youcode.Al.Baraka.Digital.enums.OperationStatus;
import ma.youcode.Al.Baraka.Digital.enums.OperationType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private OperationType type;
    private BigDecimal amount;
    private OperationStatus status;
    private LocalDateTime created_at;
    private LocalDateTime validated_at;
    @ManyToOne
    @JoinColumn(name = "accountSource_id")
    private Account accountSource;
    @ManyToOne
    @JoinColumn(name = "accountDestination_id")
    private Account accountDestination;

    @PrePersist
    private void init() {
        created_at = LocalDateTime.now();
        status = OperationStatus.PENDING;
    }
}
