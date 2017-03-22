package by.alexeypuplikov.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "voting_option")
public class VotingOption {
    @Id
    @Column(name = "VOTING_OPTION_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "OPTION_TEXT")
    private String optionText;

    @ManyToOne()
    @JsonIgnore
    private Voting voting;

    @OneToMany(mappedBy = "votingOption")
    @JsonIgnore
    private Set<Vote> votes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

        if (!id.equals(that.id)) return false;
        if (!optionText.equals(that.optionText)) return false;
        if (!voting.equals(that.voting)) return false;
        return votes != null ? votes.equals(that.votes) : that.votes == null;

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
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
