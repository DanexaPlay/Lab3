package main;

public class Weather {
    private String name;
    private boolean canAffect;

    public String AffectMessage() {
        return name + " воздействует на персонажа";
    }

    public boolean GetCanAffect() {
        return this.canAffect;
    }

    public String GetName() {
        return name;
    }

    public Weather(String name, boolean canAffect) {
        this.name = name;
        this.canAffect = canAffect;
    }
}
