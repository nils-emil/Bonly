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

    @Column(name = "winner_chosen_at")
    private Instant winnerChosenAt;

    @Column(name = "credits_required")
    private Long creditsRequired;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "image_content_type", nullable = false)
    private String imageContentType;

    @Column(name = "type")
    private String type;

    @OneToOne
    @JoinColumn(unique = true)
    private User winner;

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

    public Instant getWinnerChosenAt() {
        return winnerChosenAt;
    }

    public Prize winnerChosenAt(Instant winnerChosenAt) {
        this.winnerChosenAt = winnerChosenAt;
        return this;
    }

    public void setWinnerChosenAt(Instant winnerChosenAt) {
        this.winnerChosenAt = winnerChosenAt;
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

    public User getWinner() {
        return winner;
    }

    public Prize winner(User person) {
        this.winner = person;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setWinner(User person) {
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
            ", winnerChosenAt='" + getWinnerChosenAt() + "'" +
            ", creditsRequired=" + getCreditsRequired() +
            "}";
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }
}
