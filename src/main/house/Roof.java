package main.house;

public class Roof extends HouseParts {
    public Roof(int height, int width) {
        this.height = height;   //In this case - length of the roof, should be the same as floor length
        this.width = width;     //Should be the same as floor width
        this.endurance = 100;
    }
}
