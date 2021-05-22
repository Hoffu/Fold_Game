package model;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

import static sample.Controller.LOSS;

public class NegativeHandler extends Handler {
    private GameController gameController;

    public NegativeHandler(Handler processor, GameController gameController) {
        super(processor);
        this.gameController = gameController;
    }

    public boolean process(Integer request) {
        if (request != LOSS) return super.process(request);
        else {
            gameController.setAnswerNumbers(new int[]{});
            int temp = gameController.getCount();
            gameController.setCount(temp + 1);
            if (gameController.getCount() == 3) gameController.setSupportMessage(true);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Неправильно");
            alert.setHeaderText("Попробуйте еще раз");
            ButtonType continueGame = new ButtonType("Продолжить", ButtonBar.ButtonData.YES);
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
