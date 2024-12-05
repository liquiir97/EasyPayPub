package easypay.web.rest;

import easypay.domain.OblikPlacanja;
import easypay.domain.OsnovPlacanja;
import easypay.service.ModelPlacanjaService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;

@RestController
@RequestMapping("/v1")
public class ModelPlacanjaResource {

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private static final String ENTITY_NAME = "obpl";

    @Autowired
    ModelPlacanjaService modelPlacanjaService;

    private final Logger log = LoggerFactory.getLogger(ModelPlacanjaResource.class);

    @CrossOrigin(origins = { "http://localhost:9000", "http://localhost:8080" })
    @GetMapping("/find-all-oblik-placanja")
    public ResponseEntity<List<OblikPlacanja>> findAllOblikPlacanja() {
        List<Map<String, ?>> o = modelPlacanjaService.findOneOb();
        List<OblikPlacanja> listOblikPlacanja = modelPlacanjaService.findAllOblikPlacanja();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Access-Control-Allow-Origin", "*");
        return ResponseEntity
            .ok()
            //.headers(httpHeaders)
            .body(listOblikPlacanja); //new ResponseEntity<List<OblikPlacanja>>(listOblikPlacanja, HttpStatus.OK);
    }

    @GetMapping("/find-all-osnov-placanja")
    @CrossOrigin(origins = { "http://localhost:9000", "http://localhost:8080" })
    public ResponseEntity<List<OsnovPlacanja>> findAllOsnovPlacanja() {
        List<OsnovPlacanja> listOsnovPlacanja = modelPlacanjaService.findAllOsnovPlacanja();
        return ResponseEntity.ok(listOsnovPlacanja);
    }
}
