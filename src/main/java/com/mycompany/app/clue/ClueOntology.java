package com.mycompany.app.clue;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

import java.util.ArrayList;

public class ClueOntology {
    ClueOntologyManager clueManager;
    OWLOntology ontology;

    DefaultPrefixManager pm;

    ArrayList<OWLNamedIndividual> suspectIndividuals;
    ArrayList<OWLNamedIndividual> roomIndividuals;
    ArrayList<OWLNamedIndividual> weaponIndividuals;
    ArrayList<OWLNamedIndividual> playerIndividuals;

    private static final String DOCUMENT_IRI = "http://clueless/example.owl";


    public ClueOntology(ClueOntologyManager clueManager, Game game) throws OWLOntologyCreationException {


        this.clueManager = clueManager;
        this.ontology = clueManager.manager.createOntology(IRI.create(DOCUMENT_IRI));
        pm = new DefaultPrefixManager();
        pm.setDefaultPrefix(DOCUMENT_IRI + "#");
        pm.setPrefix("var:", "urn:swrl#");

        OWLClass suspectClass = this.clueManager.factory.getOWLClass(":Suspect", pm);
        this.clueManager.manager.addAxiom(this.ontology, this.clueManager.factory.getOWLDeclarationAxiom(suspectClass));

        OWLClass roomClass = this.clueManager.factory.getOWLClass(":Room", pm);
        this.clueManager.manager.addAxiom(this.ontology, this.clueManager.factory.getOWLDeclarationAxiom(roomClass));

        OWLClass weaponClass = this.clueManager.factory.getOWLClass(":Weapon", pm);
        this.clueManager.manager.addAxiom(this.ontology, this.clueManager.factory.getOWLDeclarationAxiom(weaponClass));

        OWLClass playerClass = this.clueManager.factory.getOWLClass(":Player", pm);
        this.clueManager.manager.addAxiom(this.ontology, this.clueManager.factory.getOWLDeclarationAxiom(playerClass));

        for(int i = 0; i < game.SUSPECTS.size(); i++) {
            suspectIndividuals.add(createIndividual(this.ontology, this.pm, this.clueManager.manager, ":" + game.SUSPECTS.get(i).getName()));
        }

        for(int i = 0; i < game.ROOMS.size(); i++) {
            roomIndividuals.add(createIndividual(this.ontology, this.pm, this.clueManager.manager, ":" + game.ROOMS.get(i).getName()));
        }

        for(int i = 0; i < game.WEAPONS.size(); i++) {
            weaponIndividuals.add(createIndividual(this.ontology, this.pm, this.clueManager.manager, ":" + game.WEAPONS.get(i).getName()));
        }

        for(int i = 0; i < game.playerRing.numPlayers(); i++) {
            playerIndividuals.add(createIndividual(this.ontology, this.pm, this.clueManager.manager, ":player" + i));
        }

        OWLObjectProperty belongsTo = createObjectProperty(this.ontology,this.pm, this.clueManager.manager, "belongsTo");
    }

    private OWLNamedIndividual createIndividual(OWLOntology ontology, DefaultPrefixManager pm, OWLOntologyManager manager, String name) {
        OWLNamedIndividual individual = this.clueManager.factory.getOWLNamedIndividual(name, pm);
        manager.addAxiom(ontology, this.clueManager.factory.getOWLDeclarationAxiom(individual));
        return individual;
    }

    private OWLObjectProperty createObjectProperty(OWLOntology ontology, DefaultPrefixManager pm, OWLOntologyManager manager, String name) {
        OWLObjectProperty objectProperty = this.clueManager.factory.getOWLObjectProperty(name, pm);
        manager.addAxiom(ontology, this.clueManager.factory.getOWLDeclarationAxiom(objectProperty));
        return objectProperty;


    }
}
