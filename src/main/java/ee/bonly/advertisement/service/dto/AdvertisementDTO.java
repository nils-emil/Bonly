package ee.bonly.advertisement.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Lob;

/**
 * A DTO for the {@link ee.bonly.advertisement.domain.Advertisement} entity.
 */
public class AdvertisementDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant activeFrom;

    @NotNull
    private Instant activeUntill;

    private String image;

    private Long imageId;

    private List<AdvertisementAnswersDTO> advertisementAnswers;

    private String imageContentType;
    @NotNull
    private String question;

    private Long creditCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getActiveFrom() {
        return activeFrom;
    }

    public void setActiveFrom(Instant activeFrom) {
        this.activeFrom = activeFrom;
    }

    public Instant getActiveUntill() {
        return activeUntill;
    }

    public void setActiveUntill(Instant activeUntill) {
        this.activeUntill = activeUntill;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdvertisementDTO)) {
            return false;
        }

        return id != null && id.equals(((AdvertisementDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdvertisementDTO{" +
            "id=" + getId() +
            ", activeFrom='" + getActiveFrom() + "'" +
            ", activeUntill='" + getActiveUntill() + "'" +
            ", image='" + getImage() + "'" +
            ", question='" + getQuestion() + "'" +
            "}";
    }

    public List<AdvertisementAnswersDTO> getAdvertisementAnswers() {
        return advertisementAnswers;
    }

    public void setAdvertisementAnswers(List<AdvertisementAnswersDTO> advertisementAnswers) {
        this.advertisementAnswers = advertisementAnswers;
    }

    public Long getCreditCount() {
        return creditCount;
    }

    public void setCreditCount(Long creditCount) {
        this.creditCount = creditCount;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }
}
