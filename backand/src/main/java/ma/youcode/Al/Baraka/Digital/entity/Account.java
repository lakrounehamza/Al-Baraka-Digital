package ma.youcode.Al.Baraka.Digital.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import ma.youcode.Al.Baraka.Digital.service.impl.AccountNumerManagment;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    @JsonBackReference
    private User onwer;
    private String numer;
    private BigDecimal balance;
    @OneToMany(mappedBy = "accountSource")
    @JsonBackReference
    List<Operation> operationsSource =new ArrayList<>();
    @OneToMany(mappedBy = "accountDestination")
    @JsonBackReference
    List<Operation> operationsDestination = new ArrayList<>();

    @PrePersist
    private  void  init(){
        balance = BigDecimal.ZERO;
    }
}
