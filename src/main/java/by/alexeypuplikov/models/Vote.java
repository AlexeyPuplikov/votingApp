package by.alexeypuplikov.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "vote")
public class Vote {
    @Id
    @Column(name = "VOTE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne()
    @JsonIgnore
    private VotingOption votingOption;

    @ManyToOne()
    @JsonIgnore
    private Voting voting;

    @ManyToOne()
    @JsonIgnore
    private User user;

    public Vote() {
    }

    public Vote(VotingOption votingOption, Voting voting, User user) {
        this.votingOption = votingOption;
        this.voting = voting;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vote vote = (Vote) o;

        if (!id.equals(vote.id)) return false;
        if (!votingOption.equals(vote.votingOption)) return false;
        if (!voting.equals(vote.voting)) return false;
        return user.equals(vote.user);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + votingOption.hashCode();
        result = 31 * result + voting.hashCode();
        result = 31 * result + user.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", votingOption=" + votingOption +
                ", voting=" + voting +
                ", user=" + user +
                '}';
    }
}
