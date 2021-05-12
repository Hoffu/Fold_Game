package model;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

import static sample.Controller.RESTART;

public class RestartHandler extends Handler {
    private GameController gameController;

    public RestartHandler(Handler processor, GameController gameController) {
        super(processor);
        this.gameController = gameController;
    }

    public boolean process(Integer request) {
        if (request != RESTART) return super.process(request);
        else {
            gameController.setCheck(true);
            gameController.setCount(0);
            gameController.setSupportMessage(false);
            gameController.setAnswerStr("");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Вы проиграли!");
            alert.setHeaderText("Начните новую игру");
            ButtonType continueGame = new ButtonType("Начать новую игру", ButtonBar.ButtonData.YES);
            ButtonType quit = new ButtonType("Выйти", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().clear();
            alert.getButtonTypes().addAll(continueGame, quit);
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get().getButtonData() == ButtonBar.ButtonData.YES)
                return true;
            else if(option.get().getButtonData() == ButtonBar.ButtonData.NO)
                System.exit(1);
            return false;
        }
    }
}
