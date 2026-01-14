package main;

import main.house.Entrance;
import main.house.Floor;
import main.house.Roof;
import main.house.Walls;

public class Character {
    private int age, height, width;
    private float clumsiness;
    private String name, pose, finalMessage = "";
    private boolean legsInside, weatherAffect, enteredHouse;
    private final static String[] poseList = {"На ногах", "На четвереньках", "Лёжа", "На боку"};

    public void SetWeatherAffection(boolean arg) {
        this.weatherAffect = arg;
    }
    public boolean GetWeatherAffection() {
        return this.weatherAffect;
    }

    public void SetLegsInside(boolean arg) {
        this.legsInside = arg;
    }
    public boolean GetLegsInside() {
        return this.legsInside;
    }

    public void PrintResult() {
        System.out.println(finalMessage);
    }

    public void SetSize(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public void SetClumsiness(float clumsiness) {
        if (clumsiness <= 1.0 && clumsiness >= 0.0) {
            this.clumsiness = clumsiness;
            if (this.clumsiness >= 0.5) {
                finalMessage += "и неуклюж. Надо быть поосторожнее - сказал " + name + ". ";
            }
            else {
                finalMessage += "и аккуратен. - сказал " + name + ". ";
            }
        }
        else {
            System.out.println("Неверно введён параметр неуклюжести!");
            System.exit(1);
        }
    }

    public void SetPose(String pose) {
        for (String i : poseList) {
            if (pose.equals(i)) {
                this.pose = pose;
                break;
            }
        }
        if (!this.pose.equals(pose)) {
            System.out.println("Введена несуществующая поза!");
            System.exit(1);
        }
    }

    private boolean AnalyseEntrance(Entrance e1, String pose) {
        if (pose.equals("На ногах")) {
            if (height > e1.GetHeight() || width > e1.GetWidth()) {
                System.out.println("Вход слишком мал, чтобы войти как обычно");
                return false;
            }
        }
        if (pose.equals("На четвереньках")) {
            if (height / 4 > e1.GetHeight() || width > e1.GetWidth()) {
                System.out.println("Вход слишком мал, чтобы войти на четвереньках");
                return false;
            }
        }
        if (pose.equals("Лёжа")) {
            if (height / 10 > e1.GetHeight() || width > e1.GetWidth()) {
                System.out.println("Вход слишком мал, чтобы проползти");
                return false;
            }
        }
        return true;
    }

    public boolean AnalyseRoof(Walls w1, Roof r1, String pose) {
        if (pose.equals("На ногах")) {
            if (w1.GetHeight() <= height) {
                return false;
            }
            return true;
        }
        else if (pose.equals("На четвереньках")) {
            if (height / 4 > w1.GetHeight()) {
                return false;
            }
            return true;
        }
        else if (pose.equals("Лёжа")) {
            if (height / 10 > w1.GetHeight()) {
                return false;
            }
            return true;
        }
        else if (pose.equals("На боку")) {
            if (width > w1.GetHeight()) {
                return false;
            }
            return true;
        }
        else {
            System.out.println("Поза не найдена!");
            System.exit(1);
            return false;
        }
    }

    public boolean AnalyseWalls(Walls w1, Roof r1, String pose) {
        if (pose.equals("На ногах")) {
            if (width >= w1.GetWidth() / 2 || height >= w1.GetHeight() / 2) {
                return false;
            }
            return true;
        }
        else if (pose.equals("На четвереньках")) {
            if (width >= w1.GetWidth() / 2 || height / 4 >= w1.GetHeight() / 2) {
                return false;
            }
            if (height > r1.GetHeight() || width > r1.GetWidth()) {
                return false;
            }
            return true;
        }
        else if (pose.equals("Лёжа")) {
            if (width >= w1.GetWidth() / 2 || height / 10 >= w1.GetHeight() / 2) {
                    return false;
                }
                if (height > r1.GetHeight() || width > r1.GetWidth()) {
                    return false;
                }
                return true;
            }
            else if (pose.equals("На боку")) {
                if (height > r1.GetHeight()) {
                    return false;
                }
                return true;
            }
        else {
            System.out.println("Поза не найдена!");
            System.exit(1);
            return false;
        }
    }

    public void AnalysePoses(Walls w1, Roof r1) {
        if (!AnalyseRoof(w1, r1, "На ногах")) {
            finalMessage += "Нельзя встать без того, чтобы не пробить головой крышу. ";
        }
        if (!AnalyseWalls(w1, r1, "Лёжа")) {
            finalMessage += "Нельзя растянуться па полу, потому что пол слишком короток. ";
        }
        if (!AnalyseRoof(w1, r1, "На боку")) {
            finalMessage += "Нельзя повернуться на бок из-за тесноты. ";
        }
    }

    public void AnalyseWeather(Weather w1) {
        if (!legsInside) {
            finalMessage += "Но главное, как быть с ногами? ";
            if (w1.GetCanAffect()) {
                finalMessage += "Если ты залез в домик, то надо втянуть внутрь и ноги, а то они, чего доброго, попадут под " + w1.GetName();
            }
        }
    }

    public void EnterHouse(Entrance e1, Walls w1, Roof r1, Floor f1) {
        if (f1.GetHeight() == r1.GetHeight() && f1.GetWidth() == r1.GetWidth() && e1.GetHeight() <= w1.GetHeight() && e1.GetWidth() <= f1.GetWidth()) {
            if (pose.equals("На ногах")) {
                finalMessage += "Он вошёл в дом обычным способом. ";
                SetLegsInside(true);
                enteredHouse = true;
            }
            else if (pose.equals("На четвереньках")) {
                finalMessage += "Он стал на колени перед входом и, вздыхая, вполз внутрь на четвереньках. ";
                enteredHouse = true;
            }
            else if (pose.equals("Лёжа")) {
                finalMessage += "Он пробрался в дом ползком. ";
                enteredHouse = true;
            }
            else {
                System.out.println("Поза задана неверно!");
                enteredHouse = false;
                System.exit(1);
            }
            if (!AnalyseRoof(w1, r1, pose)) {
                r1.BreakFromAction();
                SetWeatherAffection(true);
                finalMessage += "Входя в дом, он сломал крышу. ";
            }
            if (!AnalyseWalls(w1, r1, pose)) {
                w1.BreakFromAction();
                SetLegsInside(false);
                SetWeatherAffection(true);
                finalMessage += "Входя в дом, он сломал стены. ";
            }
            if (!AnalyseEntrance(e1, pose)) {
                e1.BreakFromAction();
                finalMessage += "Входя в дом, он сломал дверь. ";
            }
        }
        else {
            System.out.println("Неверно заданы параметры дома!");
            System.exit(1);
        }
    }

    public void LayOnFloor(Floor f1, Walls w1, Roof r1) {
        if (pose.equals("Лёжа") || !enteredHouse) {
            System.out.println("Персонаж уже лежит или не находится в домике");
        }
        else {
            SetPose("Лёжа");
            if (!AnalyseWalls(w1, r1, pose)) {
                w1.BreakFromAction();
                finalMessage += "Ложась на пол, он сломал стены. ";
                SetLegsInside(false);
                SetWeatherAffection(true);
            }
            else {
                SetLegsInside(true);
            }
        }
    }

    public void StandUp(Roof r1, Floor f1, Walls w1) {
        if (!pose.equals("На ногах") && enteredHouse) {
            SetPose("На ногах");
            SetLegsInside(true);
            if (!AnalyseRoof(w1, r1, pose)) {
                r1.BreakFromAction();
                finalMessage += "Поднимаясь, он сломал крышу. ";
                SetWeatherAffection(true);
            }
        }
        else {
            System.out.println("Персонаж уже стоит или не находится в домике");
        }
    }

    public void TurnOnSide(Roof r1, Floor f1, Walls w1) {
        if (!pose.equals("На боку") && enteredHouse) {
            SetPose("На боку");
            if (!AnalyseWalls(w1, r1, pose)) {
                w1.BreakFromAction();
                finalMessage += "Поворачиваясь на бок, он сломал стены. ";
                SetWeatherAffection(true);
                SetLegsInside(false);
            }
            else {
                SetLegsInside(true);
            }
        }
        else {
            System.out.println("Персонаж уже лежит на боку или не находится в домике");
        }
    }

    public void TryPuttingLegsInside(Walls w1, Roof r1) {
        if (!legsInside && !pose.equals("На ногах")) {
            if (height * 0.6 <= r1.GetHeight()) {
                legsInside = true;
            }
            else {
                System.out.println(name + " попытался поместить ноги внутрь, но неудачно. ");
            }
        }
    }

    public Character(String name, int age) {
        this.name = name;
        this.age = age;
        if (age < 0) {
            System.out.println("Неверно задан возраст!");
            System.exit(1);
        }
        if (age >= 35) {
            finalMessage += "Стар я становлюсь ";
        }
        else {
            finalMessage += "Молод я ещё ";
        }
    }
}
