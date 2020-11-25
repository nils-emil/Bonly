package ee.bonly.advertisement.domain;

import liquibase.pro.packaged.B;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A AdvertisementAnswers.
 */
@Entity
@Table(name = "advertisement_answers")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdvertisementAnswers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "answer")
    private String answer;

    @Column(name = "correct")
    private Boolean correct;

    @OneToOne
    @JoinColumn(unique = true)
    private Advertisement advertisement;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public AdvertisementAnswers answer(String answer) {
        this.answer = answer;
        return this;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Advertisement getAdvertisement() {
        return advertisement;
    }

    public AdvertisementAnswers advertisement(Advertisement advertisement) {
        this.advertisement = advertisement;
        return this;
    }

    public void setAdvertisement(Advertisement advertisement) {
        this.advertisement = advertisement;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdvertisementAnswers)) {
            return false;
        }
        return id != null && id.equals(((AdvertisementAnswers) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdvertisementAnswers{" +
            "id=" + getId() +
            ", answer='" + getAnswer() + "'" +
            ", correct='" + getCorrect() + "'" +
            "}";
    }

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }
}
