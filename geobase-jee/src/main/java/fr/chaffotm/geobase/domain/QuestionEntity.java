package fr.chaffotm.geobase.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "Question")
@Table(name = "question")
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_generator")
    @SequenceGenerator(name="question_generator", sequenceName = "question_sequence", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", foreignKey = @ForeignKey(name = "fk_question_quiz"))
    private QuizEntity quiz;

    private String wording;

    private String answer;

    @ElementCollection
    @CollectionTable(
            name = "suggestion",
            joinColumns = @JoinColumn(name = "question_id", referencedColumnName = "id"),
            foreignKey = @ForeignKey(name = "fk_suggestion_question")
    )
    @Column(name = "suggestion")
    private List<String> suggestions = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public QuizEntity getQuiz() {
        return quiz;
    }

    public void setQuiz(QuizEntity quiz) {
        this.quiz = quiz;
    }

    public String getWording() {
        return wording;
    }

    public void setWording(String wording) {
        this.wording = wording;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<String> getSuggestions() {
        return suggestions;
    }

    // Used by JPA
    protected void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }

    public void addSuggestion(String suggestion) {
        this.suggestions.add(suggestion);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionEntity that = (QuestionEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
