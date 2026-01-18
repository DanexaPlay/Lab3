package main.weather;

import main.Character;

import java.util.ArrayList;

public class WeatherConditions {
    private ArrayList<Weather> weatherConditions = new ArrayList<>();

    public void addWeather(Weather w1) {
        weatherConditions.add(w1);
    }

    public boolean anyWeatherAffects() {
        for (Weather i : weatherConditions) {
            if (i.canAffect()) {
                return true;
            }
        }
        return false;
    }

    public void addWeatherAffectMessages(Character c1) {
        boolean flag = false;
        for (Weather i : weatherConditions) {
            if (i.canAffect()) {
                c1.extendResult(i.name() + ",");
                flag = true;
            }
            if (flag) {
                c1.extendResult("- погода на улице");
            }
        }
    }
}
