package GUI;

import Controllers.ControlledScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import logic.Globals;
import logic.Question;
import ocsf.client.ClientGlobals;

/**
 * Manages The screen where the principal views a question placed in the database.
 */
public class PrincipalViewQuestionFrame implements ControlledScreen {

    /* Fields Start */

    private Question question;

    @FXML private Label m_questionID;
    @FXML private Label m_questionContent;
    @FXML private Label m_questionFirstAnswer;
    @FXML private Label m_questionSecondAnswer;
    @FXML private Label m_questionThirdAnswer;
    @FXML private Label m_questionFourthAnswer;
    @FXML private Label m_questionCorrectAnswer;
    @FXML private Label m_questionAuthor;
    @FXML private Button m_backButton;

    /* Fields End */

    /* Constructors Start */

    /**
     * Sets the 'Principal View Question' screen.
     * Updating all labels present.
     */
    @Override
    public void runOnScreenChange() {
        if(question != null) {
            m_questionID.setText(question.questionIDToString());
            m_questionContent.setText(question.getQuestionString());
            m_questionFirstAnswer.setText(question.getAnswer(1));
            m_questionSecondAnswer.setText(question.getAnswer(2));
            m_questionThirdAnswer.setText(question.getAnswer(3));
            m_questionFourthAnswer.setText(question.getAnswer(4));
            m_questionCorrectAnswer.setText(String.valueOf(question.getCorrectAnswerIndex()));
            m_questionAuthor.setText(question.getAuthor().getName());
        }
    }

    /* Constructors End */

    /* Getters and Setters Start */

    /**
     * Sets the question to be displayed for the Principal.
     * @param question - to be displayed.
     */
    public void setQuestion(Question question) {
        this.question = question;
    }

    /* Getters and Setters End */

    /* Methods Start */

    /**
     * Returns to View School Data screen.
     * @param event - Click on 'Back' button.
     */
    @FXML
    public void backToViewData(ActionEvent event) {
        Globals.mainContainer.setScreen(ClientGlobals.PrincipalViewDataID);
    }

    /* Methods End */
}
