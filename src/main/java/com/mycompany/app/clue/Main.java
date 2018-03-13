package com.mycompany.app.clue;


import org.semanticweb.owlapi.model.OWLOntologyCreationException;

class Main {

    public static void main(String[] args) throws OWLOntologyCreationException {
        System.out.println("Run game");
        // Register players, run game

        Game game = new Game();
        PlayerInterface player1 = new Player();
        PlayerInterface player2 = new Player();
        PlayerInterface player3 = new MachinePlayer();

        game.register(player1);
        game.register(player2);
        game.register(player3);
        
        game.configurePlayers();



        game.start();

    }
}
