package fr.chaffotm.geoquiz.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "Quiz")
@Table(name = "quiz")
public class QuizEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quiz_generator")
    @SequenceGenerator(name="quiz_generator", sequenceName = "quiz_sequence", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @OneToMany(
            mappedBy = "quiz",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<QuestionEntity> questions = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public List<QuestionEntity> getQuestions() {
        return questions;
    }

    // Used by JPA
    protected void setQuestions(final List<QuestionEntity> questions) {
        this.questions = questions;
    }

    public void addQuestion(final QuestionEntity question) {
        this.questions.add(question);
        question.setQuiz(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuizEntity)) return false;
        QuizEntity that = (QuizEntity) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
