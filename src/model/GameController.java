package model;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class GameController {
    private int sumNumber;
    private int supportNumber;
    private int[] answerNumbers;
    private int count = 0;
    private boolean supportMessage = false;
    private boolean check;

    public ArrayList<Integer> randomNumbers() {
        int amountOfNumbers = ThreadLocalRandom.current().nextInt(2, 5);
        int[] numbers = new int[amountOfNumbers];
        sumNumber = 0;
        for (int i = 0; i < amountOfNumbers; i++) {
            numbers[i] = ThreadLocalRandom.current().nextInt(1, 15);
            sumNumber += numbers[i];
        }
        ArrayList<Integer> numbersWithFakeNums = new ArrayList<Integer>();
        for (int num : numbers) {
            numbersWithFakeNums.add(num);
            int amountOfFakeNums = ThreadLocalRandom.current().nextInt(0, 2);
            for (int k = 0; k < amountOfFakeNums; k++) {
                numbersWithFakeNums.add(ThreadLocalRandom.current().nextInt(1, 15));
            }
        }
        supportNumber = numbers[ThreadLocalRandom.current().nextInt(1, numbers.length)];
        return numbersWithFakeNums;
    }

    public boolean chainAction() {
        check = false;
        Handler chain = new PositiveHandler(new NegativeHandler(new RestartHandler(null, this), this), this);
        int chainNum = count;
        int sumOfNumbersFromInput = 0;
        for (int number : answerNumbers) {
            sumOfNumbersFromInput += number;
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

    public int[] getAnswerNumbers() {
        return answerNumbers;
    }

    public void setAnswerNumbers(int[] answerNumbers) {
        this.answerNumbers = answerNumbers;
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
