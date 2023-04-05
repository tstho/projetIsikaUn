package fr.isika.cda24.projetIsikaUn;

import fr.isika.cda24.model.ArbreBinaireRecherche;
import fr.isika.cda24.model.ListeStagiaire;
import fr.isika.cda24.model.Stagiaire;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class InterfaceStagiaire extends Application {

	private ArbreBinaireRecherche arbreBinaire;
	private ListeStagiaire stagiairesImportes;
	private ListeStagiaire stagiaireTrouve;
	private ListeStagiaire stagiaireImportesEtAjoutee;
	private ListeStagiaire stagiaireActualiser;
	boolean estConnecte = false;

	public InterfaceStagiaire() {
		arbreBinaire = new ArbreBinaireRecherche();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) {

		
// Création des éléments graphiques
		
		primaryStage.setTitle("Authentification");
		
		// Création de la scène d'authentification
		BorderPane rootAuthentification = new BorderPane();
		rootAuthentification.setPadding(new Insets(20, 20, 20, 20));

		Label usernameLabel = new Label("Nom d'utilisateur:");
		TextField usernameTextField = new TextField();
		Label passwordLabel = new Label("Mot de passe:");
		PasswordField passwordField = new PasswordField();
		Button loginBtn = new Button("Valider");

		VBox vboxAuth = new VBox();
		vboxAuth.getChildren().addAll(usernameLabel, usernameTextField, passwordLabel, passwordField, loginBtn);
		vboxAuth.setAlignment(Pos.CENTER);
		vboxAuth.setSpacing(10);

		rootAuthentification.setCenter(vboxAuth);

		// Création de la fenêtre d'authentification
		Stage authentificationStage = new Stage();
		authentificationStage.setTitle("Authentification");

		// Création des éléments de la fenêtre d'authentification
		VBox authentificationBox = new VBox();
		Label nomUtilisateurLabel = new Label("Nom d'utilisateur:");
		TextField nomUtilisateurTextField = new TextField();
		Label motDePasseLabel = new Label("Mot de passe:");
		PasswordField motDePassePasswordField = new PasswordField();
		Button validerBtn = new Button("Valider");
		Button entrezBtn = new Button("Entrez");

		// Ajout des éléments à la fenêtre d'authentification
		authentificationBox.getChildren().addAll(nomUtilisateurLabel, nomUtilisateurTextField, motDePasseLabel,
				motDePassePasswordField, validerBtn, entrezBtn);
		authentificationBox.setAlignment(Pos.CENTER);
		authentificationBox.setSpacing(10);
		Scene authentificationScene = new Scene(authentificationBox, 300, 200);
		authentificationStage.setScene(authentificationScene);
		authentificationBox.setStyle("-fx-background-color: blanchedalmond; -fx-font-family: Arial");
		
		

		// Création des BorderPane de la fenêtre principale
		BorderPane root = new BorderPane();
		root.setPadding(new Insets(10, 10, 10, 10));
		
		BorderPane rootInter = new BorderPane();
		rootInter.setPadding(new Insets(20, 20, 20, 20));

		// Création / remplissage de la HBox permettant le recherche (située TOP rootInter)
		HBox hboxRecherche = new HBox();
		
		TextField rechercheTextField = new TextField("Recherche");
		rechercheTextField.setOnTouchPressed(event -> {
			rechercheTextField.setText("");
		});
		
		ComboBox<String> attributsComboBox = new ComboBox<>();
		attributsComboBox.getItems().addAll("Tout", "Nom", "Prénom", "Département", "Formation", "Année");
		attributsComboBox.setValue("Tout");
		
		Button rechercheSimpleBtn = new Button("Recherche");
		Button rechercheAvanceBtn = new Button("Recherche Avancée");
		hboxRecherche.getChildren().addAll(rechercheTextField, attributsComboBox, rechercheSimpleBtn,
				rechercheAvanceBtn);


		
		// Création / remplissage de la HBox avec les boutons d'édition (située BOTTOM rootInter)
		HBox hboxFooter = new HBox();
		hboxFooter.setPadding(new Insets(10, 10, 10, 10));
		rootInter.setBottom(hboxFooter);

		Button ajouterBtn = new Button("Ajouter");
		Button supprimerBtn = new Button("Supprimer");
		Button modifierBtn = new Button("Modifier");
		Button exportBtn = new Button("Export / Sauvegarder");
		Button imprimerBtn = new Button("Imprimer");
		hboxFooter.getChildren().addAll(ajouterBtn, supprimerBtn, modifierBtn, exportBtn, imprimerBtn);
		hboxFooter.setAlignment(Pos.CENTER);
		
		// Création / remplissage de la HBox avec les boutons d'authification, actualisation etc.(située TOP root : Hhader)
		HBox hboxTopRoot = new HBox();
		
		Button importerFichierBtn = new Button("Importer");
		Button idBtn = new Button("Authentification");
		Button actualiserBtn = new Button("Actualiser");
		Button homeBtn = new Button("Home");
		
		hboxTopRoot.getChildren().addAll(importerFichierBtn, idBtn, actualiserBtn, homeBtn);
		hboxTopRoot.setAlignment(Pos.CENTER_RIGHT);
		hboxTopRoot.setPadding(new Insets(0, 0, 10, 0));
		
		

		// Création / remplissage de la Gridpane de la fenêtre PopUp permettant la recherche avancée;
		GridPane gridPaneRechercheAvancee = new GridPane();
		
		gridPaneRechercheAvancee.setHgap(10);
		gridPaneRechercheAvancee.setVgap(10);
		gridPaneRechercheAvancee.setPadding(new Insets(10, 10, 10, 10));

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
		Button rechercherBtn = new Button("Rechercher");

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
		gridPaneRechercheAvancee.setStyle("-fx-font-family: Arial");
		

		// Création / remplissage de la Gridpane de la fenêtre PopUp permettant l'ajout d'un stagiaire;
		GridPane gridPaneAjouter = new GridPane();
		
		gridPaneAjouter.setHgap(10);
		gridPaneAjouter.setVgap(10);
		gridPaneAjouter.setPadding(new Insets(10, 10, 10, 10));

		Label nomLabelAjouter = new Label("Nom:");
		TextField nomTextFieldAjouter = new TextField();
		Label prenomLabelAjouter = new Label("Prénom:");
		TextField prenomTextFieldAjouter = new TextField();
		Label departementLabelAjouter = new Label("Département:");
		TextField departementTextFieldAjouter = new TextField();
		Label formationLabelAjouter = new Label("Formation:");
		TextField formationTextFieldAjouter = new TextField();
		Label anneeLabelAjouter = new Label("Année:");
		TextField anneeTextFieldAjouter = new TextField();
		Button ajouterBtnPopUp = new Button("Ajouter");

		gridPaneAjouter.add(nomLabelAjouter, 0, 1);
		gridPaneAjouter.add(nomTextFieldAjouter, 1, 1);
		gridPaneAjouter.add(prenomLabelAjouter, 0, 2);
		gridPaneAjouter.add(prenomTextFieldAjouter, 1, 2);
		gridPaneAjouter.add(departementLabelAjouter, 0, 3);
		gridPaneAjouter.add(departementTextFieldAjouter, 1, 3);
		gridPaneAjouter.add(formationLabelAjouter, 0, 4);
		gridPaneAjouter.add(formationTextFieldAjouter, 1, 4);
		gridPaneAjouter.add(anneeLabelAjouter, 0, 5);
		gridPaneAjouter.add(anneeTextFieldAjouter, 1, 5);
		gridPaneAjouter.add(ajouterBtnPopUp, 1, 6);
		gridPaneAjouter.setStyle("-fx-font-family: Arial");

		// Création de la fenêtre PopUp apparaissant lorsque l'on clique sur "Recherche Avancée" de la fenêtre principale
		Stage popupRechercheAvancee = new Stage();
		popupRechercheAvancee.setTitle("Recherche Avancée");
		Scene popupScene = new Scene(gridPaneRechercheAvancee, 300, 300);
		popupRechercheAvancee.setScene(popupScene);

		rechercheAvanceBtn.setOnAction(event -> {
			popupRechercheAvancee.show();
		});
		
		// Création de la fenêtre PopUp apparaissant lorsque l'on clique sur "Ajouter" de la fenêtre principale
		Stage popupAjouter = new Stage();
		popupAjouter.setTitle("Supression");
		Scene popupSuprimerAjouter = new Scene(gridPaneAjouter, 300, 300);
		popupAjouter.setScene(popupSuprimerAjouter);

		ajouterBtn.setOnAction(event -> {
			popupAjouter.show();
		});

		// Création de la tableView qui contiendra la liste des éléments de l'arbre
		TableView<Stagiaire> stagiaireTableView = new TableView<>();
		stagiaireTableView.setPrefWidth(800);
		stagiaireTableView.setEditable(true);
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
		VBox vboxStagiaire = new VBox(stagiaireTableView);
		vboxStagiaire.setAlignment(Pos.CENTER);
		rootInter.setCenter(vboxStagiaire);
		
		
		
		

// Gestion des actions des boutons		

		// Action sur bouton "Importer" de la fenêtre principale (import du fichier et affichage infixe de l'arbre dans la tableview)
		// Création de l'arbre
		importerFichierBtn.setOnAction(event -> {
		    // Créer une boîte de dialogue de sélection de fichier
		    FileChooser fileChooser = new FileChooser();
		    fileChooser.setTitle("Sélectionnez le fichier des stagiaires");
		    fileChooser.getExtensionFilters().addAll();

		    // Ouvrir la boîte de dialogue de sélection de fichier et récupérer le fichier sélectionné
		    File selectedFile = fileChooser.showOpenDialog(primaryStage);

		    // Si un fichier a été sélectionné
		    if (selectedFile != null) {
		        // Effacer tous les éléments actuels de la table de visualisation
		        stagiaireTableView.getItems().clear();

		        // Créer un nouvel objet ListeStagiaire
		        stagiairesImportes = new ListeStagiaire();

		        // Vider l'arbre binaire
		        arbreBinaire.vider();

		        // Charger la liste des stagiaires à partir du fichier sélectionné
		        ListeStagiaire liste = new ListeStagiaire();
		        liste = liste.chargerStagiairesDepuisFichier(selectedFile.getAbsolutePath());

		        // Ajouter chaque stagiaire de la liste à l'arbre binaire
		        for (Stagiaire stagiaire : liste.getListeStagiaire()) {
		            arbreBinaire.ajoutStagiaire(stagiaire);
		        }

		        // Ajouter tous les stagiaires de l'arbre binaire à la ListeStagiaire
		        arbreBinaire.affichageInfixe(stagiairesImportes);

		        // Créer une nouvelle ObservableList à partir de la ListeStagiaire
		        ObservableList<Stagiaire> stagiaires = FXCollections.observableArrayList(stagiairesImportes.getListeStagiaire());

		        // Ajouter les stagiaires à la table de visualisation
		        stagiaireTableView.getItems().addAll(stagiaires);

		        // Mettre à jour la table de visualisation
		        stagiaireTableView.refresh();
		    }
		});
		

		// Action sur bouton "Recherche" de la fenêtre PopUp apparaissant lorsque l'on clique sur le bouton "Recherche Avancée" de la fenêtre principale (import du fichier et affichage infixe de l'arbre dans la tableview)
		rechercherBtn.setOnAction(event -> {
		    // Effacer tous les éléments actuels de la table de visualisation
		    stagiaireTableView.getItems().clear();

		    // Récupérer les valeurs de texte entrées dans les champs de recherche
		    String nom = nomTextField.getText().isEmpty() ? null : nomTextField.getText();
		    String prenom = prenomTextField.getText().isEmpty() ? null : prenomTextField.getText();
		    String departement = departementTextField.getText().isEmpty() ? null : departementTextField.getText();
		    String formation = formationTextField.getText().isEmpty() ? null : formationTextField.getText();
		    String annee = anneeTextField.getText().isEmpty() ? null : anneeTextField.getText();

		    // Rechercher les stagiaires correspondant aux critères de recherche dans l'arbre binaire
		    stagiaireTrouve = arbreBinaire.rechercherMultiCritere(nom, prenom, departement, formation, annee);

		    // Afficher les stagiaires trouvés dans la console
		    stagiaireTrouve.afficherStagiaire();

		    // Créer une nouvelle ObservableList à partir des stagiaires trouvés
		    ObservableList<Stagiaire> stagiaires = FXCollections.observableArrayList(stagiaireTrouve.getListeStagiaire());

		    // Ajouter les stagiaires à la table de visualisation
		    stagiaireTableView.getItems().addAll(stagiaires);

		    // Mettre à jour la table de visualisation
		    stagiaireTableView.refresh();

		    // Fermer la fenêtre popup de recherche avancée et afficher la nouvelle table de visualisation
		    popupRechercheAvancee.close();
		});
		
		
		// Action sur le bouton Recherche à cotés du champ de texte au dessus de la tableView (recherche simple en fonction d'un attribut)
		rechercheSimpleBtn.setOnAction(event -> {
		    // Effacer tous les éléments actuels de la table de visualisation
		    stagiaireTableView.getItems().clear();

		    // Récupérer l'attribut de recherche sélectionné dans la liste déroulante et la valeur de recherche entrée dans le champ de texte
		    String attribut = attributsComboBox.getValue();
		    String valeur = rechercheTextField.getText().isEmpty() ? null : rechercheTextField.getText();

		    // Rechercher les stagiaires correspondant aux critères de recherche dans l'arbre binaire
		    stagiaireTrouve = arbreBinaire.rechercher(attribut, valeur);

		    // Afficher les stagiaires trouvés dans la console
		    stagiaireTrouve.afficherStagiaire();

		    // Créer une nouvelle ObservableList à partir des stagiaires trouvés
		    ObservableList<Stagiaire> stagiaires = FXCollections.observableArrayList(stagiaireTrouve.getListeStagiaire());

		    // Ajouter les stagiaires à la table de visualisation
		    stagiaireTableView.getItems().addAll(stagiaires);

		    // Mettre à jour la table de visualisation
		    stagiaireTableView.refresh();
		});


		// Action sur le bouton "Entrez" de la fenêtre d'authentification
		entrezBtn.setOnAction(event -> {
		    // Fermer la fenêtre d'authentification
		    authentificationStage.close();

		    // Afficher la fenêtre principale
		    primaryStage.show();

		    // Réinitialiser l'état de connexion à false
		    estConnecte = false;

		    // Griser les boutons modifier et supprimer
		    supprimerBtn.setDisable(true);
		    modifierBtn.setDisable(true);
		});


		// Action sur le bouton "Valider" de la fenêtre d'authentification
		validerBtn.setOnAction(event -> {
		    // Récupérer les valeurs entrées dans les champs de texte de nom d'utilisateur et de mot de passe
		    String nomUtilisateur = nomUtilisateurTextField.getText();
		    String motDePasse = motDePassePasswordField.getText();

		    // Vérifier si les identifiants de connexion sont corrects
		    if (nomUtilisateur.equals("admin") && motDePasse.equals("admin")) {
		        // Fermer la fenêtre d'authentification et afficher la fenêtre principale
		        authentificationStage.close();
		        primaryStage.show();

		        // Marquer l'utilisateur comme connecté
		        estConnecte = true;

		        // Activer les boutons modifier et supprimer
		        supprimerBtn.setDisable(false);
		        modifierBtn.setDisable(false);
		    } else {
		        // Afficher une alerte si les identifiants sont incorrects
		        Alert alert = new Alert(Alert.AlertType.WARNING);
		        alert.setTitle("Authentification");
		        alert.setHeaderText("Identifiants incorrects");
		        alert.setContentText("Le nom d'utilisateur ou le mot de passe est incorrect.");
		        alert.showAndWait();
		    }
		});

		
		// Action sur le bouton "Modifier" de la fenêtre principale
		modifierBtn.setOnAction(event -> {
			if (estConnecte) {
				// code de la fonction modifier
			} else {

			}
		});
		
		// Action sur le bouton "Supprimer" de la fenêtre principale
		supprimerBtn.setOnAction(event -> {
			if (estConnecte) {
				// code de la fonction supprimer
			} else {

			}
		});
		
		// Action sur le bouton "Ajoutez" de la fenêtre PopUp qui apparait lorsque l'on clic sur "Ajoutez" de la fenêtre principale
		ajouterBtnPopUp.setOnAction(event -> {
		    // Effacer tous les éléments actuels de la table de visualisation
		    stagiaireTableView.getItems().clear();

		    // Récupérer les valeurs de texte entrées dans les champs de texte
		    String nomAdd = nomTextFieldAjouter.getText().isEmpty() ? null : nomTextFieldAjouter.getText();
		    String prenomAdd = prenomTextFieldAjouter.getText().isEmpty() ? null : prenomTextFieldAjouter.getText();
		    String departementAdd = departementTextFieldAjouter.getText().isEmpty() ? null : departementTextFieldAjouter.getText();
		    String formationAdd = formationTextFieldAjouter.getText().isEmpty() ? null : formationTextFieldAjouter.getText();
		    String anneeAdd = anneeTextFieldAjouter.getText().isEmpty() ? null : anneeTextFieldAjouter.getText();

		    // Créer un nouvel objet Stagiaire à partir des valeurs de texte récupérées
		    Stagiaire stagiaireAdd = new Stagiaire(nomAdd, prenomAdd, departementAdd, formationAdd, anneeAdd);

		    // Créer un nouvel objet ListeStagiaire
		    stagiaireImportesEtAjoutee = new ListeStagiaire();

		    // Ajouter le nouveau stagiaire à l'arbre binaire
		    arbreBinaire.ajoutStagiaire(stagiaireAdd);

		    // Ajouter tous les stagiaires de l'arbre binaire à la ListeStagiaire
		    arbreBinaire.affichageInfixe(stagiaireImportesEtAjoutee);

		    // Créer une nouvelle ObservableList à partir de la ListeStagiaire
		    ObservableList<Stagiaire> stagiaires = FXCollections.observableArrayList(stagiaireImportesEtAjoutee.getListeStagiaire());

		    // Ajouter les stagiaires à la table de visualisation
		    stagiaireTableView.getItems().addAll(stagiaires);

		    // Mettre à jour la table de visualisation
		    stagiaireTableView.refresh();

		    // Fermer la fenêtre popup actuelle
		    popupAjouter.close();
		});
		

		// Action sur bouton actualiser : affiche l'arbre actualisé mis à jour après
		// d'éventuels suppression/ajout/modification
		actualiserBtn.setOnAction(event -> {
			// Vider la TableView
			stagiaireTableView.getItems().clear();
			stagiaireActualiser = new ListeStagiaire();
			arbreBinaire.affichageInfixe(stagiaireActualiser);
			// arbreBinaire.ajoutStagiaire(stagiaire);

			// Afficher tous les stagiaires importés
			ObservableList<Stagiaire> stagiaires = FXCollections
					.observableArrayList(stagiaireActualiser.getListeStagiaire());
			stagiaireTableView.getItems().addAll(stagiaires);
			stagiaireTableView.refresh();
		});
		
		
		
		// Affichage de la fenêtre d'authentification au démarrage de l'application
		authentificationStage.show();
		rootAuthentification.setStyle("-fx-background-color: blanchedalmond; -fx-font-family: Arial");

		// Affichage de la fenêtre princiaple; insersion des éléments dans les borderPane ; résolution des problèmes de police (MAC)

		root.setStyle("-fx-background-color: blanchedalmond; -fx-font-family: Arial");
		root.setCenter(rootInter);
		root.setTop(hboxTopRoot);
		// root.setBottom(gridPane);
		rootInter.setStyle("-fx-background-color: grey; -fx-font-family: Arial;-fx-background-radius: 10px");
		rootInter.setCenter(vboxStagiaire);
		rootInter.setBottom(hboxFooter);
		rootInter.setTop(hboxRecherche);
	
		Scene scene = new Scene(root, 600, 600);
		primaryStage.setScene(scene);
	}
}
