package engine.enteties;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
public class Answer {

    private int[] answers;

    public int[] getAnswer() {
        return answers;
    }

    public void setAnswer(int[] answers) {
        this.answers = answers;
    }
}
