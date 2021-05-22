package sample;

import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.GameController;

import java.util.concurrent.Callable;

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
        gameController.setAnswerNumbers(new int[]{});
        support.setText("Одно из требуемых чисел: " + gameController.getSupportNumber());
    }

    public void pickNumber(ActionEvent actionEvent) {
        try {
            int[] temp = new int[gameController.getAnswerNumbers().length + 1];
            int i = 0;
            StringBuilder answer = new StringBuilder();
            for (int number : gameController.getAnswerNumbers()) {
                answer.append(number);
                answer.append(", ");
                temp[i] = number;
                i++;
            }
            answer.append(comboBox.getValue().toString());
            output.setText("Выбранные числа: " + answer);
            temp[i] = Integer.parseInt(comboBox.getValue().toString());
            gameController.setAnswerNumbers(temp);
        } catch (Exception ignored) {

        }
    }
}
