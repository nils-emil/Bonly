package ee.bonly.advertisement.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
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

    
    @Lob
    private byte[] image;

    private String imageContentType;
    @NotNull
    private String question;


    private Long correctAnswerId;
    
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
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

    public Long getCorrectAnswerId() {
        return correctAnswerId;
    }

    public void setCorrectAnswerId(Long advertisementAnswersId) {
        this.correctAnswerId = advertisementAnswersId;
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
            ", correctAnswerId=" + getCorrectAnswerId() +
            "}";
    }
}
