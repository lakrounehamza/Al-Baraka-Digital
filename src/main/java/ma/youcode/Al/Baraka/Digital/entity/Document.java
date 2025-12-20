package ma.youcode.Al.Baraka.Digital.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String fileName;
    private String storagePath;
    private String fileType;
    private LocalDateTime uploaded_at;
    @OneToOne
    private Operation operation;

    @PrePersist
    private void init() {
        uploaded_at = LocalDateTime.now();
    }
}
