package easypay.web.rest;

import easypay.service.NbsQrCode;
import easypay.service.PdfGeneratorService;
import easypay.service.UplatnicaService;
import easypay.service.dto.UplatnicaDTO;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class PdfGeneratorController {

    private final Logger log = LoggerFactory.getLogger(PdfGeneratorController.class);

    @Autowired
    NbsQrCode nbsQrCode;

    @Autowired
    PdfGeneratorService pdfGeneratorService;

    @Autowired
    UplatnicaService uplatnicaService;

    public PdfGeneratorController() {}

    @CrossOrigin(origins = "http://localhost:9000")
    @GetMapping("/generate-pdf/{id}")
    public ResponseEntity<byte[]> generateSimplePdf(@PathVariable("id") Long id) {
        UplatnicaDTO uplatnicaDTO = new UplatnicaDTO();
        if (id != null) {
            Optional<UplatnicaDTO> uplatnicaDTO1 = uplatnicaService.findOne(id);
            uplatnicaDTO = uplatnicaDTO1.get();
        }
        DateTimeFormatter dateTimeForPdf = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");
        LocalDateTime now = LocalDateTime.now();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "uplatnica_" + dateTimeForPdf.format(now) + ".pdf");
        try {
            byte[] stp = pdfGeneratorService.generateSimplePdf(uplatnicaDTO);
            log.info("PDF download was successful!");
            return ResponseEntity.ok().headers(headers).body(stp);
        } catch (Exception e) {
            log.error("PDF download wasn't successful!");
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().headers(headers).body(null);
        }
    }

    @GetMapping("/get-qr")
    public Boolean generateSimplePdfQr() {
        UplatnicaDTO uplatnicaDTO = new UplatnicaDTO();
        nbsQrCode.generateQrCode(uplatnicaDTO);
        return true;
    }
}
