package ee.bonly.advertisement.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A UserAdvertisementAnswers.
 */
@Entity
@Table(name = "user_advertisement_answers")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserAdvertisementAnswers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

//    TODO rename Answers to Answer
    @OneToOne
    @JoinColumn(unique = true)
    private AdvertisementAnswers advertisementAnswer;

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

    public User getUser() {
        return user;
    }

    public UserAdvertisementAnswers user(User person) {
        this.user = person;
        return this;
    }

    public void setUser(User person) {
        this.user = person;
    }

    public Advertisement getAdvertisement() {
        return advertisement;
    }

    public UserAdvertisementAnswers advertisement(Advertisement advertisement) {
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
        if (!(o instanceof UserAdvertisementAnswers)) {
            return false;
        }
        return id != null && id.equals(((UserAdvertisementAnswers) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserAdvertisementAnswers{" +
            "id=" + getId() +
            "}";
    }

    public AdvertisementAnswers getAdvertisementAnswer() {
        return advertisementAnswer;
    }

    public void setAdvertisementAnswer(AdvertisementAnswers advertisementAnswer) {
        this.advertisementAnswer = advertisementAnswer;
    }
}
