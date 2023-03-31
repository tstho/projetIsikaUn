package fr.isika.cda24.projetIsikaUn;

import fr.isika.cda24.model.ArbreBinaireRecherche;
import fr.isika.cda24.model.FichierStagiaire;
import fr.isika.cda24.model.Stagiaire;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class InterfaceStagiaire extends Application {

    private ArbreBinaireRecherche abr;

    public InterfaceStagiaire() {
        abr = new ArbreBinaireRecherche();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Recherche de Stagiaires");

        // Création du BorderPane
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10, 10, 10, 10));

        // Création du GridPane
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        root.setCenter(gridPane);

        // Titre
        Label titre = new Label("Recherche de Stagiaires");
        titre.setFont(new Font("Arial", 20));
        root.setTop(titre);
        BorderPane.setAlignment(titre, javafx.geometry.Pos.CENTER);

        // Bouton pour importer le fichier
        Button importerFichierBtn = new Button("Importer fichier");
        gridPane.add(importerFichierBtn, 0, 0);

        // Labels et TextFields pour les critères de recherche
        Label nomLabel = new Label("Nom:");
        TextField nomTextField = new TextField();
        Label prenomLabel = new Label("Prénom:");
        TextField prenomTextField = new TextField();
        Label departementLabel = new Label("Département:");
        TextField departementTextField = new TextField();
        Label formationLabel = new Label("Formation:");
        TextField formationTextField = new TextField();
        Label anneeLabel = new Label("Année:");
        TextField anneeTextField = new TextField();

        gridPane.add(nomLabel, 0, 1);
        gridPane.add(nomTextField, 1, 1);
        gridPane.add(prenomLabel, 0, 2);
        gridPane.add(prenomTextField, 1, 2);
        gridPane.add(departementLabel, 0, 3);
        gridPane.add(departementTextField, 1, 3);
        gridPane.add(formationLabel, 0, 4);
        gridPane.add(formationTextField, 1, 4);
        gridPane.add(anneeLabel, 0, 5);
        gridPane.add(anneeTextField, 1, 5);

        // Bouton pour effectuer la recherche multicritère
        Button rechercherBtn = new Button("Rechercher");
        gridPane.add(rechercherBtn, 1, 6);

        // TextArea pour afficher les stagiaires trouvés
        //TextArea resultatArea = new TextArea();

        
        //TableView
    	TableView<Stagiaire> stagiaireTableView = new TableView<>();
    	stagiaireTableView.setPrefWidth(700);

    	// Créer des colonnes pour la TableView
    	TableColumn<Stagiaire, String> nomCol = new TableColumn<>("Nom");
    	nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));

    	TableColumn<Stagiaire, String> prenomCol = new TableColumn<>("Prénom");
    	prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));

    	TableColumn<Stagiaire, String> departementCol = new TableColumn<>("Département");
    	departementCol.setCellValueFactory(new PropertyValueFactory<>("departement"));

    	TableColumn<Stagiaire, String> formationCol = new TableColumn<>("Formation");
    	formationCol.setCellValueFactory(new PropertyValueFactory<>("formation"));

    	TableColumn<Stagiaire, String> anneeCol = new TableColumn<>("Année");
    	anneeCol.setCellValueFactory(new PropertyValueFactory<>("annee"));

    	// Ajouter les colonnes à la TableView
    	stagiaireTableView.getColumns().addAll(nomCol, prenomCol, departementCol, formationCol, anneeCol);

        // Gestion des actions des boutons
        importerFichierBtn.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Sélectionnez le fichier des stagiaires");
            fileChooser.getExtensionFilters().addAll(
                //new FileChooser.ExtensionFilter("Fichiers texte", "*.txt"),
                //new FileChooser.ExtensionFilter("Tous les fichiers", "*.*")
            );
            
            
            
            ArbreBinaireRecherche arbreStagiaires = new ArbreBinaireRecherche();
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                ArrayList<Stagiaire> stagiaires = FichierStagiaire.chargerStagiairesDepuisFichier(selectedFile.getAbsolutePath());
                ObservableList<Stagiaire> stagiairesImportes = FXCollections.observableArrayList(stagiaires);
                stagiairesImportes.addAll(stagiaires);
                stagiaireTableView.getItems().clear();
                stagiaireTableView.getItems().addAll(stagiairesImportes);
            }
//            if (selectedFile != null) {
//                List<Stagiaire> stagiaires = FichierStagiaire.chargerStagiairesDepuisFichier(selectedFile.getAbsolutePath());
//                for (Stagiaire stagiaire : stagiaires) {
//					arbreStagiaires.inserer(stagiaire);
//                }
//             
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("Importation réussie");
//                alert.setHeaderText(null);
//                alert.setContentText("Importation réussie");
//                alert.showAndWait();
//            } else {
//                Alert alert = new Alert(Alert.AlertType.WARNING);
//                alert.setTitle("Aucun fichier sélectionné");
//                alert.setHeaderText(null);
//                alert.setContentText("Aucun fichier sélectionné");
//                alert.showAndWait();
//            }
        });

        rechercherBtn.setOnAction(e -> {
            String nom = nomTextField.getText();
            String prenom = prenomTextField.getText();
            String departement = departementTextField.getText();
            String formation = formationTextField.getText();
            String annee = anneeTextField.getText();
            

            // Effectuer la recherche multicritère
            ArrayList<Stagiaire> resultatRecherche = abr.rechercherMultiCritere(nom, prenom, departement, formation, annee);

            // Afficher les stagiaires trouvés
            //stagiairesImportes.clear();
            StringBuilder resultats = new StringBuilder();
            if (resultatRecherche.size() > 0) {
                for (Stagiaire stagiaire : resultatRecherche) {
    
                    stagiaireTableView.getItems().clear();
                    stagiaireTableView.getItems().addAll(resultatRecherche);
                }
            } else {
                resultats.append("Aucun stagiaire trouvé avec ces critères.");
            }
//            stagiaireTableView.getItems().clear();
//            stagiaireTableView.getItems().add(resultats);
        });
        
        stagiaireTableView.setEditable(false);
        root.setTop(stagiaireTableView);

        // Création de la scène et affichage
       root.setStyle("-fx-background-color: grey; -fx-font-family: Arial");
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}


