package engine.enteties;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.*;

@Entity
@JsonPropertyOrder({"id", "completedAt"})
public class SolvedQuiz {


    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "solvedQuiz_generator")
    @SequenceGenerator(name="solvedQuiz_generator", sequenceName = "solvedQuiz_seq", allocationSize=50)
    @Column(name = "idPrimal", updatable = false, nullable = false)
    private Long id;

    @Column
    @JsonProperty("id")
    private Long questionId;

    @Column
    @JsonProperty("completedAt")
    private String completedAt;

    @Column
    @JsonIgnore
    private String completedBy;

    public SolvedQuiz(Long questionId, String completedAt) {
        this.questionId = questionId;
        this.completedAt = completedAt;
    }

    public SolvedQuiz(Long questionId, String completedAt, String completedBy) {
        this.questionId = questionId;
        this.completedAt = completedAt;
        this.completedBy = completedBy;
    }

    public SolvedQuiz() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getCompletedBy() {
        return completedBy;
    }

    public void setCompletedBy(String completedBy) {
        this.completedBy = completedBy;
    }
}
