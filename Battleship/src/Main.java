import model.game.GameObserver;
import view.ConfigurationScreen;
import view.MainView;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        MainView MasterView = new MainView();
        MasterView.showScreen("PLACEMENT");


    }
}