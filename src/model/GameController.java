package model;

import java.util.concurrent.ThreadLocalRandom;

public class GameController {
    private int sumNumber;
    private int supportNumber;
    private String answerStr;
    private int count = 0;
    private boolean supportMessage = false;
    private boolean check;

    public String[] randomNumbers() {
        int amountOfNumbers = ThreadLocalRandom.current().nextInt(2, 5);
        int[] numbers = new int[amountOfNumbers];
        StringBuilder nums = new StringBuilder();
        sumNumber = 0;
        for (int i = 0; i < amountOfNumbers; i++) {
            numbers[i] = ThreadLocalRandom.current().nextInt(1, 15);
            sumNumber += numbers[i];
        }
        for (int i = 0; i < 10; i++) {
            if (i < amountOfNumbers) {
                nums.append(numbers[i]);
                nums.append(",");
            }

            int amountOfFakeNums = ThreadLocalRandom.current().nextInt(0, 2);
            for (int k = 0; k < amountOfFakeNums; k++) {
                nums.append(ThreadLocalRandom.current().nextInt(1, 15));
                nums.append(",");
            }
        }
        supportNumber = numbers[ThreadLocalRandom.current().nextInt(1, numbers.length)];
        return nums.substring(0, nums.length() - 1).split(",");
    }

    public boolean chainAction() {
        check = false;
        Handler chain = new PositiveHandler(new NegativeHandler(new RestartHandler(null, this), this), this);
        String[] temp = answerStr.split(",");
        int chainNum = count;
        int sumOfNumbersFromInput = 0;
        for (String str : temp) {
            if (!str.equals(""))
                sumOfNumbersFromInput += Integer.parseInt(str);
        }
        if (sumOfNumbersFromInput == sumNumber) {
            chainNum = 10;
        } else if (count != 4) {
            chainNum = 9;
        }
        chain.process(chainNum);
        return check;
    }

    public int getSumNumber() {
        return sumNumber;
    }

    public int getSupportNumber() {
        return supportNumber;
    }

    public String getAnswerStr() {
        return answerStr;
    }

    public void setAnswerStr(String answerStr) {
        this.answerStr = answerStr;
    }

    public boolean isSupportMessage() {
        return supportMessage;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setSupportMessage(boolean supportMessage) {
        this.supportMessage = supportMessage;
    }

    public int getCount() {
        return count;
    }
}
