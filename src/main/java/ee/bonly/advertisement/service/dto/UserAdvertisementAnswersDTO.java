package ee.bonly.advertisement.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link ee.bonly.advertisement.domain.UserAdvertisementAnswers} entity.
 */
public class UserAdvertisementAnswersDTO implements Serializable {

    private Long id;

    private Long userId;

    private Long advertisementId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long personId) {
        this.userId = personId;
    }

    public Long getAdvertisementId() {
        return advertisementId;
    }

    public void setAdvertisementId(Long advertisementId) {
        this.advertisementId = advertisementId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserAdvertisementAnswersDTO)) {
            return false;
        }

        return id != null && id.equals(((UserAdvertisementAnswersDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserAdvertisementAnswersDTO{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", advertisementId=" + getAdvertisementId() +
            "}";
    }
}
