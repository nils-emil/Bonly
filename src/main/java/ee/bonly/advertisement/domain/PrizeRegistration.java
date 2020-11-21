package ee.bonly.advertisement.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A PrizeRegistration.
 */
@Entity
@Table(name = "prize_registration")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PrizeRegistration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "registation")
    private Instant registation;

    @OneToOne
    @JoinColumn(unique = true)
    private Prize prize;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getRegistation() {
        return registation;
    }

    public PrizeRegistration registation(Instant registation) {
        this.registation = registation;
        return this;
    }

    public void setRegistation(Instant registation) {
        this.registation = registation;
    }

    public Prize getPrize() {
        return prize;
    }

    public PrizeRegistration prize(Prize prize) {
        this.prize = prize;
        return this;
    }

    public void setPrize(Prize prize) {
        this.prize = prize;
    }

    public User getUser() {
        return user;
    }

    public PrizeRegistration user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrizeRegistration)) {
            return false;
        }
        return id != null && id.equals(((PrizeRegistration) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PrizeRegistration{" +
            "id=" + getId() +
            ", registation='" + getRegistation() + "'" +
            "}";
    }
}
