package fr.isika.cda24.front;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import fr.isika.cda24.model.ArbreBinaireRecherche;
import fr.isika.cda24.model.ListeStagiaire;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
		authentificationBox.setStyle("-fx-background-color:papayawhip");
		
		//HBox contenant le logo
		HBox hBox = new HBox();
		hBox.setStyle("-fx-background-color:papayawhip");
		hBox.setPadding(new Insets(20, 0, 20, 0));
		hBox.setAlignment(Pos.CENTER);
		this.setTop(hBox);
		
		//logo ISIKA
		try {
			Image image = new Image(new FileInputStream("src/image/Logo_ISIKA.png"));
			ImageView imageView = new ImageView(image);
			imageView.setX(50);
			imageView.setY(25);
			imageView.setFitHeight(150);
			imageView.setFitWidth(300);
			Group grpTest = new Group(imageView);
			hBox.getChildren().add(grpTest);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//Création du formulaire de login/mot de passe 
		Label nomUtilisateurLabel = new Label("Identifiant administrateur :");
		nomUtilisateurTextField = new TextField();
		Label motDePasseLabel = new Label("Mot de passe :");
		motDePassePasswordField = new PasswordField();
		validerBtn = new Button("Valider");
		entrezBtn = new Button("Accès visiteur");
		
		//style du formulaire
		 nomUtilisateurTextField.setStyle ("-fx-background-radius:5%/100%");
		 motDePassePasswordField.setStyle ("-fx-background-radius:5%/100%");
		 validerBtn.setStyle("-fx-background-color: #EE774F");
		 validerBtn.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		 entrezBtn.setStyle("-fx-background-color: lightsalmon");
		 entrezBtn.setFont(Font.font("Arial", FontWeight.BOLD, 12));
	 
		// Remplissage de la vbox
		authentificationBox.getChildren().addAll(nomUtilisateurLabel, nomUtilisateurTextField, motDePasseLabel,
				motDePassePasswordField, validerBtn, entrezBtn);
		authentificationBox.setAlignment(Pos.CENTER);
		authentificationBox.setSpacing(12);
		authentificationBox.setPadding(new Insets(0,50,0,50));
		
		// Placement au centre de la borderPane
		this.setCenter(authentificationBox);
		this.setStyle("-fx-font-family: Arial");
		
		
		// Action sur le bouton "Entrez" de la fenêtre d'authentification
		entrezBtn.setOnAction(event -> {
			FenetrePrincipale fenetrePrincipale = new FenetrePrincipale(false, liste, abr);
			scenePrincipale = new Scene(fenetrePrincipale, 600, 700);
			App.getStage().setScene(scenePrincipale);
		});
		// Action sur le bouton "Valider" de la fenêtre d'authentification
		validerBtn.setOnAction(event -> {
			String nomUtilisateur = nomUtilisateurTextField.getText();
			String motDePasse = motDePassePasswordField.getText();
			// Vérifier si les identifiants de connexion sont corrects
			if (nomUtilisateur.equals("admin") && motDePasse.equals("admin")) {
				FenetrePrincipale fenetrePrincipale = new FenetrePrincipale(true, liste, abr);
				scenePrincipale = new Scene(fenetrePrincipale, 600, 700);
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
