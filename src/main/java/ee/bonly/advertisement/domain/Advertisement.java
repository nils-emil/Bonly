package ee.bonly.advertisement.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

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

    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="advertisement")
    private List<AdvertisementAnswers> advertisementAnswers;

    @Column(name = "credit_count", nullable = false)
    private Long creditCount;

    @Column(name = "image_id", nullable = false)
    private Long imageId;

    @NotNull
    @Column(name = "question", nullable = false)
    private String question;

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
            ", question='" + getQuestion() + "'" +
            "}";
    }

    public List<AdvertisementAnswers> getAdvertisementAnswers() {
        return advertisementAnswers;
    }

    public void setAdvertisementAnswers(List<AdvertisementAnswers> advertisementAnswers) {
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
