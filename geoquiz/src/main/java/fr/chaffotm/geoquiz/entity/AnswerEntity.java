package fr.chaffotm.geoquiz.entity;

import javax.persistence.*;

@Entity(name = "Answer")
@Table(name = "answer")
public class AnswerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answer_generator")
    @SequenceGenerator(name="answer_generator", sequenceName = "answer_sequence", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", foreignKey = @ForeignKey(name = "fk_answer_question"))
    private QuestionEntity question;

    private String answer;

    private boolean correct;

    public QuestionEntity getQuestion() {
        return question;
    }

    public void setQuestion(QuestionEntity question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

}
