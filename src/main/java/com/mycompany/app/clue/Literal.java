package com.mycompany.app.clue;

import java.util.ArrayList;

public class Literal {
    ArrayList<Card> possibleCards = new ArrayList();

    public Literal(Card card) {
        possibleCards.add(card);
    }

    public Literal(Card card1, Card card2, Card card3) {
        possibleCards.add(card1);
        possibleCards.add(card2);
        possibleCards.add(card3);
    }

    public Literal(ArrayList<Card> possibleCards) {
        this.possibleCards = possibleCards;
    }

    void remove(int i) {
        possibleCards.remove(i);
    }

    void remove(Card card) {
        possibleCards.remove(card);
    }

    boolean contains(Card card) {
        return possibleCards.contains(card);
    }

    int size() {
        return possibleCards.size();
    }

    Card get(int i) {
        return possibleCards.get(i);
    }
}
