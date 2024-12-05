package easypay.repository;

import easypay.domain.OsnovPlacanja;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OsnovPlacanjaRepository extends JpaRepository<OsnovPlacanja, Long> {}
