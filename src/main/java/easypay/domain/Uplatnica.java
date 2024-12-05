package easypay.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

/**
 * A Uplatnica.
 */
@Entity
@Table(name = "uplatnica")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Uplatnica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "uplatilac")
    private String uplatilac;

    @Column(name = "svrha_uplate")
    private String svrhaUplate;

    @Column(name = "primalac")
    private String primalac;

    @Column(name = "sifra_placanja")
    private String sifraPlacanja;

    @Column(name = "valuta")
    private String valuta;

    @Column(name = "iznos")
    private Long iznos;

    @Column(name = "racun_primaoca")
    private String racunPrimaoca;

    @Column(name = "model")
    private String model;

    @Column(name = "poziv_na_broj")
    private String pozivNaBroj;

    @Column(name = "datum_kreiranja")
    private LocalDate datumKreiranja;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "favourite")
    private boolean favourite;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Uplatnica id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUplatilac() {
        return this.uplatilac;
    }

    public Uplatnica uplatilac(String uplatilac) {
        this.setUplatilac(uplatilac);
        return this;
    }

    public void setUplatilac(String uplatilac) {
        this.uplatilac = uplatilac;
    }

    public String getSvrhaUplate() {
        return this.svrhaUplate;
    }

    public Uplatnica svrhaUplate(String svrhaUplate) {
        this.setSvrhaUplate(svrhaUplate);
        return this;
    }

    public void setSvrhaUplate(String svrhaUplate) {
        this.svrhaUplate = svrhaUplate;
    }

    public String getPrimalac() {
        return this.primalac;
    }

    public Uplatnica primalac(String primalac) {
        this.setPrimalac(primalac);
        return this;
    }

    public void setPrimalac(String primalac) {
        this.primalac = primalac;
    }

    public String getSifraPlacanja() {
        return this.sifraPlacanja;
    }

    public Uplatnica sifraPlacanja(String sifraPlacanja) {
        this.setSifraPlacanja(sifraPlacanja);
        return this;
    }

    public void setSifraPlacanja(String sifraPlacanja) {
        this.sifraPlacanja = sifraPlacanja;
    }

    public String getValuta() {
        return this.valuta;
    }

    public Uplatnica valuta(String valuta) {
        this.setValuta(valuta);
        return this;
    }

    public void setValuta(String valuta) {
        this.valuta = valuta;
    }

    public Long getIznos() {
        return this.iznos;
    }

    public Uplatnica iznos(Long iznos) {
        this.setIznos(iznos);
        return this;
    }

    public void setIznos(Long iznos) {
        this.iznos = iznos;
    }

    public String getRacunPrimaoca() {
        return this.racunPrimaoca;
    }

    public Uplatnica racunPrimaoca(String racunPrimaoca) {
        this.setRacunPrimaoca(racunPrimaoca);
        return this;
    }

    public void setRacunPrimaoca(String racunPrimaoca) {
        this.racunPrimaoca = racunPrimaoca;
    }

    public String getModel() {
        return this.model;
    }

    public Uplatnica model(String model) {
        this.setModel(model);
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPozivNaBroj() {
        return this.pozivNaBroj;
    }

    public Uplatnica pozivNaBroj(String pozivNaBroj) {
        this.setPozivNaBroj(pozivNaBroj);
        return this;
    }

    public void setPozivNaBroj(String pozivNaBroj) {
        this.pozivNaBroj = pozivNaBroj;
    }

    public LocalDate getDatumKreiranja() {
        return this.datumKreiranja;
    }

    public Uplatnica datumKreiranja(LocalDate datumKreiranja) {
        this.setDatumKreiranja(datumKreiranja);
        return this;
    }

    public void setDatumKreiranja(LocalDate datumKreiranja) {
        this.datumKreiranja = datumKreiranja;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Uplatnica)) {
            return false;
        }
        return id != null && id.equals(((Uplatnica) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Uplatnica{" +
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
            ", favourite='" + isFavourite() + "'" +
            "}";
    }
}
