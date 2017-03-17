package by.alexeypuplikov.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "voting_option")
public class VotingOption {
    @Id
    @Column(name = "VOTING_OPTION_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "OPTION_TEXT")
    private String optionText;

    @JsonIgnore
    @ManyToOne()
    private Voting voting;

    @OneToMany(mappedBy = "votingOption")
    private Set<Vote> votes;

    public VotingOption() {
    }

    public VotingOption(String optionText) {
        this.optionText = optionText;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public Voting getVoting() {
        return voting;
    }

    public void setVoting(Voting voting) {
        this.voting = voting;
    }

    public Set<Vote> getVotes() {
        return votes;
    }

    public void setVotes(Set<Vote> votes) {
        this.votes = votes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VotingOption that = (VotingOption) o;

        return id == that.id && optionText.equals(that.optionText) && voting.equals(that.voting) && (votes != null ? votes.equals(that.votes) : that.votes == null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + optionText.hashCode();
        result = 31 * result + voting.hashCode();
        result = 31 * result + (votes != null ? votes.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "VotingOption{" +
                "id=" + id +
                ", optionText='" + optionText + '\'' +
                ", voting=" + voting +
                ", votes=" + votes +
                '}';
    }
}
