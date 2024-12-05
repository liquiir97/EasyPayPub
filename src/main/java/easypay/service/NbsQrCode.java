package easypay.service;

import easypay.config.ApplicationProperties;
import easypay.service.dto.NbsQrCodeRequest;
import easypay.service.dto.NbsQrCodeResponse;
import easypay.service.dto.UplatnicaDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class NbsQrCode {

    private static final Logger log = LoggerFactory.getLogger(NbsQrCode.class);

    @Autowired
    ApplicationProperties applicationProperties;

    public NbsQrCode() {}

    public String generateQrCode(UplatnicaDTO uplatnicaDTO) {
        String nbsEndpoint = this.applicationProperties.getNbsEndpoint();

        RestTemplate restTemplate = new RestTemplate();

        NbsQrCodeRequest nbsQrCodeRequest = new NbsQrCodeRequest();

        String requestQrCode = "K:PR|V:01|C:1";

        requestQrCode += addRacunPrimaoca(uplatnicaDTO.getRacunPrimaoca());
        requestQrCode += addNazivPrimaoca(uplatnicaDTO.getPrimalac());
        requestQrCode += addValutaAndIznos(uplatnicaDTO.getValuta(), uplatnicaDTO.getIznos());
        requestQrCode += addPodaciPlatioc(uplatnicaDTO.getUplatilac());
        requestQrCode += addSifraPlacanja(uplatnicaDTO.getSifraPlacanja());
        requestQrCode += addSvrhaPlacanja(uplatnicaDTO.getSvrhaUplate());
        String resultModelAndPozivNaBroj = addModelAndPozivNaBroj(uplatnicaDTO.getModel(), uplatnicaDTO.getPozivNaBroj());
        if (!resultModelAndPozivNaBroj.contains("/")) {
            requestQrCode += resultModelAndPozivNaBroj;
        }

        HttpEntity httpEntity = new HttpEntity<>(requestQrCode);

        ResponseEntity<NbsQrCodeResponse> response = null;
        try {
            response = restTemplate.postForEntity(nbsEndpoint, httpEntity, NbsQrCodeResponse.class);
            int code = response.getStatusCode().value();
            log.info(response.getStatusCode().toString());
            return response.getBody().getI();
        } catch (HttpServerErrorException | HttpClientErrorException e) {
            log.error(e.toString());
            return "Error";
        }
    }

    private String addRacunPrimaoca(String racunPrimaoca) {
        //check if there is "-" and space in racunPrimaoca
        if (racunPrimaoca.contains("-") || racunPrimaoca.contains(" ")) {
            racunPrimaoca.replaceAll("-", "");
            racunPrimaoca.replaceAll(" ", "");
        }

        if (racunPrimaoca.length() < 18) {
            String fixedPartStart = racunPrimaoca.substring(0, 3);
            String fixedPartEnd = racunPrimaoca.substring(racunPrimaoca.length() - 2, racunPrimaoca.length());
            String middlePart = racunPrimaoca.substring(3, racunPrimaoca.length() - 2);
            while (middlePart.length() < 13) {
                middlePart = "0" + middlePart;
            }
            racunPrimaoca = fixedPartStart + middlePart + fixedPartEnd;
        }
        return "|R:" + racunPrimaoca;
    }

    private String addNazivPrimaoca(String nazivPrimaoca) {
        if (nazivPrimaoca.length() > 70) {
            nazivPrimaoca = nazivPrimaoca.substring(0, 70);
        }

        return "|N:" + nazivPrimaoca;
    }

    private String addValutaAndIznos(String valuta, Long iznos) {
        String result = "|I:" + valuta + iznos.toString() + ",";

        return result;
    }

    private String addPodaciPlatioc(String platioc) {
        if (platioc.length() > 70) {
            platioc = platioc.substring(0, 70);
        }

        return "|P:" + platioc;
    }

    private String addSifraPlacanja(String sifraPlacanja) {
        if (!sifraPlacanja.substring(0, 1).equalsIgnoreCase("1") && !sifraPlacanja.substring(0, 1).equalsIgnoreCase("2")) {
            log.error("Sifra placanja mora poceti sa 1 ili 2!");
            log.error("Kako je PK zadat u zahtevu za qr kod!");
            log.error("Vrednost: " + sifraPlacanja);
        }

        return "|SF:" + sifraPlacanja;
    }

    private String addSvrhaPlacanja(String svrhaUplate) {
        if (svrhaUplate.length() > 35) {
            svrhaUplate = svrhaUplate.substring(0, 35);
        }

        return "|S:" + svrhaUplate;
    }

    private String addModelAndPozivNaBroj(String model, String pozivNaBroj) {
        //TODO sve provere dodaj i na front-u

        if (pozivNaBroj == null || pozivNaBroj.isBlank()) {
            model = "/";
            pozivNaBroj = "/";
        } else {
            if (model == null || model.isBlank()) {
                model = "00";
            } else {
                model = model.length() == 1 ? "0" + model : model;
            }

            if ((model.equals("97") || model.equals("11")) && pozivNaBroj.contains("-")) {
                pozivNaBroj = pozivNaBroj.replaceAll("-", "");
            }
        }
        return "|RO:" + model + pozivNaBroj;
    }
}
