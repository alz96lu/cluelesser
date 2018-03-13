package com.mycompany.app.clue;


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


public class ClueOntologyManager {

    OWLOntologyManager manager;
    OWLDataFactory factory;

    ClueOntologyManager() {
        this.manager = OWLManager.createOWLOntologyManager();
        this.factory = manager.getOWLDataFactory();

    }


}
