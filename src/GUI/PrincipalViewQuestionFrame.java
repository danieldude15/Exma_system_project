package GUI;

import Controllers.ControlledScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import logic.Globals;
import logic.Question;
import ocsf.client.ClientGlobals;

import java.net.URL;
import java.util.ResourceBundle;


public class PrincipalViewQuestionFrame implements Initializable, ControlledScreen {

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


    public void setQuestion(Question question) {
        this.question = question;
    }

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     *  method returns you to ViewData screen
     */
    @FXML
    public void backToViewData(ActionEvent event) {
        Globals.mainContainer.setScreen(ClientGlobals.PrincipalViewDataID);
    }
}
