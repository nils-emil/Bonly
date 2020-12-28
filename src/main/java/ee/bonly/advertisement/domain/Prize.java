package ee.bonly.advertisement.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Prize.
 */
@Entity
@Table(name = "prize")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Prize implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "registation_stops")
    private Instant registationStops;

    @Column(name = "credits_required")
    private Long creditsRequired;

    @Column(name = "image_id")
    private Long imageId;

    @Column(name = "type")
    private String type;

    @Column(name = "winner")
    private String winner;

    @Column(name = "title")
    private String title;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getRegistationStops() {
        return registationStops;
    }

    public Prize registationStops(Instant registationStops) {
        this.registationStops = registationStops;
        return this;
    }

    public void setRegistationStops(Instant registationStops) {
        this.registationStops = registationStops;
    }

    public Long getCreditsRequired() {
        return creditsRequired;
    }

    public Prize creditsRequired(Long creditsRequired) {
        this.creditsRequired = creditsRequired;
        return this;
    }

    public void setCreditsRequired(Long creditsRequired) {
        this.creditsRequired = creditsRequired;
    }

    public String getWinner() {
        return winner;
    }

    public Prize winner(String person) {
        this.winner = person;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setWinner(String person) {
        this.winner = person;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Prize)) {
            return false;
        }
        return id != null && id.equals(((Prize) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Prize{" +
            "id=" + getId() +
            ", registationStops='" + getRegistationStops() + "'" +
            ", creditsRequired=" + getCreditsRequired() +
            "}";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }
}
