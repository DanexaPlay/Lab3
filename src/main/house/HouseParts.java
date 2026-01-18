package main.house;

import main.HasSizes;

import java.util.Objects;

abstract public class HouseParts implements HasSizes {
    protected int height, width;
    protected boolean isBroken;

    public void BreakFromAction() {
        if (!isBroken) {
            setIsBroken();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HouseParts that = (HouseParts) o;
        return height == that.height &&
                width == that.width &&
                isBroken == that.isBroken;
    }

    @Override
    public int hashCode() {
        return Objects.hash(height, width, isBroken);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "height=" + height +
                ", width=" + width +
                ", isBroken=" + isBroken +
                '}';
    }
}
