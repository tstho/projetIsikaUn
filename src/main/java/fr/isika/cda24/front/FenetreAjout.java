package fr.isika.cda24.front;

import fr.isika.cda24.model.ArbreBinaireRecherche;
import fr.isika.cda24.model.ListeStagiaire;
import fr.isika.cda24.model.Stagiaire;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FenetreAjout extends BorderPane {

	private TextField nomTextFieldAjouter;
	private TextField prenomTextFieldAjouter;
	private TextField departementTextFieldAjouter;
	private TextField formationTextFieldAjouter;
	private TextField anneeTextFieldAjouter;
	private Button ajouterBtnPopUp;
	private ListeStagiaire stagiaireImportesEtAjoutee;
	private StagiaireTableView stagiaireTableView;
	private ListeStagiaire listeImportee;
	private ArbreBinaireRecherche arbreImporte;
	private FenetrePrincipale fenetrePrincipale;

	public FenetreAjout(ArbreBinaireRecherche arbreImporte, ListeStagiaire listeImportee,
			FenetrePrincipale fenetrePrincipale) {
		super();
		this.listeImportee = listeImportee;
		this.arbreImporte = arbreImporte;
		this.fenetrePrincipale = fenetrePrincipale;
		stagiaireTableView = new StagiaireTableView();
		
		
// Eléments graphique du formulaire
		BorderPane borderPaneInterAjout = new BorderPane();

		GridPane gridPaneAjout = new GridPane();
		gridPaneAjout.setHgap(10);
		gridPaneAjout.setVgap(10);
		gridPaneAjout.setPadding(new Insets(10, 10, 10, 10));

		Label nomLabelAjouter = new Label("Nom:");
		nomTextFieldAjouter = new TextField();
		Label prenomLabelAjouter = new Label("Prénom:");
		prenomTextFieldAjouter = new TextField();
		Label departementLabelAjouter = new Label("Département:");
		departementTextFieldAjouter = new TextField();
		Label formationLabelAjouter = new Label("Formation:");
		formationTextFieldAjouter = new TextField();
		Label anneeLabelAjouter = new Label("Année:");
		anneeTextFieldAjouter = new TextField();
		Button ajouterBtnFormulaire = new Button("Ajouter");
		Button retourBtn = new Button ("Retour");

		gridPaneAjout.add(nomLabelAjouter, 0, 1);
		gridPaneAjout.add(nomTextFieldAjouter, 1, 1);
		gridPaneAjout.add(prenomLabelAjouter, 0, 2);
		gridPaneAjout.add(prenomTextFieldAjouter, 1, 2);
		gridPaneAjout.add(departementLabelAjouter, 0, 3);
		gridPaneAjout.add(departementTextFieldAjouter, 1, 3);
		gridPaneAjout.add(formationLabelAjouter, 0, 4);
		gridPaneAjout.add(formationTextFieldAjouter, 1, 4);
		gridPaneAjout.add(anneeLabelAjouter, 0, 5);
		gridPaneAjout.add(anneeTextFieldAjouter, 1, 5);
		gridPaneAjout.add(ajouterBtnFormulaire, 1, 6);
		gridPaneAjout.add(retourBtn, 0, 6);
		gridPaneAjout.setStyle("-fx-font-family: Arial");

// Action sur le bouton Ajouter
		
		retourBtn.setOnAction(event ->{
			App.getStage().setScene(FenetreAuthentification.getScenePrincipale());
		});
		
		
		ajouterBtnFormulaire.setOnAction(event -> {
			fenetrePrincipale.getStagiaireTableView().getItems().clear();
			// Récupérer les valeurs des attribut de sstagiaire entrées dans les champs de texte
			String nomAdd = nomTextFieldAjouter.getText().isEmpty() ? null : nomTextFieldAjouter.getText();
			String prenomAdd = prenomTextFieldAjouter.getText().isEmpty() ? null : prenomTextFieldAjouter.getText();
			String departementAdd = departementTextFieldAjouter.getText().isEmpty() ? null
					: departementTextFieldAjouter.getText();
			String formationAdd = formationTextFieldAjouter.getText().isEmpty() ? null
					: formationTextFieldAjouter.getText();
			String anneeAdd = anneeTextFieldAjouter.getText().isEmpty() ? null : anneeTextFieldAjouter.getText();
			// Création du stagiaire à ajouter
			Stagiaire stagiaireAdd = new Stagiaire(nomAdd, prenomAdd, departementAdd, formationAdd, anneeAdd);
			stagiaireImportesEtAjoutee = new ListeStagiaire();
			// Réalisation de l'ajout
			arbreImporte.ajoutStagiaire(stagiaireAdd);
			// Mise à jour des liste et tableView
			arbreImporte.affichageInfixe(stagiaireImportesEtAjoutee);
			ObservableList<Stagiaire> stagiaires = FXCollections
					.observableArrayList(stagiaireImportesEtAjoutee.getListeStagiaire());
			fenetrePrincipale.getStagiaireTableView().getItems().addAll(stagiaires);
			fenetrePrincipale.getStagiaireTableView().refresh();
			// Retour sur la fenêtre principale
			App.getStage().setScene(FenetreAuthentification.getScenePrincipale());
		});

		this.setStyle("-fx-background-color: blanchedalmond; -fx-font-family: Arial");
		this.setPadding(new Insets(10, 10, 10, 10));
		this.setCenter(borderPaneInterAjout);
		borderPaneInterAjout.setStyle("-fx-background-color: grey; -fx-font-family: Arial;-fx-background-radius: 10px");
		borderPaneInterAjout.setPadding(new Insets(20, 20, 20, 20));
		borderPaneInterAjout.setCenter(gridPaneAjout);

	}
}
