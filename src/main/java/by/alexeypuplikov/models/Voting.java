package by.alexeypuplikov.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "voting")
public class Voting {
    @Id
    @Column(name = "VOTING_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;

    @Column(name = "TOPIC")
    private String topic;

    @Column(name = "LINK")
    private String link;

    @OneToMany(mappedBy = "voting")
    private Set<VotingOption> votingOptions;

    @OneToMany(mappedBy = "voting")
    private Set<Vote> votes;

    public Voting() {
    }

    public Voting(String topic, String link) {
        this.topic = topic;
        this.link = link;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Set<VotingOption> getVotingOptions() {
        return votingOptions;
    }

    public void setVotingOptions(Set<VotingOption> votingOptions) {
        this.votingOptions = votingOptions;
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

        Voting voting = (Voting) o;

        if (Id != voting.Id) return false;
        if (!topic.equals(voting.topic)) return false;
        if (!link.equals(voting.link)) return false;
        if (!votingOptions.equals(voting.votingOptions)) return false;
        return votes != null ? votes.equals(voting.votes) : voting.votes == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (Id ^ (Id >>> 32));
        result = 31 * result + topic.hashCode();
        result = 31 * result + link.hashCode();
        result = 31 * result + votingOptions.hashCode();
        result = 31 * result + (votes != null ? votes.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Voting{" +
                "Id=" + Id +
                ", topic='" + topic + '\'' +
                ", link='" + link + '\'' +
                ", votingOptions=" + votingOptions +
                ", votes=" + votes +
                '}';
    }
}
