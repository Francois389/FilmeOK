/*
 * ParametreClasseurControleur.java                                  09 mars 2024
 * IUT de Rodez, pas de droit d'auteur
 */

package org.fsp.filmok.controleur;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import org.fsp.filmok.FilmeOKApplication;
import org.fsp.filmok.FilmeOKApplication.View;
import org.fsp.filmok.ModelePrincipal;
import org.fsp.filmok.classeur.Classeur;
import org.fsp.filmok.classeur.ClasseurFilme;
import org.fsp.filmok.classeur.ClasseurFilme.FilmField;

import java.util.ArrayList;

/**
 * @author François de Saint Palais
 */
public class ParametreClasseurControleur {
    private static final ModelePrincipal modelePrincipal = ModelePrincipal.getInstance();
    @FXML
    public ComboBox<String> colonnesTitre;
    @FXML
    public ComboBox<String> colonnesDateSorti;
    @FXML
    public ComboBox<String> colonnesRealisateur;
    @FXML
    public ComboBox<String> colonnesDuree;
    @FXML
    public ComboBox<String> colonnesResume;
    @FXML
    public ComboBox<String> listeFeuilles;
    @FXML
    public GridPane containerChoixColonnes;
    private Classeur classeur;

    @FXML
    void initialize() {
        classeur = modelePrincipal.getClasseur().getClasseur();

        containerChoixColonnes.setDisable(true);

        ArrayList<String> feuilles = classeur.getNomsFeuilles();
        listeFeuilles.getItems().addAll(feuilles);

        listeFeuilles.valueProperty().addListener((observable, oldValue, newValue) -> {
            classeur.setFeuilleActive(newValue);
            containerChoixColonnes.setDisable(false);

            remplirListe();
        });
    }

    private void remplirListe() {
        ArrayList<String> colonnes = classeur.getNomsColonnes();

        if (colonnes.isEmpty()) {
            containerChoixColonnes.setDisable(true);
        } else {
            containerChoixColonnes.setDisable(false);

            colonnesTitre.getItems().clear();
            colonnesTitre.getItems().addAll(colonnes);

            colonnesDateSorti.getItems().clear();
            colonnesDateSorti.getItems().addAll(colonnes);

            colonnesRealisateur.getItems().clear();
            colonnesRealisateur.getItems().addAll(colonnes);

            colonnesDuree.getItems().clear();
            colonnesDuree.getItems().addAll(colonnes);

            colonnesResume.getItems().clear();
            colonnesResume.getItems().addAll(colonnes);
        }

    }

    @FXML
    public void confirmer() {
        ClasseurFilme classeurFilme = modelePrincipal.getClasseur();
        classeurFilme.updateColum(FilmField.TITRE, colonnesTitre.getSelectionModel().getSelectedIndex());
        classeurFilme.updateColum(FilmField.DATE_SORTIE, colonnesDateSorti.getSelectionModel().getSelectedIndex());
        classeurFilme.updateColum(FilmField.REALISATEUR, colonnesRealisateur.getSelectionModel().getSelectedIndex());
        classeurFilme.updateColum(FilmField.DUREE, colonnesDuree.getSelectionModel().getSelectedIndex());
        classeurFilme.updateColum(FilmField.RESUME, colonnesResume.getSelectionModel().getSelectedIndex());

        FilmeOKApplication.loadEtChangerScene(View.RESULTAT);
    }

}
