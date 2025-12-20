package ma.youcode.Al.Baraka.Digital.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import ma.youcode.Al.Baraka.Digital.enums.UserRole;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String username;
    private String email;
    private UserRole role;
    private boolean active;
    private LocalDateTime created_at;
    private String password;

    @PrePersist
    private void init() {
        created_at = LocalDateTime.now();
        active = true;
    }
}
