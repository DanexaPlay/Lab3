import main.Character;
import main.Poses;
import main.house.*;
import main.weather.Weather;
import main.weather.WeatherConditions;

public static void main(String[] args) {
    Character c1 = new Character("Медвед", 40);
    c1.setSize(15, 8);
    c1.setPose(Poses.ONALLFOURS);
    Weather w1 = new Weather("Дождь", true);
    House.Floor.Roof r1 = new House.Floor.Roof(100, 50);
    House.Floor f1 = new House.Floor(100, 50);
    House.Entrance e1 = new House.Entrance(20, 10);
    House.Walls w2 = new House.Walls(50);
    WeatherConditions weatherlist = new WeatherConditions();
    weatherlist.addWeather(w1);
    c1.enterHouse(e1, w2, r1, f1);
    c1.analysePoses(w2, r1);
    c1.analyseWeather(weatherlist);
    c1.printResult();
}