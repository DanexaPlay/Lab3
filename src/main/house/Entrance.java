package main.house;

public class Entrance extends HouseParts {
    public Entrance(int height, int width) {
        this.height = height;   //Shouldn't be more than walls height
        this.width = width; //Shouldn't be more than floor and roof width
        this.endurance = 100;
    }
    //Isn't related with weather affection
}
