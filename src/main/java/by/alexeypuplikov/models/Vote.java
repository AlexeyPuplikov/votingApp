package by.alexeypuplikov.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "vote")
public class Vote {
    @Id
    @Column(name = "VOTE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @JsonIgnore
    @ManyToOne
    private VotingOption votingOption;

    @JsonIgnore
    @ManyToOne
    private Voting voting;

    public Vote() {
    }

    public Vote(VotingOption votingOption, Voting voting) {
        this.votingOption = votingOption;
        this.voting = voting;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public VotingOption getVotingOption() {
        return votingOption;
    }

    public void setVotingOption(VotingOption votingOption) {
        this.votingOption = votingOption;
    }

    public Voting getVoting() {
        return voting;
    }

    public void setVoting(Voting voting) {
        this.voting = voting;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vote vote = (Vote) o;

        return id == vote.id && votingOption.equals(vote.votingOption) && voting.equals(vote.voting);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + votingOption.hashCode();
        result = 31 * result + voting.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", votingOption=" + votingOption +
                ", voting=" + voting +
                '}';
    }
}
