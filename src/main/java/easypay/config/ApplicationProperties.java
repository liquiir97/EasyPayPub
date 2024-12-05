package easypay.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Easy Pay.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link tech.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    // jhipster-needle-application-properties-property
    // jhipster-needle-application-properties-property-getter
    // jhipster-needle-application-properties-property-class

    @Value("${nbs.endpoint}")
    private String nbsEndpoint;

    public String getNbsEndpoint() {
        return nbsEndpoint;
    }

    public void setNbsEndpoint(String nbsEndpoint) {
        this.nbsEndpoint = nbsEndpoint;
    }
}
