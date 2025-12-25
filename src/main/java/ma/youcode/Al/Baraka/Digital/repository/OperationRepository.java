package ma.youcode.Al.Baraka.Digital.repository;

import ma.youcode.Al.Baraka.Digital.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OperationRepository extends JpaRepository<Operation, UUID> {
}
