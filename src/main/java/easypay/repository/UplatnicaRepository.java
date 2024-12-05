package easypay.repository;

import easypay.domain.Uplatnica;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Uplatnica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UplatnicaRepository extends JpaRepository<Uplatnica, Long> {
    public List<Uplatnica> findAllByUserId(Long userId);

    Page<Uplatnica> findAllByUserId(Pageable pageable, Long userId);
}
