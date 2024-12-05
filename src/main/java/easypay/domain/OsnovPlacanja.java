package easypay.domain;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "osnov_placanja")
public class OsnovPlacanja implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private Long code;

    @Column(name = "name")
    private String name;

    @Column(name = "detail")
    private String detail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "OsnovPlacanja{ " + "id " + getId() + "code " + getCode() + "name " + getName() + "detail " + getDetail();
    }
}
