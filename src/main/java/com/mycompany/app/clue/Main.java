package com.mycompany.app.clue;


class Main {

    public static void main(String[] args) {
        System.out.println("Run game");
        // Register players, run game

        Game game = new Game();
        PlayerInterface player1 = new MachinePlayer();
        PlayerInterface player2 = new HumanPlayer();

        game.register(player1);
        game.register(player2);
        
        game.configurePlayers();

        game.start();

    }
}
