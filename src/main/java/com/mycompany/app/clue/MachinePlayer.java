package com.mycompany.app.clue;


class MachinePlayer extends Player {
    ClueOntology playerOntology;

    public void makeOntology(ClueOntologyManager clueOntologyManager, Game game) {
        playerOntology = new ClueOntology(clueOntologyManager, game);
    }



}
