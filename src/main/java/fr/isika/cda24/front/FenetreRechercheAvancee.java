package fr.isika.cda24.front;

import fr.isika.cda24.model.ArbreBinaireRecherche;
import fr.isika.cda24.model.ListeStagiaire;
import fr.isika.cda24.model.Stagiaire;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class FenetreRechercheAvancee extends BorderPane {

	private TextField nomTextField;
	private TextField prenomTextField;
	private TextField departementTextField;
	private TextField formationTextField;
	private TextField anneeTextField;
	private Button rechercherBtn;
	private ListeStagiaire stagiaireTrouve;
	private StagiaireTableView stagiaireTableView;
	private ListeStagiaire listeImportee;
	private ArbreBinaireRecherche arbreImporte;
	private FenetrePrincipale fenetrePrincipale;

	public FenetreRechercheAvancee(ArbreBinaireRecherche arbreImporte, ListeStagiaire listeImportee,
			FenetrePrincipale fenetrePrincipale) {
		super();
		this.listeImportee = listeImportee;
		this.arbreImporte = arbreImporte;
		this.fenetrePrincipale = fenetrePrincipale;

		stagiaireTableView = new StagiaireTableView();
		
// Eléments graphique du formulaire
		BorderPane borderPaneInterRechercheAvancee = new BorderPane();

		GridPane gridPaneRechercheAvancee = new GridPane();
		gridPaneRechercheAvancee.setHgap(10);
		gridPaneRechercheAvancee.setVgap(10);
		gridPaneRechercheAvancee.setPadding(new Insets(10, 10, 10, 10));

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
		Button rechercherBtn = new Button("Rechercher");
		Button retourBtn = new Button ("Retour");

		gridPaneRechercheAvancee.add(nomLabel, 0, 1);
		gridPaneRechercheAvancee.add(nomTextField, 1, 1);
		gridPaneRechercheAvancee.add(prenomLabel, 0, 2);
		gridPaneRechercheAvancee.add(prenomTextField, 1, 2);
		gridPaneRechercheAvancee.add(departementLabel, 0, 3);
		gridPaneRechercheAvancee.add(departementTextField, 1, 3);
		gridPaneRechercheAvancee.add(formationLabel, 0, 4);
		gridPaneRechercheAvancee.add(formationTextField, 1, 4);
		gridPaneRechercheAvancee.add(anneeLabel, 0, 5);
		gridPaneRechercheAvancee.add(anneeTextField, 1, 5);
		gridPaneRechercheAvancee.add(rechercherBtn, 1, 6);
		gridPaneRechercheAvancee.add(retourBtn, 0, 6);
		gridPaneRechercheAvancee.setStyle("-fx-font-family: Arial");

		// Action sur le bouton Rechercher
		
		retourBtn.setOnAction(event ->{
			App.getStage().setScene(FenetreAuthentification.getScenePrincipale());
		});
		
		rechercherBtn.setOnAction(event -> {
			fenetrePrincipale.getStagiaireTableView().getItems().clear();
			// Récupérer les valeurs de texte entrées dans les champs de recherche
			String nom = nomTextField.getText().isEmpty() ? null : nomTextField.getText();
			String prenom = prenomTextField.getText().isEmpty() ? null : prenomTextField.getText();
			String departement = departementTextField.getText().isEmpty() ? null : departementTextField.getText();
			String formation = formationTextField.getText().isEmpty() ? null : formationTextField.getText();
			String annee = anneeTextField.getText().isEmpty() ? null : anneeTextField.getText();
			//Effectuer la recherche multicrière et mettre à jour la tableView
			stagiaireTrouve = arbreImporte.rechercherMultiCritere(nom, prenom, departement, formation, annee);
			stagiaireTrouve.afficherStagiaire();
			ObservableList<Stagiaire> stagiaires = FXCollections
					.observableArrayList(stagiaireTrouve.getListeStagiaire());
			fenetrePrincipale.getStagiaireTableView().getItems().addAll(stagiaires);
			fenetrePrincipale.getStagiaireTableView().refresh();

			App.getStage().setScene(FenetreAuthentification.getScenePrincipale());
		});

		this.setStyle("-fx-background-color: blanchedalmond; -fx-font-family: Arial");
		this.setPadding(new Insets(10, 10, 10, 10));
		this.setCenter(borderPaneInterRechercheAvancee);
		borderPaneInterRechercheAvancee.setStyle("-fx-background-color: grey; -fx-font-family: Arial;-fx-background-radius: 10px");
		borderPaneInterRechercheAvancee.setPadding(new Insets(20, 20, 20, 20));
		borderPaneInterRechercheAvancee.setCenter(gridPaneRechercheAvancee);
	}

}
