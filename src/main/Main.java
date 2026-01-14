import main.Character;
import main.Weather;
import main.house.Entrance;
import main.house.Floor;
import main.house.Roof;
import main.house.Walls;

public static void main(String[] args) {
    Character c1 = new Character("Медвед", 40);
    c1.SetSize(15, 8);
    c1.SetPose("На четвереньках");
    c1.SetClumsiness(0.5F);
    Weather w1 = new Weather("Дождь", true);
    Roof r1 = new Roof(100, 50);
    Floor f1 = new Floor(100, 50);
    Entrance e1 = new Entrance(20, 10);
    Walls w2 = new Walls(50);
    c1.EnterHouse(e1, w2, r1, f1);
    c1.AnalysePoses(w2, r1);
    c1.AnalyseWeather(w1);
    c1.PrintResult();
}
//Changes branch