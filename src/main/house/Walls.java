package main.house;

public class Walls extends HouseParts {
    public Walls(int height) {
        this.height = height;
        this.width = 1; //Because doesn't matter for walls
        this.endurance = 100;
    }
}
