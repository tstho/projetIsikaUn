package fr.isika.cda24.front;

import fr.isika.cda24.model.ArbreBinaireRecherche;
import fr.isika.cda24.model.ListeStagiaire;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FenetreAuthentification extends BorderPane {
	private TextField nomUtilisateurTextField;
	private PasswordField motDePassePasswordField;
	private Button validerBtn;
	private Button entrezBtn;
	private static Scene scenePrincipale;
	private ListeStagiaire liste;
	private ArbreBinaireRecherche abr;

	public FenetreAuthentification(ListeStagiaire listeRecu, ArbreBinaireRecherche abrRecu) {
		super();
		this.liste = listeRecu;
		this.abr = abrRecu;
		// Création des éléments de la fenêtre d'authentification
		VBox authentificationBox = new VBox();
		Label nomUtilisateurLabel = new Label("Nom d'utilisateur:");
		nomUtilisateurTextField = new TextField();
		Label motDePasseLabel = new Label("Mot de passe:");
		motDePassePasswordField = new PasswordField();
		validerBtn = new Button("Valider");
		entrezBtn = new Button("Entrez");
		// Remplissage de la vbox
		authentificationBox.getChildren().addAll(nomUtilisateurLabel, nomUtilisateurTextField, motDePasseLabel,
				motDePassePasswordField, validerBtn, entrezBtn);
		authentificationBox.setAlignment(Pos.CENTER);
		authentificationBox.setSpacing(10);
		// Placement au centre de la borderPane
		this.setCenter(authentificationBox);
		
		
		
		
		// Action sur le bouton "Entrez" de la fenêtre d'authentification
		entrezBtn.setOnAction(event -> {
			FenetrePrincipale fenetrePrincipale = new FenetrePrincipale(false, liste, abr);
			scenePrincipale = new Scene(fenetrePrincipale, 600, 600);
			App.getStage().setScene(scenePrincipale);
		});
		// Action sur le bouton "Valider" de la fenêtre d'authentification
		validerBtn.setOnAction(event -> {
			String nomUtilisateur = nomUtilisateurTextField.getText();
			String motDePasse = motDePassePasswordField.getText();
			// Vérifier si les identifiants de connexion sont corrects
			if (nomUtilisateur.equals("admin") && motDePasse.equals("admin")) {
				FenetrePrincipale fenetrePrincipale = new FenetrePrincipale(true, liste, abr);
				scenePrincipale = new Scene(fenetrePrincipale, 600, 600);
				App.getStage().setScene(scenePrincipale);
			} else {
				// Afficher une alerte si les identifiants sont incorrects
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setTitle("Authentification");
				alert.setHeaderText("Identifiants incorrects");
				alert.setContentText("Le nom d'utilisateur ou le mot de passe est incorrect.");
				alert.showAndWait();
			}
		});
		this.setStyle("-fx-font-family: Arial");
	}

	public static Scene getScenePrincipale() {
		return scenePrincipale;
	}

	public static void setScenePrincipale(Scene scenePrincipale) {
		FenetreAuthentification.scenePrincipale = scenePrincipale;
	}

	public TextField getNomUtilisateurTextField() {
		return nomUtilisateurTextField;
	}

	public PasswordField getMotDePassePasswordField() {
		return motDePassePasswordField;
	}

	public Button getValiderBtn() {
		return validerBtn;
	}

	public Button getEntrezBtn() {
		return entrezBtn;
	}
}
