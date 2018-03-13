package com.mycompany.app.clue;


import org.semanticweb.owlapi.model.OWLOntologyCreationException;

class MachinePlayer extends Player {
    Ontology ontology;

    public Ontology getOntology() {
        return ontology;
    }

    public MachinePlayer() {
    }

    @Override
    public void observeCard(Card responseCard, int responsePlayer) {
        ontology.hasA(responsePlayer, new Literal(responseCard));
    }

    @Override
    public void observe(Guess guess, int holdingPlayer) {
        ontology.hasA(holdingPlayer, new Literal(guess.suspect, guess.weapon, guess.room));
    }

    @Override
    public boolean openConfidential(Guess currentGuess) {
        if(ontology.playerKnown(currentGuess.weapon) < 0
            && ontology.playerKnown(currentGuess.room) < 0
            && ontology.playerKnown(currentGuess.suspect) < 0) {
            return true;
        }
        return false;
    }

    @Override
    public Card showCard(Guess guess) {
        if(ontology.playerKnown(guess.getRoom()) == this.playerID) {
            return guess.getRoom();
        }
        if(ontology.playerKnown(guess.getSuspect()) == this.playerID) {
            return guess.getSuspect();
        }
        if(ontology.playerKnown(guess.getWeapon()) == this.playerID) {
            return guess.getWeapon();
        }

        return null;
    }

    public void loadOntology(Guess confidential) {
        ontology = new Ontology(this.playerID,this.cards,this.numPlayers,confidential);
    }

    public Guess makeGuess() {
        Suspect guessSuspect = ontology.suspects.get(Helper.random(ontology.suspects.size()));
        Weapon guessWeapon = ontology.weapons.get(Helper.random(ontology.weapons.size()));
        Room guessRoom = ontology.rooms.get(Helper.random(ontology.rooms.size()));
        Guess guess = new Guess(guessSuspect,guessRoom,guessWeapon);
        System.out.println("Player " + this.playerID + " made a guess: " + guess);
        return guess;
    }




}
