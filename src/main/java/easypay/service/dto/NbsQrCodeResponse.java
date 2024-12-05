package easypay.service.dto;

public class NbsQrCodeResponse {

    private NbsQrCodeSuccessError s;

    private String t;

    private NbsQrCodeRequest n;

    private String i;

    public NbsQrCodeSuccessError getS() {
        return s;
    }

    public void setS(NbsQrCodeSuccessError s) {
        this.s = s;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public NbsQrCodeRequest getN() {
        return n;
    }

    public void setN(NbsQrCodeRequest n) {
        this.n = n;
    }

    public String getI() {
        return i;
    }

    public void setI(String i) {
        this.i = i;
    }
}
