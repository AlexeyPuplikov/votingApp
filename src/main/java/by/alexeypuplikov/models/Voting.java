package by.alexeypuplikov.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "voting")
public class Voting {
    @Id
    @Column(name = "VOTING_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "TOPIC")
    private String topic;

    @Column(name = "LINK")
    private String link;

    @OneToMany(mappedBy = "voting")
    @JsonIgnore
    private Set<VotingOption> votingOptions;

    @OneToMany(mappedBy = "voting")
    @JsonIgnore
    private Set<Vote> votes;

    @Column(name = "IS_LAUNCHED")
    private boolean isLaunched;

    public Voting() {
    }

    public Voting(String topic, String link) {
        this.isLaunched = false;
        this.topic = topic;
        this.link = link;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isLaunched() {
        return isLaunched;
    }

    public void setLaunched(boolean launched) {
        isLaunched = launched;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Voting voting = (Voting) o;

        if (isLaunched != voting.isLaunched) return false;
        if (!id.equals(voting.id)) return false;
        if (!topic.equals(voting.topic)) return false;
        if (link != null ? !link.equals(voting.link) : voting.link != null) return false;
        if (votingOptions != null ? !votingOptions.equals(voting.votingOptions) : voting.votingOptions != null)
            return false;
        return votes != null ? votes.equals(voting.votes) : voting.votes == null;

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + topic.hashCode();
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + (votingOptions != null ? votingOptions.hashCode() : 0);
        result = 31 * result + (votes != null ? votes.hashCode() : 0);
        result = 31 * result + (isLaunched ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Voting{" +
                "id=" + id +
                ", topic='" + topic + '\'' +
                ", link='" + link + '\'' +
                ", votingOptions=" + votingOptions +
                ", votes=" + votes +
                ", isLaunched=" + isLaunched +
                '}';
    }
}
