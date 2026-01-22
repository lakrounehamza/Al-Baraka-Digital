package ma.youcode.Al.Baraka.Digital.repository;

import ma.youcode.Al.Baraka.Digital.entity.Operation;
import ma.youcode.Al.Baraka.Digital.enums.OperationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OperationRepository extends JpaRepository<Operation, UUID> {
    Page<Operation> findByAccountSource_Id(UUID accountSourceId, Pageable pageable);
    Page<Operation> findByStatus(OperationStatus status, Pageable pageable);
}
