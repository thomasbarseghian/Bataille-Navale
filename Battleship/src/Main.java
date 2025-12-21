import controller.*;
import model.game.Game;
import model.player.Player;
import view.MainView;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Battleship Game...");

        // --- 1. MODEL INITIALIZATION ---
        // A single line is enough: The Game class handles everything (Factory, Grids, Ships, Players)
        Game game = new Game();

        // Retrieve references created by Game to pass them to the controllers.
        // Note: We assign them to Player variables here.
        Player humanPlayer = game.getHumanPlayer();
        Player aiPlayer = game.getAIPlayer();

        // --- 2. LOGICAL CONTROLLERS ---

        // A. Human Controller
        HumanController humanLogic = new HumanController();
        humanLogic.setPlayer(humanPlayer);

        // B. AI Controller
        AIController aiLogic = new AIController();
        aiLogic.setPlayer(aiPlayer);
        aiLogic.setOpponent(humanPlayer); // The AI needs to know its target

        // C. Game Controller (The Orchestrator)
        // It manages the turn flow between the Human logic and the AI logic
        GameController gameLogic = new GameController(game, humanLogic, aiLogic);

        // --- 3. UI CONTROLLERS (MVC) ---
        // These controllers handle user interactions (clicks) and manipulate the Model (Game)
        ConfigController configCtrl = new ConfigController(game);
        PlacementController placementCtrl = new PlacementController(game);

        // BattleController needs the human logic (to relay clicks) and the AI player info
        BattleController battleCtrl = new BattleController(humanLogic, aiPlayer);

        // --- 4. MAIN VIEW ---
        // The View needs references to almost everything to render the UI components correctly
        MainView view = new MainView(
                configCtrl,
                placementCtrl,
                battleCtrl,
                humanPlayer,
                aiPlayer,
                game
        );

        // --- 5. NAVIGATION ---
        // Manages switching between screens (Config -> Placement -> Battle)
        ScreenController screenCtrl = new ScreenController(view, game);

        // --- 6. START! ---
        game.startGame();
    }
}