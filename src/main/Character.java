package main;

import main.house.*;
import main.weather.WeatherConditions;

import java.util.Objects;

public class Character implements HasSizes {
    private int height, width;
    private final int age;
    private final String name;
    private String finalMessage = "";
    private Poses pose;
    private boolean legsInside, weatherAffect, enteredHouse;

    public void setWeatherAffection(boolean arg) {
        this.weatherAffect = arg;
    }

    public boolean getWeatherAffection() {
        return this.weatherAffect;
    }

    public void setLegsInside(boolean arg) {
        this.legsInside = arg;
    }

    public boolean getLegsInside() {
        return this.legsInside;
    }

    public void printResult() {
        System.out.println(finalMessage);
    }

    public void extendResult(String arg) {
        finalMessage += arg;
    }

    public void setSize(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Poses getPose() {
        return pose;
    }

    public void setPose(Poses pose) {
        this.pose = pose;
    }

    public void analysePoses(House.Walls w1, House.Floor.Roof r1) {
        if (!r1.analyse(w1, this, Poses.ONLEGS)) {
            finalMessage += "Нельзя встать без того, чтобы не пробить головой крышу. ";
        }
        if (!w1.analyse(r1, this, Poses.LYING)) {
            finalMessage += "Нельзя растянуться па полу, потому что пол слишком короток. ";
        }
        if (r1.analyse(w1, this, Poses.ONSIDE)) {
            finalMessage += "Нельзя повернуться на бок из-за тесноты. ";
        }
    }

    public void analyseWeather(WeatherConditions w1) {
        if (!legsInside) {
            finalMessage += "Но главное, как быть с ногами? ";
            if (w1.anyWeatherAffects()) {
                w1.addWeatherAffectMessages(this);
            }
        }
    }

    public void enterHouse(House.Entrance e1, House.Walls w1, House.Floor.Roof r1, House.Floor f1) throws IllegalArgumentException {
        if (f1.getHeight() == r1.getHeight() && f1.getWidth() == r1.getWidth() && e1.getHeight() <= w1.getHeight() && e1.getWidth() <= f1.getWidth()) {
            switch (pose) {
                case Poses.ONLEGS:
                    finalMessage += "Он вошёл в дом обычным способом. ";
                    setLegsInside(true);
                    enteredHouse = true;
                    break;
                case Poses.ONALLFOURS:
                    finalMessage += "Он стал на колени перед входом и, вздыхая, вполз внутрь на четвереньках. ";
                    enteredHouse = true;
                    break;
                case Poses.LYING:
                    finalMessage += "Он пробрался в дом ползком. ";
                    enteredHouse = true;
                    break;
                default:
                    System.out.println("Поза задана неверно!");
                    enteredHouse = false;
                    break;
            }
            if (!r1.analyse(w1, this, pose)) {
                r1.BreakFromAction();
                setWeatherAffection(true);
                finalMessage += "Входя в дом, он сломал крышу. ";
            }
            if (!w1.analyse(r1, this, pose)) {
                w1.BreakFromAction();
                setLegsInside(false);
                setWeatherAffection(true);
                finalMessage += "Входя в дом, он сломал стены. ";
            }
            if (!e1.analyse(this)) {
                e1.BreakFromAction();
                finalMessage += "Входя в дом, он сломал дверь. ";
            }
        } else {
            throw new IllegalArgumentException("Неверно заданы параметры дома!");
        }
    }

    public void layOnFloor(House.Floor f1, House.Walls w1, House.Floor.Roof r1) {
        if (pose == Poses.LYING || !enteredHouse) {
            System.out.println("Персонаж уже лежит или не находится в домике");
        } else {
            setPose(Poses.LYING);
            if (!w1.analyse(r1, this, pose)) {
                w1.BreakFromAction();
                finalMessage += "Ложась на пол, он сломал стены. ";
                setLegsInside(false);
                setWeatherAffection(true);
            } else {
                setLegsInside(true);
            }
        }
    }

    public void standUp(House.Floor.Roof r1, House.Floor f1, House.Walls w1) {
        if (pose != Poses.ONLEGS && enteredHouse) {
            setPose(Poses.ONLEGS);
            setLegsInside(true);
            if (!r1.analyse(w1, this, pose)) {
                r1.BreakFromAction();
                finalMessage += "Поднимаясь, он сломал крышу. ";
                setWeatherAffection(true);
            }
        } else {
            System.out.println("Персонаж уже стоит или не находится в домике");
        }
    }

    public void turnOnSide(House.Floor.Roof r1, House.Floor f1, House.Walls w1) {
        if (pose != Poses.ONSIDE && enteredHouse) {
            setPose(Poses.ONSIDE);
            if (!w1.analyse(r1, this, pose)) {
                w1.BreakFromAction();
                finalMessage += "Поворачиваясь на бок, он сломал стены. ";
                setWeatherAffection(true);
                setLegsInside(false);
            } else {
                setLegsInside(true);
            }
        } else {
            System.out.println("Персонаж уже лежит на боку или не находится в домике");
        }
    }

    public void tryPuttingLegsInside(House.Walls w1, House.Floor.Roof r1) {
        if (!legsInside && pose != Poses.ONLEGS) {
            if (height * 0.6 <= r1.getHeight()) {
                legsInside = true;
            } else {
                System.out.println(name + " попытался поместить ноги внутрь, но неудачно. ");
            }
        }
    }

    public Character(String name, int age) throws IllegalArgumentException {
        this.name = name;
        this.age = age;
        if (age < 0) {
            throw new IllegalArgumentException("Возраст < 0!");
        } else if (age >= 40) {
            finalMessage += "Стар я становлюсь и неуклюж. - Сказал " + name + ". ";
        } else {
            finalMessage += "Молод я ещё. " + "Сказал " + name + ". ";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Character character = (Character) o;
        return height == character.height &&
                width == character.width &&
                age == character.age &&
                legsInside == character.legsInside &&
                weatherAffect == character.weatherAffect &&
                enteredHouse == character.enteredHouse &&
                Objects.equals(name, character.name) &&
                Objects.equals(finalMessage, character.finalMessage) &&
                Objects.equals(pose, character.pose);
    }

    @Override
    public int hashCode() {
        return Objects.hash(height, width, age, name, finalMessage, pose,
                legsInside, weatherAffect, enteredHouse);
    }

    public String toString() {
        return "Character{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", height=" + height +
                ", width=" + width +
                ", finalMessage='" + finalMessage + '\'' +
                ", pose=" + pose +
                ", legsInside=" + legsInside +
                ", weatherAffect=" + weatherAffect +
                ", enteredHouse=" + enteredHouse +
                '}';
    }
}
