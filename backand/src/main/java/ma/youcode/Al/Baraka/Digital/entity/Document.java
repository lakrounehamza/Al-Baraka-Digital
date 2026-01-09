package ma.youcode.Al.Baraka.Digital.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String fileName;
    private String storagePath;
    private String fileType;
    private String extension;
    private LocalDateTime uploaded_at;
    @OneToOne
    @JoinColumn(name = "operation_id")
    @JsonBackReference
    private Operation operation;

    @PrePersist
    private void init() {
        uploaded_at = LocalDateTime.now();
    }
}
