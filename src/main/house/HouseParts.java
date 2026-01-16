package main.house;

import main.HasSizes;

abstract public class HouseParts implements HasSizes {
    protected int height, width, endurance; //Endurance - from 0 to 100
    protected boolean isBroken;

    public void BreakFromAction() {
        if (!isBroken) {
            setIsBroken();
            endurance = 0;
        }
    }
    public boolean getIsBroken() {
        return isBroken;
    }
    protected void setIsBroken() {
        isBroken = true;
    }

    public int getHeight() {
        return this.height;
    }
    public int getWidth() {
        return this.width;
    }
}
