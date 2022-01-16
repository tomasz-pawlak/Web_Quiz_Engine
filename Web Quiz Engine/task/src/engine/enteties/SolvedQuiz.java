package engine.enteties;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.*;

@Entity
@JsonPropertyOrder({"id", "completedAt"})
public class SolvedQuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long DataBaseId;

    private Long id;

    @Column
    @JsonProperty("completedAt")
    private String completedAt;

    @Column
    @JsonIgnore
    private String completedBy;

    public SolvedQuiz(Long id, String completedAt) {
        this.id = id;
        this.completedAt = completedAt;
    }

    public SolvedQuiz(Long id, String completedAt, String completedBy) {
        this.id = id;
        this.completedAt = completedAt;
        this.completedBy = completedBy;
    }

    public SolvedQuiz() {

    }


    public String getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long questionId) {
        this.id = questionId;
    }

    public String getCompletedBy() {
        return completedBy;
    }

    public void setCompletedBy(String completedBy) {
        this.completedBy = completedBy;
    }
}
