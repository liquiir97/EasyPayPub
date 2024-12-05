package easypay.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link easypay.domain.Uplatnica} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UplatnicaDTO implements Serializable {

    private Long id;

    private String uplatilac;

    private String svrhaUplate;

    private String primalac;

    private String sifraPlacanja;

    private String valuta;

    private Long iznos;

    private String racunPrimaoca;

    private String model;

    private String pozivNaBroj;

    private LocalDate datumKreiranja;

    private Long userId;

    private boolean generatePdf;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUplatilac() {
        return uplatilac;
    }

    public void setUplatilac(String uplatilac) {
        this.uplatilac = uplatilac;
    }

    public String getSvrhaUplate() {
        return svrhaUplate;
    }

    public void setSvrhaUplate(String svrhaUplate) {
        this.svrhaUplate = svrhaUplate;
    }

    public String getPrimalac() {
        return primalac;
    }

    public void setPrimalac(String primalac) {
        this.primalac = primalac;
    }

    public String getSifraPlacanja() {
        return sifraPlacanja;
    }

    public void setSifraPlacanja(String sifraPlacanja) {
        this.sifraPlacanja = sifraPlacanja;
    }

    public String getValuta() {
        return valuta;
    }

    public void setValuta(String valuta) {
        this.valuta = valuta;
    }

    public Long getIznos() {
        return iznos;
    }

    public void setIznos(Long iznos) {
        this.iznos = iznos;
    }

    public String getRacunPrimaoca() {
        return racunPrimaoca;
    }

    public void setRacunPrimaoca(String racunPrimaoca) {
        this.racunPrimaoca = racunPrimaoca;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPozivNaBroj() {
        return pozivNaBroj;
    }

    public void setPozivNaBroj(String pozivNaBroj) {
        this.pozivNaBroj = pozivNaBroj;
    }

    public LocalDate getDatumKreiranja() {
        return datumKreiranja;
    }

    public void setDatumKreiranja(LocalDate datumKreiranja) {
        this.datumKreiranja = datumKreiranja;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isGeneratePdf() {
        return generatePdf;
    }

    public void setGeneratePdf(boolean generatePdf) {
        this.generatePdf = generatePdf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UplatnicaDTO)) {
            return false;
        }

        UplatnicaDTO uplatnicaDTO = (UplatnicaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, uplatnicaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UplatnicaDTO{" +
            "id=" + getId() +
            ", uplatilac='" + getUplatilac() + "'" +
            ", svrhaUplate='" + getSvrhaUplate() + "'" +
            ", primalac='" + getPrimalac() + "'" +
            ", sifraPlacanja='" + getSifraPlacanja() + "'" +
            ", valuta='" + getValuta() + "'" +
            ", iznos=" + getIznos() +
            ", racunPrimaoca='" + getRacunPrimaoca() + "'" +
            ", model='" + getModel() + "'" +
            ", pozivNaBroj='" + getPozivNaBroj() + "'" +
            ", datumKreiranja='" + getDatumKreiranja() + "'" +
            ", userId='" + getUserId() + "'" +
            "}";
    }
}
