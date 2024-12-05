package easypay.repository;

import easypay.domain.OblikPlacanja;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OblikPlacanjaRepository extends JpaRepository<OblikPlacanja, Long> {
    @Query(value = "SELECT p.* FROM oblik_placanja p", nativeQuery = true)
    List<Map<String, ?>> findObOne();
}
