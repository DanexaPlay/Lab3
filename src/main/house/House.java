package main.house;

import main.Character;
import main.Poses;

public class House {
    public static class Entrance extends HouseParts {
        public Entrance(int height, int width) {
            this.height = height;   //Shouldn't be more than walls height
            this.width = width; //Shouldn't be more than floor and roof width
            this.endurance = 100;
        }
        //Isn't related with weather affection
        public boolean analyse(Character c1) throws IllegalStateException {
            switch (c1.getPose()) {
                case Poses.ONLEGS:
                    if (c1.getHeight() > height || c1.getWidth() > width) {
                        System.out.println("Вход слишком мал, чтобы войти как обычно");
                        return false;
                    }
                    return true;
                case Poses.ONALLFOURS:
                    if (c1.getHeight() / 4 > height || c1.getWidth() > width) {
                        System.out.println("Вход слишком мал, чтобы войти на четвереньках");
                        return false;
                    }
                    return true;
                case Poses.LYING:
                    if (c1.getHeight() / 10 > height || c1.getWidth() > width) {
                        System.out.println("Вход слишком мал, чтобы проползти");
                        return false;
                    }
                    return true;
                default:
                    throw new IllegalStateException("Поза не найдена!");
            }
        }
    }

    public static class Walls extends HouseParts {
        public Walls(int height) {
            this.height = height;
            this.width = 1; //Because doesn't matter for walls
            this.endurance = 100;
        }

        public boolean analyse(Floor.Roof r1, Character c1, Poses pose) {
            switch (pose) {
                case Poses.ONLEGS:
                    if (c1.getWidth() >= r1.getWidth() / 2 || c1.getHeight() >= height / 2) {
                        return false;
                    }
                    return true;
                case Poses.ONALLFOURS:
                    if (c1.getWidth() >= r1.getWidth() / 2 || c1.getHeight() / 4 >= height / 2) {
                        return false;
                    }
                    if (height > r1.getHeight() || width > r1.getWidth()) {
                        return false;
                    }
                    return true;
                case Poses.LYING:
                    if (c1.getWidth() >= width / 2 || c1.getHeight() / 10 >= height / 2) {
                        return false;
                    }
                    if (c1.getHeight() > r1.getHeight() || c1.getWidth() > r1.getWidth()) {
                        return false;
                    }
                    return true;
                case Poses.ONSIDE:
                    if (c1.getHeight() > r1.getHeight()) {
                        return false;
                    }
                    return true;
                default:
                    throw new IllegalStateException("Поза не найдена!");
            }
        }
    }

    public static class Floor extends HouseParts {
        public Floor(int height, int width) {
            this.height = height;   //In this case - length of the floor, should be the same as roof length
            this.width = width;     //Should be the same as roof width
            this.endurance = 100;
        }
        //Isn't related with weather affection

        public static class Roof extends HouseParts {
            public Roof(int height, int width) {
                this.height = height;   //In this case - length of the roof, should be the same as floor length
                this.width = width;     //Should be the same as floor width
                this.endurance = 100;
            }

            public boolean analyse(Walls w1, Character c1, Poses pose) {
                switch (pose) {
                    case Poses.ONLEGS:
                        if (w1.getHeight() <= c1.getHeight()) {
                            return false;
                        }
                        return true;
                    case Poses.ONALLFOURS :
                        if (c1.getHeight() / 4 > w1.getHeight()) {
                            return false;
                        }
                        return true;
                    case Poses.LYING:
                        if (c1.getHeight() / 10 > w1.getHeight()) {
                            return false;
                        }
                        return true;
                    case Poses.ONSIDE:
                        if (c1.getWidth() > w1.getHeight()) {
                            return false;
                        }
                        return true;
                    default:
                        throw new IllegalStateException("Поза не найдена!");
                    }
                }
            }
    }
}
