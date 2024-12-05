package easypay.service;

import easypay.domain.OblikPlacanja;
import easypay.domain.OsnovPlacanja;
import easypay.repository.OblikPlacanjaRepository;
import easypay.repository.OsnovPlacanjaRepository;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelPlacanjaService {

    @Autowired
    OsnovPlacanjaRepository osnovPlacanjaRepository;

    @Autowired
    OblikPlacanjaRepository oblikPlacanjaRepository;

    public List<OsnovPlacanja> findAllOsnovPlacanja() {
        return this.osnovPlacanjaRepository.findAll();
    }

    public List<OblikPlacanja> findAllOblikPlacanja() {
        return this.oblikPlacanjaRepository.findAll();
    }

    public List<Map<String, ?>> findOneOb() {
        return this.oblikPlacanjaRepository.findObOne();
    }
}
