package ee.bonly.advertisement.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Advertisement.
 */
@Entity
@Table(name = "advertisement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Advertisement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "active_from", nullable = false)
    private Instant activeFrom;

    @NotNull
    @Column(name = "active_untill", nullable = false)
    private Instant activeUntill;

    
    @Lob
    @Column(name = "image", nullable = false)
    private byte[] image;

    @Column(name = "image_content_type", nullable = false)
    private String imageContentType;

    @NotNull
    @Column(name = "question", nullable = false)
    private String question;

    @OneToOne
    @JoinColumn(unique = true)
    private AdvertisementAnswers correctAnswer;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getActiveFrom() {
        return activeFrom;
    }

    public Advertisement activeFrom(Instant activeFrom) {
        this.activeFrom = activeFrom;
        return this;
    }

    public void setActiveFrom(Instant activeFrom) {
        this.activeFrom = activeFrom;
    }

    public Instant getActiveUntill() {
        return activeUntill;
    }

    public Advertisement activeUntill(Instant activeUntill) {
        this.activeUntill = activeUntill;
        return this;
    }

    public void setActiveUntill(Instant activeUntill) {
        this.activeUntill = activeUntill;
    }

    public byte[] getImage() {
        return image;
    }

    public Advertisement image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Advertisement imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public String getQuestion() {
        return question;
    }

    public Advertisement question(String question) {
        this.question = question;
        return this;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public AdvertisementAnswers getCorrectAnswer() {
        return correctAnswer;
    }

    public Advertisement correctAnswer(AdvertisementAnswers advertisementAnswers) {
        this.correctAnswer = advertisementAnswers;
        return this;
    }

    public void setCorrectAnswer(AdvertisementAnswers advertisementAnswers) {
        this.correctAnswer = advertisementAnswers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Advertisement)) {
            return false;
        }
        return id != null && id.equals(((Advertisement) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Advertisement{" +
            "id=" + getId() +
            ", activeFrom='" + getActiveFrom() + "'" +
            ", activeUntill='" + getActiveUntill() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", question='" + getQuestion() + "'" +
            "}";
    }
}
