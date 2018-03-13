package com.mycompany.app;

import com.clarkparsia.owlapiv3.OWL;
import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.dlsyntax.renderer.DLSyntaxObjectRenderer;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import uk.ac.manchester.cs.owl.owlapi.OWLNegativeDataPropertyAssertionAxiomImpl;

import java.io.File;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class CreateOntology {

        private static final String DOCUMENT_IRI = "http://clueless/example.owl";
        private static OWLObjectRenderer renderer = new DLSyntaxObjectRenderer();

        public static void main(String[] args) throws OWLOntologyCreationException, OWLOntologyStorageException {

            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            OWLDataFactory factory = manager.getOWLDataFactory();

            // create new empty ontology
            OWLOntology ontology = manager.createOntology(IRI.create(DOCUMENT_IRI));
            //set up prefixes
            DefaultPrefixManager pm = new DefaultPrefixManager();
            pm.setDefaultPrefix(DOCUMENT_IRI + "#");
            pm.setPrefix("var:", "urn:swrl#");

            //class declarations
            OWLClass suspectClass = factory.getOWLClass(":Suspect", pm);
            manager.addAxiom(ontology, factory.getOWLDeclarationAxiom(suspectClass));

            OWLClass weaponClass = factory.getOWLClass(":weaponClass", pm);
            manager.addAxiom(ontology, factory.getOWLDeclarationAxiom(weaponClass));

            OWLClass roomClass = factory.getOWLClass(":roomClass", pm);
            manager.addAxiom(ontology, factory.getOWLDeclarationAxiom(roomClass));

            //named individuals declarations
//            OWLNamedIndividual english = createIndividual(ontology, pm, manager, ":English");
//            OWLNamedIndividual comp = createIndividual(ontology, pm, manager, ":Computer-Programming");
//            OWLNamedIndividual john = createIndividual(ontology, pm, manager, ":John");
            OWLNamedIndividual mrsWhite = createIndividual(ontology, pm, manager, ":mrs_white");
            OWLNamedIndividual mrGreen = createIndividual(ontology, pm, manager, ":mr_green");
            OWLNamedIndividual kitchen = createIndividual(ontology, pm, manager, ":kitchen");
            OWLNamedIndividual bedroom = createIndividual(ontology, pm, manager, ":bedroom");
            OWLNamedIndividual rope = createIndividual(ontology, pm, manager, ":rope");
            OWLNamedIndividual knife = createIndividual(ontology, pm, manager, ":knife");


            //annotated subclass axiom
            OWLAnnotationProperty annotationProperty = factory.getOWLAnnotationProperty(OWLRDFVocabulary.RDFS_COMMENT.getIRI());
            OWLAnnotationValue value = factory.getOWLLiteral("States that every man is a person.");
            OWLAnnotation annotation = factory.getOWLAnnotation(annotationProperty, value);
            //OWLSubClassOfAxiom subClassOfAxiom = factory.getOWLSubClassOfAxiom(manClass, personClass, Collections.singleton(annotation));
            //manager.addAxiom(ontology, subClassOfAxiom);

            //object property declaration
            //OWLObjectProperty speaksLanguageProperty = createObjectProperty(ontology, pm, manager, ":speaksLanguage");
            //OWLObjectProperty hasKnowledgeOfProperty = createObjectProperty(ontology, pm, manager, ":hasKnowledgeOf");
            OWLObjectProperty hasObjectProperty = createObjectProperty(ontology,pm, manager, "hasObject");
            OWLObjectProperty inRoomProperty = createObjectProperty(ontology,pm, manager, "inRoom");

            //axiom - Mrs White is a suspect
            manager.addAxiom(ontology, factory.getOWLClassAssertionAxiom(suspectClass, mrsWhite));
            manager.addAxiom(ontology, factory.getOWLClassAssertionAxiom(suspectClass, mrGreen));
            //axiom- Kitchen is a room
            manager.addAxiom(ontology, factory.getOWLClassAssertionAxiom(roomClass, kitchen));
            manager.addAxiom(ontology, factory.getOWLClassAssertionAxiom(roomClass, bedroom));

            manager.addAxiom(ontology, factory.getOWLClassAssertionAxiom(weaponClass, rope));
            manager.addAxiom(ontology, factory.getOWLClassAssertionAxiom(weaponClass, knife));

            //axiom- Mr Green has the knife, therefore mrs white must not have the knife
            manager.addAxiom(ontology, factory.getOWLObjectPropertyAssertionAxiom(hasObjectProperty, mrGreen, knife));
            manager.addAxiom(ontology, factory.getOWLNegativeObjectPropertyAssertionAxiom(hasObjectProperty, mrsWhite, knife));

            //axiom- Mr Green is in the kitchen
            manager.addAxiom(ontology, factory.getOWLObjectPropertyAssertionAxiom(inRoomProperty, mrGreen, kitchen));


            //axiom - John speaksLanguage English
           // manager.addAxiom(ontology, factory.getOWLObjectPropertyAssertionAxiom(speaksLanguageProperty, john, english));
            //axiom - John hasKnowledgeOf Computer-Programming
            //manager.addAxiom(ontology, factory.getOWLObjectPropertyAssertionAxiom(hasKnowledgeOfProperty, john, comp));

            //axiom - EnglishProgrammers is equivalent to intersection of classes
//            OWLObjectHasValue c1 = factory.getOWLObjectHasValue(speaksLanguageProperty, english);
//            OWLObjectHasValue c2 = factory.getOWLObjectHasValue(hasKnowledgeOfProperty, comp);
//            OWLObjectIntersectionOf andExpr = factory.getOWLObjectIntersectionOf(personClass, c1, c2);
//            manager.addAxiom(ontology, factory.getOWLEquivalentClassesAxiom(englishProgrammerClass, andExpr));


            //SWRL rule - Person(?x),speaksLanguage(?x,English),hasKnowledgeOf(?x,Computer-Programming)->englishProgrammer(?x)
            //SWRLVariable varX = factory.getSWRLVariable(pm.getIRI("var:x"));
            //Set<SWRLAtom> body = new LinkedHashSet<>();
            //body.add(factory.getSWRLClassAtom(suspectClass, varX));
            //body.add(factory.getSWRLObjectPropertyAtom(speaksLanguageProperty, varX, factory.getSWRLIndividualArgument(english)));
            //body.add(factory.getSWRLObjectPropertyAtom(hasKnowledgeOfProperty, varX, factory.getSWRLIndividualArgument(comp)));
            //Set<? extends SWRLAtom> head = Collections.singleton(factory.getSWRLClassAtom(englishProgrammerClass, varX));
            //SWRLRule swrlRule = factory.getSWRLRule(body, head);
            //manager.addAxiom(ontology, swrlRule);

            //save  to a file
            FunctionalSyntaxDocumentFormat ontologyFormat = new FunctionalSyntaxDocumentFormat();
            ontologyFormat.copyPrefixesFrom(pm);
            manager.saveOntology(ontology, ontologyFormat, IRI.create(new File("clueless.owl").toURI()));

            //reason
            OWLReasonerFactory reasonerFactory = PelletReasonerFactory.getInstance();
            OWLReasoner reasoner = reasonerFactory.createReasoner(ontology, new SimpleConfiguration());
            for (OWLNamedIndividual person : reasoner.getInstances(suspectClass, false).getFlattened()) {
                System.out.println("person : " + renderer.render(person));
            }
            //for (OWLNamedIndividual englishProgrammer : reasoner.getInstances(englishProgrammerClass, false).getFlattened()) {
              //  System.out.println("englishProgrammer : " + renderer.render(englishProgrammer));
            //}
        }

        private static OWLNamedIndividual createIndividual(OWLOntology ontology, DefaultPrefixManager pm, OWLOntologyManager manager, String name) {
            OWLDataFactory factory = manager.getOWLDataFactory();
            OWLNamedIndividual individual = factory.getOWLNamedIndividual(name, pm);
            manager.addAxiom(ontology, factory.getOWLDeclarationAxiom(individual));
            return individual;
        }

        private static OWLObjectProperty createObjectProperty(OWLOntology ontology, DefaultPrefixManager pm, OWLOntologyManager manager, String name) {
            OWLDataFactory factory = manager.getOWLDataFactory();
            OWLObjectProperty objectProperty = factory.getOWLObjectProperty(name, pm);
            manager.addAxiom(ontology, factory.getOWLDeclarationAxiom(objectProperty));
            return objectProperty;


        }

    private static OWLNegativeObjectPropertyAssertionAxiom createNegativeObjectProperty(OWLOntology ontology, OWLOntologyManager manager, OWLNamedIndividual name, OWLNamedIndividual name2, OWLObjectProperty property) {
        OWLDataFactory factory = manager.getOWLDataFactory();
        //OWLObjectProperty negObjectProperty = factory.getOWLNegativeObjectProperty(name);
        OWLNegativeObjectPropertyAssertionAxiom negObjectProperty = factory.getOWLNegativeObjectPropertyAssertionAxiom(property, name, name2);
        manager.addAxiom(ontology, negObjectProperty);
        return negObjectProperty;


    }


}



