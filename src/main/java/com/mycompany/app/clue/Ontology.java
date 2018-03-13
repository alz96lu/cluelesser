package com.mycompany.app.clue;

import java.util.ArrayList;
import java.util.Arrays;

public class Ontology {
    ArrayList<Weapon> weapons= new ArrayList();
    ArrayList<Room> rooms= new ArrayList();
    ArrayList<Suspect> suspects = new ArrayList();
    ArrayList<ArrayList<Literal>> playerIDs= new ArrayList();

    public Ontology(int selfPlayerID, ArrayList<Card> selfCards, int numPlayers, Guess confidential) {
        this.suspects.addAll(Game.SUSPECTS);
        this.weapons.addAll(Game.WEAPONS);
        this.rooms.addAll(Game.ROOMS);

        this.removeOption(confidential.room);
        this.removeOption(confidential.suspect);
        this.removeOption(confidential.weapon);


        for(int i = 0; i < numPlayers; i++) {
            playerIDs.add(new ArrayList<Literal>());
        }

        for(int i = 0; i < selfCards.size(); i++) {
            removeOption(selfCards.get(i));

            ArrayList<Card> toAdd = new ArrayList();
            toAdd.add(selfCards.get(i));
            playerIDs.get(selfPlayerID).add(new Literal(toAdd));
        }


    }

    ArrayList<Literal> literalList(int i) {
        return playerIDs.get(i);
    }

    boolean playerDefinitelyHas(Card checkCard, int playerit) {
        ArrayList<Literal> playersLiterals = playerIDs.get(playerit);
        for(int lit = 0; lit < playersLiterals.size(); lit++) {
            if(playersLiterals.get(lit).contains(checkCard)) {
                return true;
            }
        }
        return false;
    }

    void hasA(int playerID, Literal literal) {
        if (literal.size() == 1) {
            removeOption(literal.get(0));

        }
        // iterates through each player

        for (int literalMember = 0; literalMember < literal.size(); literalMember++) {
            Card checkCard = literal.get(literalMember);

            for (int playerit = 0; playerit < playerIDs.size(); playerit++) {
                if (playerit != playerID) {
                    if (playerDefinitelyHas(checkCard, playerit)) {
                        literal.remove(checkCard);
                        literalMember--;
                        break;
                    }
                }
            }
        }

        playerIDs.get(playerID).add(literal);

        for (int playerit = 0; playerit < playerIDs.size(); playerit++) {
            for (int lit = 0; lit < literalList(playerit).size(); lit++) {
                if (literalList(playerit).get(lit).size() == 1) {
                    removeOption(literalList(playerit).get(lit).get(0));
                }
            }
        }
    }

    int playerKnown(Card card) {
        for(int i = 0; i < playerIDs.size(); i++) {
            for(int j = 0; j < playerIDs.get(i).size();j++) {
                if(playerIDs.get(i).get(j).contains(card)) {
                    return i;
                }
            }
        }
        return -1;
    }

    void removeOption(Card card) {
        if(card.getClass().getName().contains("Weapon")) {
            weapons.remove(card);
        } else if (card.getClass().getName().contains("Room")) {
            rooms.remove(card);
        } else {
            suspects.remove(card);
        }
    }

    static void testRemoveOption() {
        Tester tester = new Tester();
        ArrayList<Card> testCards= new ArrayList();
        Guess confidential = new Guess(new Suspect("Colonel Mustard"), new Room("Conservatory"), new Weapon("Wrench"));
        testCards.add(new Weapon("Knife"));
        testCards.add(new Suspect("Senator Scarlet"));
        testCards.add(new Suspect("Professor Plum"));

        Ontology ont = new Ontology(1, testCards, 3, confidential);
        ont.removeOption(new Weapon("Rope"));
        tester.test(!ont.weapons.contains(new Weapon("Rope")),"should not have rope");
        tester.test(ont.weapons.contains(new Weapon("Lead Pipe")),"should have lead pipe");
    }

    public static void main(String args[]) {
        testRemoveOption();
    }
}
