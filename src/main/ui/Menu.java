package ui;

import model.persistence.MatchHistory;

import java.util.Scanner;

public class Menu {
    private Scanner keyboard = new Scanner(System.in);
    private boolean exit = false;
    public MatchHistory matchHistory = new MatchHistory();
    private Game game;

    public Menu() {
    }

    //EFFECTS : Prints the main menu
    public void displayMenu() {
        do {
            System.out.println("1. PLAY GAME");
            System.out.println("2. MATCH HISTORY");
            //TODO implement match history
            //TODO quick save game and "3. resume game"?
            System.out.println("0. EXIT");

            interpretInput();
        } while (!exit);
    }

    //EFFECTS : Interprets player input and puts it into effect
    private void interpretInput() {
        String input = keyboard.nextLine();
        if (input.equals("1") || input.equals("1.") || input.equalsIgnoreCase("play game")) {
            game = new Game();
            game.play();
        } else if (input.equals("2") || input.equals("2.") || input.equalsIgnoreCase("match history")) {
            matchHistory.display();
        } else if (input.equals("0") || input.equals("0.") || input.equalsIgnoreCase("exit")) {
            exit = true;
        } else {
            System.out.println("That is not a valid input");
            interpretInput();
        }
    }
}
//./data/lastGamePlayed