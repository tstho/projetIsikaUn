package fr.isika.cda24.front;

import java.io.File;

import fr.isika.cda24.model.ArbreBinaireRecherche;
import fr.isika.cda24.model.ListeStagiaire;
import fr.isika.cda24.model.Stagiaire;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class FenetreModification extends BorderPane {

	private TextField nomTextField;
	private TextField prenomTextField;
	private TextField departementTextField;
	private TextField formationTextField;
	private TextField anneeTextField;
	private ListeStagiaire stagiaireListeModifie;
	private StagiaireTableView stagiaireTableView;
	private ListeStagiaire listeImportee;
	private ArbreBinaireRecherche arbreImporte;
	private FenetrePrincipale fenetrePrincipale;
	private Stagiaire stagiaireOriginal;
	private Stagiaire stagiaireModifie;

	public FenetreModification(ArbreBinaireRecherche arbreImporte, ListeStagiaire listeImportee,
			FenetrePrincipale fenetrePrincipale, Stagiaire stagiaireOriginal) {
		super();
		this.listeImportee = listeImportee;
		this.arbreImporte = arbreImporte;
		this.fenetrePrincipale = fenetrePrincipale;

		stagiaireTableView = new StagiaireTableView();
		
// Eléments graphique du formulaire
		BorderPane borderPaneInterModification = new BorderPane();

		GridPane gridPaneModification = new GridPane();
		gridPaneModification.setHgap(10);
		gridPaneModification.setVgap(10);
		gridPaneModification.setPadding(new Insets(10, 10, 10, 10));

		Label nomLabel = new Label("Nom:");
		nomTextField = new TextField();
		Label prenomLabel = new Label("Prénom:");
		prenomTextField = new TextField();
		Label departementLabel = new Label("Département:");
		departementTextField = new TextField();
		Label formationLabel = new Label("Formation:");
		formationTextField = new TextField();
		Label anneeLabel = new Label("Année:");
		anneeTextField = new TextField();
		Button modifierBtn = new Button("Modifier");
		Button retourBtn = new Button ("Retour");

		gridPaneModification.add(nomLabel, 0, 1);
		gridPaneModification.add(nomTextField, 1, 1);
		gridPaneModification.add(prenomLabel, 0, 2);
		gridPaneModification.add(prenomTextField, 1, 2);
		gridPaneModification.add(departementLabel, 0, 3);
		gridPaneModification.add(departementTextField, 1, 3);
		gridPaneModification.add(formationLabel, 0, 4);
		gridPaneModification.add(formationTextField, 1, 4);
		gridPaneModification.add(anneeLabel, 0, 5);
		gridPaneModification.add(anneeTextField, 1, 5);
		gridPaneModification.add(modifierBtn, 1, 6);
		gridPaneModification.add(retourBtn, 0, 6);
		gridPaneModification.setStyle("-fx-font-family: Arial");

// Action sur le bouton Modifier
		
		retourBtn.setOnAction(event ->{
			App.getStage().setScene(FenetreAuthentification.getScenePrincipale());
		});
		
		modifierBtn.setOnAction(event -> {
			fenetrePrincipale.getStagiaireTableView().getItems().clear();

			String nom = nomTextField.getText().isEmpty() ? null : nomTextField.getText();
			String prenom = prenomTextField.getText().isEmpty() ? null : prenomTextField.getText();
			String departement = departementTextField.getText().isEmpty() ? null : departementTextField.getText();
			String formation = formationTextField.getText().isEmpty() ? null : formationTextField.getText();
			String annee = anneeTextField.getText().isEmpty() ? null : anneeTextField.getText();

			stagiaireModifie = new Stagiaire(nom, prenom, departement, formation, annee);

			arbreImporte.modifierStagiaire(fenetrePrincipale.getStagiaireOriginal(), stagiaireModifie);

			ListeStagiaire testListe = new ListeStagiaire();
			ArbreBinaireRecherche abr = new ArbreBinaireRecherche();
			File fichierBinaire = new File("src/fichiers/stagiaires.bin");
			if (fichierBinaire.isFile()) {
				abr.initialiserArbreFichier();
				testListe = abr.affichageInfixe(testListe);
			}
			testListe.afficherStagiaire();
			stagiaireListeModifie = new ListeStagiaire();
			//Effectuer la modification et mettre à jour la tableView
			arbreImporte.initialiserArbreFichier();
			arbreImporte.affichageInfixe(stagiaireListeModifie);
			ObservableList<Stagiaire> stagiaires = FXCollections
					.observableArrayList(stagiaireListeModifie.getListeStagiaire());
			fenetrePrincipale.getStagiaireTableView().getItems().addAll(stagiaires);
			fenetrePrincipale.getStagiaireTableView().refresh();
			App.getStage().setScene(FenetreAuthentification.getScenePrincipale());
		});

		this.setStyle("-fx-background-color: blanchedalmond; -fx-font-family: Arial");
		this.setPadding(new Insets(10, 10, 10, 10));
		this.setCenter(borderPaneInterModification);
		borderPaneInterModification
				.setStyle("-fx-background-color: grey; -fx-font-family: Arial;-fx-background-radius: 10px");
		borderPaneInterModification.setPadding(new Insets(20, 20, 20, 20));
		borderPaneInterModification.setCenter(gridPaneModification);
	}

	public TextField getNomTextField() {
		return nomTextField;
	}

	public void setNomTextField(TextField nomTextField) {
		this.nomTextField = nomTextField;
	}

	public TextField getPrenomTextField() {
		return prenomTextField;
	}

	public void setPrenomTextField(TextField prenomTextField) {
		this.prenomTextField = prenomTextField;
	}

	public TextField getDepartementTextField() {
		return departementTextField;
	}

	public void setDepartementTextField(TextField departementTextField) {
		this.departementTextField = departementTextField;
	}

	public TextField getFormationTextField() {
		return formationTextField;
	}

	public void setFormationTextField(TextField formationTextField) {
		this.formationTextField = formationTextField;
	}

	public TextField getAnneeTextField() {
		return anneeTextField;
	}

	public void setAnneeTextField(TextField anneeTextField) {
		this.anneeTextField = anneeTextField;
	}

}
