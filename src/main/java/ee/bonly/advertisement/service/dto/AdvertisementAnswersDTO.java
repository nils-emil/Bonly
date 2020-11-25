package ee.bonly.advertisement.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link ee.bonly.advertisement.domain.AdvertisementAnswers} entity.
 */
public class AdvertisementAnswersDTO implements Serializable {

    private Long id;

    private String answer;

    private Boolean correct;

    private Long advertisementId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
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
        if (!(o instanceof AdvertisementAnswersDTO)) {
            return false;
        }

        return id != null && id.equals(((AdvertisementAnswersDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdvertisementAnswersDTO{" +
            "id=" + getId() +
            ", answer='" + getAnswer() + "'" +
            ", correct='" + getCorrect() + "'" +
            ", advertisementId=" + getAdvertisementId() +
            "}";
    }

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }
}
