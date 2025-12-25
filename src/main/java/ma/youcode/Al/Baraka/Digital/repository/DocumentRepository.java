package ma.youcode.Al.Baraka.Digital.repository;

import ma.youcode.Al.Baraka.Digital.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DocumentRepository  extends JpaRepository<Document, UUID> {
    Optional<Document> findByOperation_id(UUID operationId);

}
