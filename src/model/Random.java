package src.model;

/**
 * Created by nasrinizadivahedi on 2017-11-21.
 */
public class Random implements Strategy {
    @Override
    public int doOperation(int num1, int num2) {
        return num1 + num2;
    }
}