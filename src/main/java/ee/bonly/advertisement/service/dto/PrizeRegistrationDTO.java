package ee.bonly.advertisement.service.dto;

import java.time.Instant;
import java.io.Serializable;

/**
 * A DTO for the {@link ee.bonly.advertisement.domain.PrizeRegistration} entity.
 */
public class PrizeRegistrationDTO implements Serializable {
    
    private Long id;

    private Instant registation;


    private Long prizeId;

    private Long userId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getRegistation() {
        return registation;
    }

    public void setRegistation(Instant registation) {
        this.registation = registation;
    }

    public Long getPrizeId() {
        return prizeId;
    }

    public void setPrizeId(Long prizeId) {
        this.prizeId = prizeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long personId) {
        this.userId = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrizeRegistrationDTO)) {
            return false;
        }

        return id != null && id.equals(((PrizeRegistrationDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PrizeRegistrationDTO{" +
            "id=" + getId() +
            ", registation='" + getRegistation() + "'" +
            ", prizeId=" + getPrizeId() +
            ", userId=" + getUserId() +
            "}";
    }
}
