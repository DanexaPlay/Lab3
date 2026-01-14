package main.house;

abstract public class HouseParts {
    protected int height, width, endurance; //Endurance - from 0 to 100
    protected boolean isBroken;

    public void BreakFromAction() {
        if (isBroken == false) {
            SetIsBroken();
            endurance = 0;
        }
    }
    public boolean GetIsBroken() {
        return isBroken;
    }
    protected void SetIsBroken() {
        isBroken = true;
    }
    public int GetHeight() {
        return this.height;
    }
    public int GetWidth() {
        return this.width;
    }
}
