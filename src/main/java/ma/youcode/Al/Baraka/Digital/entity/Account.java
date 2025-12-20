package ma.youcode.Al.Baraka.Digital.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @OneToOne
    private User onwer;
    private String numer;
    private BigDecimal balance;
    @OneToMany(mappedBy = "accountSource_id")
    List<Operation> operationsSource;
    @OneToMany(mappedBy = "accountDestination_id")
    List<Operation> operationsDestination;
}
