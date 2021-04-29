package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Handler;
import model.NegativeHandler;
import model.PositiveHandler;
import model.RestartHandler;

import java.util.concurrent.ThreadLocalRandom;

public class Controller {
    public Label numbers;
    public Text sum;
    public TextField input;
    public Handler chain;
    public Label support;
    private int sumNumber;
    public static int SUCCESS = 1;
    public static int LOSS = 3;
    public static int RESTART = 2;
    private int count = 0;

    public void initialize() {
        sum.setFill(Color.DARKCYAN);
        sum.setFont(Font.font("Calibri", 30));
        support.setVisible(false);
        restartGame();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Игра \"сложи\"");
        alert.setHeaderText("Правила игры");
        alert.setContentText("Среди случайных десятичных цифр, необходимо выбрать числа таким образом," +
                "чтобы сумма их составила заданное двоичное значение. При правильном ответе цифры удаляются и заменяются новыми," +
                "при неправильном ответе дается дается еще несколько попыток, при 5 неправильных ответах подряд игра начинается сначала.");
        ButtonType startGame = new ButtonType("Продолжить", ButtonBar.ButtonData.YES);
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(startGame);
        alert.showAndWait();
    }

    public void submitClicked(ActionEvent actionEvent) {
        chain = new PositiveHandler(new NegativeHandler(new RestartHandler(null)));
        String inputString = input.getText().replaceAll("\\D", ",");
        String[] temp = inputString.split(",");
        int sumOfNumbersFromInput = 0;
        for (String str : temp) {
            if (!str.equals(""))
                sumOfNumbersFromInput += Integer.parseInt(str);
        }
        if (sumOfNumbersFromInput == sumNumber) {
            chain.process(SUCCESS);
            restartGame();
        } else if (count == 4) {
            chain.process(RESTART);
            restartGame();
            count = 0;
            support.setVisible(false);
        } else {
            chain.process(LOSS);
            count++;
            System.out.println(count);
            if (count == 3) support.setVisible(true);
        }
    }

    public String randomNumbers() {
        int amountOfNumbers = ThreadLocalRandom.current().nextInt(2, 5);
        int[] numbers = new int[amountOfNumbers];
        StringBuilder nums = new StringBuilder();
        for (int i = 0; i < amountOfNumbers; i++) {
            numbers[i] = ThreadLocalRandom.current().nextInt(1, 15);
            sumNumber += numbers[i];
        }
        for (int i = 0; i < 10; i++) {
            if (i < amountOfNumbers) {
                nums.append(numbers[i]);
                nums.append(", ");
            }

            int amountOfFakeNums = ThreadLocalRandom.current().nextInt(0, 3);
            for (int k = 0; k < amountOfFakeNums; k++) {
                nums.append(ThreadLocalRandom.current().nextInt(1, 15));
                nums.append(", ");
            }
        }
        int temp = ThreadLocalRandom.current().nextInt(1, numbers.length);
        support.setText("Одно из чисел: " + numbers[temp]);
        return nums.toString();
    }

    public void restartGame() {
        String output = randomNumbers();
        numbers.setText(output.substring(0, output.length() - 2));
        sum.setText(Integer.toBinaryString(sumNumber));
//        sum.setText(String.valueOf(sumNumber));
    }
}
