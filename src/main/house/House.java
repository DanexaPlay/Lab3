package main.house;

import main.Character;
import main.Poses;

public class House {
    public static class Entrance extends HouseParts {
        public Entrance(int height, int width) {
            this.height = height;   //Shouldn't be more than walls height
            this.width = width; //Shouldn't be more than floor and roof width
        }
        //Isn't related with weather affection
        public boolean analyse(Character c1) throws IllegalStateException {
            return switch (c1.getPose()) {
                case Poses.ONLEGS -> {
                    if (c1.getHeight() > height || c1.getWidth() > width) {
                        System.out.println("Вход слишком мал, чтобы войти как обычно");
                        yield false;
                    }
                    yield true;
                }
                case Poses.ONALLFOURS -> {
                    if (c1.getHeight() / 4 > height || c1.getWidth() > width) {
                        System.out.println("Вход слишком мал, чтобы войти на четвереньках");
                        yield false;
                    }
                    yield true;
                }
                case Poses.LYING -> {
                    if (c1.getHeight() / 10 > height || c1.getWidth() > width) {
                        System.out.println("Вход слишком мал, чтобы проползти");
                        yield false;
                    }
                    yield true;
                }
                default -> throw new IllegalStateException("Поза не найдена!");
            };
        }
    }

    public static class Walls extends HouseParts {
        public Walls(int height) {
            this.height = height;
            this.width = 1; //Because doesn't matter for walls
        }

        public boolean analyse(Floor.Roof r1, Character c1, Poses pose) {
            return switch (pose) {
                case Poses.ONLEGS -> c1.getWidth() < r1.getWidth() / 2 && c1.getHeight() < height / 2;
                case Poses.ONALLFOURS -> {
                    if (c1.getWidth() >= r1.getWidth() / 2 || c1.getHeight() / 4 >= height / 2) {
                        yield false;
                    }
                    yield height <= r1.getHeight() && width <= r1.getWidth();
                }
                case Poses.LYING -> {
                    if (c1.getWidth() >= width / 2 || c1.getHeight() / 10 >= height / 2) {
                        yield false;
                    }
                    yield c1.getHeight() <= r1.getHeight() && c1.getWidth() <= r1.getWidth();
                }
                case Poses.ONSIDE -> c1.getHeight() <= r1.getHeight();
                default -> throw new IllegalStateException("Поза не найдена!");
            };
        }
    }

    public static class Floor extends HouseParts {
        public Floor(int height, int width) {
            this.height = height;   //In this case - length of the floor, should be the same as roof length
            this.width = width;     //Should be the same as roof width
        }
        //Isn't related with weather affection

        public static class Roof extends HouseParts {
            public Roof(int height, int width) {
                this.height = height;   //In this case - length of the roof, should be the same as floor length
                this.width = width;     //Should be the same as floor width
            }

            public boolean analyse(Walls w1, Character c1, Poses pose) {
                return switch (pose) {
                    case Poses.ONLEGS -> w1.getHeight() > c1.getHeight();
                    case Poses.ONALLFOURS -> c1.getHeight() / 4 <= w1.getHeight();
                    case Poses.LYING -> c1.getHeight() / 10 <= w1.getHeight();
                    case Poses.ONSIDE -> c1.getWidth() <= w1.getHeight();
                    default -> throw new IllegalStateException("Поза не найдена!");
                };
                }
            }
    }
}
