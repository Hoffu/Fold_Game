package sample;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.GameController;

public class Controller {
    public Text sum;
    public Label support;
    public ComboBox comboBox;
    public Label output;
    public static int SUCCESS = 10;
    public static int RESTART = 4;
    public static int LOSS = 9;
    private final GameController gameController = new GameController();

    public void initialize() {
        comboBox.setItems(FXCollections.observableArrayList(gameController.randomNumbers()));
        sum.setFill(Color.DARKCYAN);
        sum.setFont(Font.font("Calibri", 30));
        support.setVisible(false);
        restartGame();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Игра \"сложи\"");
        alert.setHeaderText("Правила игры");
        alert.setContentText("Среди случайных десятичных цифр, необходимо выбрать числа таким образом," +
                "чтобы их сумма составила заданное двоичное значение. При правильном ответе цифры удаляются и заменяются новыми," +
                "при неправильном ответе дается дается еще несколько попыток, при 5 неправильных ответах подряд игра начинается сначала.");
        ButtonType startGame = new ButtonType("Продолжить", ButtonBar.ButtonData.YES);
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(startGame);
        alert.showAndWait();
    }

    public void submitClicked(ActionEvent actionEvent) {
        boolean checkRestart = gameController.chainAction();
        if (checkRestart) restartGame();
        support.setVisible(gameController.isSupportMessage());
        output.setText("Выбранные числа: ");
    }

    public void restartGame() {
        comboBox.setItems(FXCollections.observableArrayList(gameController.randomNumbers()));
        sum.setText(Integer.toBinaryString(gameController.getSumNumber()));
//        sum.setText(String.valueOf(gameController.getSumNumber()));
        gameController.setAnswerStr("");
        support.setText("Одно из требуемых чисел: " + gameController.getSupportNumber());
    }

    public void pickNumber(ActionEvent actionEvent) {
        try {
            String str = gameController.getAnswerStr();
            str += comboBox.getValue().toString();
            str += ",";
            gameController.setAnswerStr(str);
            output.setText("Выбранные числа: " + gameController.getAnswerStr().substring(0, gameController.getAnswerStr().length() - 1));
        } catch (Exception ignored) {

        }
    }
}
