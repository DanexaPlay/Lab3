package main.house;

public class Floor extends HouseParts {
    public Floor(int height, int width) {
        this.height = height;   //In this case - length of the floor, should be the same as roof length
        this.width = width;     //Should be the same as roof width
        this.endurance = 100;
    }
    //Isn't related with weather affection
}
