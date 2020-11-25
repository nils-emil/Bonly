package ee.bonly.advertisement.service.dto;

import javax.persistence.Lob;
import java.time.Instant;
import java.io.Serializable;

/**
 * A DTO for the {@link ee.bonly.advertisement.domain.Prize} entity.
 */
public class PrizeDTO implements Serializable {

    private Long id;

    private Instant registationStops;

    private Instant winnerChosenAt;

    private Long creditsRequired;

    private String image;

    private String winnerLogin;

    private String imageContentType;

    private String type;

    private Long winnerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getRegistationStops() {
        return registationStops;
    }

    public void setRegistationStops(Instant registationStops) {
        this.registationStops = registationStops;
    }

    public Instant getWinnerChosenAt() {
        return winnerChosenAt;
    }

    public void setWinnerChosenAt(Instant winnerChosenAt) {
        this.winnerChosenAt = winnerChosenAt;
    }

    public Long getCreditsRequired() {
        return creditsRequired;
    }

    public void setCreditsRequired(Long creditsRequired) {
        this.creditsRequired = creditsRequired;
    }

    public Long getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(Long personId) {
        this.winnerId = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrizeDTO)) {
            return false;
        }

        return id != null && id.equals(((PrizeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PrizeDTO{" +
            "id=" + getId() +
            ", registationStops='" + getRegistationStops() + "'" +
            ", winnerChosenAt='" + getWinnerChosenAt() + "'" +
            ", creditsRequired=" + getCreditsRequired() +
            ", winnerId=" + getWinnerId() +
            "}";
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public String getWinnerLogin() {
        return winnerLogin;
    }

    public void setWinnerLogin(String winnerLogin) {
        this.winnerLogin = winnerLogin;
    }
}
