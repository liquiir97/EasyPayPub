package easypay.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import easypay.EasyPayApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NbsQrCodeRequest {

    private String K;

    private String V;

    private String C;

    private String R;

    private String N;

    private String I;

    private String P;

    private String SF;

    private String S;

    private String RO;

    @JsonProperty("k")
    private String k;

    @JsonProperty("v")
    private String v;

    @JsonProperty("c")
    private String c;

    @JsonProperty("r")
    private String r;

    @JsonProperty("n")
    private String n;

    @JsonProperty("i")
    private String i;

    @JsonProperty("p")
    private String p;

    @JsonProperty("sf")
    private String sf;

    @JsonProperty("s")
    private String s;

    @JsonProperty("ro")
    private String ro;

    public String getK() {
        return K;
    }

    public void setK(String k) {
        K = k;
    }

    public String getV() {
        return V;
    }

    public void setV(String v) {
        V = v;
    }

    public String getC() {
        return C;
    }

    public void setC(String c) {
        C = c;
    }

    public String getR() {
        return R;
    }

    public void setR(String r) {
        R = r;
    }

    public String getN() {
        return N;
    }

    public void setN(String n) {
        N = n;
    }

    public String getI() {
        return I;
    }

    public void setI(String i) {
        I = i;
    }

    public String getP() {
        return P;
    }

    public void setP(String p) {
        P = p;
    }

    public String getSF() {
        return SF;
    }

    public void setSF(String SF) {
        this.SF = SF;
    }

    public String getS() {
        return S;
    }

    public void setS(String s) {
        S = s;
    }

    public String getRO() {
        return RO;
    }

    public void setRO(String RO) {
        this.RO = RO;
    }
}
