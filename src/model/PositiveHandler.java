package model;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

import static sample.Controller.SUCCESS;

public class PositiveHandler extends Handler {
    private GameController gameController;

    public PositiveHandler(Handler processor, GameController gameController) {
        super(processor);
        this.gameController = gameController;
    }

    public boolean process(Integer request) {
        if (request != SUCCESS) return super.process(request);
        else {
            gameController.setCheck(true);
            gameController.setSupportMessage(false);
            gameController.setAnswerStr("");
            gameController.setCount(0);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Вы выиграли!");
            alert.setHeaderText("Поздравляем");
            ButtonType startNewGame = new ButtonType("Начать новую игру", ButtonBar.ButtonData.YES);
            ButtonType quit = new ButtonType("Выйти", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().clear();
            alert.getButtonTypes().addAll(startNewGame, quit);
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get().getButtonData() == ButtonBar.ButtonData.YES)
                return true;
            else if(option.get().getButtonData() == ButtonBar.ButtonData.NO)
                System.exit(1);
            return false;
        }
    }
}
