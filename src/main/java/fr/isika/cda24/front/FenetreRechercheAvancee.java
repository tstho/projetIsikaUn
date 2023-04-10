package fr.isika.cda24.front;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import fr.isika.cda24.model.ArbreBinaireRecherche;
import fr.isika.cda24.model.ListeStagiaire;
import fr.isika.cda24.model.Stagiaire;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * La classe FenetreRechercheAvancee est une fenêtre graphique qui contient des
 * éléments graphiques pour effectuer une recherche avancée de Stagiaires.
 */
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

	/**
	 * Constructeur de la classe FenetreRechercheAvancee.
	 * 
	 * @param arbreImporte      L'arbre binaire de recherche utilisé pour effectuer
	 *                          la recherche.
	 * @param listeImportee     La liste de stagiaires importée utilisée pour
	 *                          effectuer la recherche.
	 * @param fenetrePrincipale La fenêtre principale dans laquelle cette recherche
	 *                          est effectuée.
	 */
	public FenetreRechercheAvancee(ArbreBinaireRecherche arbreImporte, ListeStagiaire listeImportee,
			FenetrePrincipale fenetrePrincipale) {
		super();
		this.listeImportee = listeImportee;
		this.arbreImporte = arbreImporte;
		this.fenetrePrincipale = fenetrePrincipale;

		stagiaireTableView = new StagiaireTableView();

// Eléments graphique du formulaire

		// Construction du formulaire

		BorderPane borderPaneInterRechercheAvancee = new BorderPane();

		// Creation de la gridpane su formulaire et de ses éléments
		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		// gridPaneRechercheAvancee.setPadding(new Insets(10, 50, 20, 50));

		Label labelTitre = new Label("           	  Recherche Avancée :");
		// police, taille du texte
		labelTitre.setFont(Font.font("Arial", FontWeight.BOLD, 18));
		labelTitre.setTextFill(Color.WHITE);
		GridPane.setConstraints(labelTitre, 0, 0, 2, 1, HPos.CENTER, VPos.CENTER);

		Label nomLabel = new Label("Nom :");
		nomTextField = new TextField();
		nomLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		nomLabel.setTextFill(Color.WHITE);
		Label prenomLabel = new Label("Prénom :");
		prenomLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		prenomLabel.setTextFill(Color.WHITE);
		prenomTextField = new TextField();
		Label departementLabel = new Label("Département :");
		departementLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		departementLabel.setTextFill(Color.WHITE);
		departementTextField = new TextField();
		Label formationLabel = new Label("Formation :");
		formationLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		formationTextField = new TextField();
		formationLabel.setTextFill(Color.WHITE);
		Label anneeLabel = new Label("Année :");
		anneeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		anneeLabel.setTextFill(Color.WHITE);
		anneeTextField = new TextField();

		// Création d'une forme personnalisée pour les textfield
		Rectangle rect = new Rectangle(20, 20);
		rect.setArcWidth(10);
		rect.setArcHeight(10);
		BackgroundFill bgFill = new BackgroundFill(Color.WHITE, new CornerRadii(15), new Insets(0));
		Background bg = new Background(bgFill);

		DropShadow dropShadow = new DropShadow();
		dropShadow.setRadius(3);
		dropShadow.setColor(Color.BLACK);

		nomTextField.setBackground(bg);
		nomTextField.setEffect(dropShadow);
		prenomTextField.setBackground(bg);
		prenomTextField.setEffect(dropShadow);
		departementTextField.setBackground(bg);
		departementTextField.setEffect(dropShadow);
		formationTextField.setBackground(bg);
		formationTextField.setEffect(dropShadow);
		anneeTextField.setBackground(bg);
		anneeTextField.setEffect(dropShadow);

		// Mise en forme du bouton rechercher
		Button rechercherBtn = new Button("Rechercher");
		rechercherBtn.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		// fixe la taille du bouton
		rechercherBtn.setPrefHeight(45);
		rechercherBtn.setPrefWidth(300);
		Image imageRecherche;
		try {
			imageRecherche = new Image(new FileInputStream("src/image/loupe.png"));
			ImageView imageViewRech = new ImageView(imageRecherche);
			imageViewRech.setPreserveRatio(true);
			// ajuste la largeur de l'image pour qu'elle soit légèrement plus petite que le
			// bouton
			imageViewRech.setFitWidth(rechercherBtn.getPrefWidth() - 20);
			imageViewRech.setFitHeight(rechercherBtn.getPrefHeight() - 20);
			rechercherBtn.setGraphic(imageViewRech);
			rechercherBtn.setGraphicTextGap(20);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Création d'une forme personnalisée pour le bouton "Rechercher"
		Rectangle rectBtnRech = new Rectangle(30, 30);
		rectBtnRech.setArcWidth(3);
		rectBtnRech.setArcHeight(3);
		BackgroundFill bgFillBtnRech = new BackgroundFill(Color.WHITE, new CornerRadii(15), new Insets(0));
		Background bgBtnRech = new Background(bgFillBtnRech);
		rechercherBtn.setBackground(bgBtnRech);
		rechercherBtn.setEffect(dropShadow);

		// Remplissage de la gridPane
		gridPane.add(labelTitre, 0, 1);
		gridPane.add(nomLabel, 0, 4);
		gridPane.add(nomTextField, 1, 4);
		gridPane.add(prenomLabel, 0, 5);
		gridPane.add(prenomTextField, 1, 5);
		gridPane.add(departementLabel, 0, 6);
		gridPane.add(departementTextField, 1, 6);
		gridPane.add(formationLabel, 0, 7);
		gridPane.add(formationTextField, 1, 7);
		gridPane.add(anneeLabel, 0, 8);
		gridPane.add(anneeTextField, 1, 8);
		gridPane.add(rechercherBtn, 1, 11, 1, 2);
		gridPane.setAlignment(Pos.CENTER);

		// Construction du header
		// La Hbox "header" contient deux Hbox, l'une avec le logo ISIKA et l'autre avec
		// les boutons "retour" et "info"
		HBox header = new HBox();

		// Hbox avec les boutons "retour" et "info"
		HBox hboxSubHeaderBtn = new HBox();

		Button retourBtn = new Button("Retour");
		retourBtn.setFont(Font.font("Arial", FontWeight.BOLD, 9));
		retourBtn.setPrefWidth(80);
		retourBtn.setPrefHeight(35);
		Image imageRetour;
		try {
			imageRetour = new Image(new FileInputStream("src/image/fleche-de-retour.png"));
			ImageView imageViewRechSimple = new ImageView(imageRetour);
			imageViewRechSimple.setPreserveRatio(true);
			imageViewRechSimple.setFitWidth(retourBtn.getPrefWidth() - 10);
			imageViewRechSimple.setFitHeight(retourBtn.getPrefHeight() - 10);
			retourBtn.setGraphic(imageViewRechSimple);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Button aideBtn = new Button("Info");
		aideBtn.setFont(Font.font("Arial", FontWeight.BOLD, 9));
		aideBtn.setPrefWidth(80);
		aideBtn.setPrefHeight(35);
		Image imageAide;
		try {
			imageAide = new Image(new FileInputStream("src/image/bouton-web-daide.png"));
			ImageView imageViewAide = new ImageView(imageAide);
			imageViewAide.setPreserveRatio(true);
			// imageViewRechercheAvancee.setFitWidth(aideBtn.getPrefWidth() - 10);
			imageViewAide.setFitHeight(aideBtn.getPrefHeight() - 10);
			aideBtn.setGraphic(imageViewAide);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Création d'une forme personnalisée pour les boutons
		Rectangle rectBtn = new Rectangle(30, 30);
		rectBtn.setArcWidth(3);
		rectBtn.setArcHeight(3);
		BackgroundFill bgFillBtn = new BackgroundFill(Color.TRANSPARENT, new CornerRadii(15), new Insets(0));
		Background bgBtn = new Background(bgFillBtn);
		aideBtn.setBackground(bgBtn);
		retourBtn.setBackground(bgBtn);

		// Remplissage de la Hbox
		hboxSubHeaderBtn.getChildren().addAll(retourBtn, aideBtn);
		hboxSubHeaderBtn.setAlignment(Pos.CENTER_RIGHT);
		hboxSubHeaderBtn.setPadding(new Insets(0, 0, 10, 0));
		HBox.setHgrow(hboxSubHeaderBtn, Priority.ALWAYS);

		// Hbox contenant le logo ISIKA
		HBox hboxImage = new HBox();
		hboxImage.setAlignment(Pos.CENTER_LEFT);
		hboxImage.setPadding(new Insets(0, 0, 10, 5));
		HBox.setHgrow(hboxImage, Priority.ALWAYS);
		Image logoIsika;
		try {
			logoIsika = new Image(new FileInputStream("src/image/Logo_ISIKA.png"));
			ImageView imageViewLogoIsika = new ImageView(logoIsika);
			imageViewLogoIsika.setPreserveRatio(true);
			// Ajustement de la taille de l'image à la taille des boutons (de leur police)
			double buttonFontSize = retourBtn.getFont().getSize();
			imageViewLogoIsika.setFitHeight(buttonFontSize + 30);
			// ajout du logo à la hbox
			hboxImage.getChildren().add(imageViewLogoIsika);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Replissage de la Hbox header
		header.getChildren().addAll(hboxImage, hboxSubHeaderBtn);
		// ajuste la taille de la Hbox à la largeur de la bordePane
		borderPaneInterRechercheAvancee.widthProperty().addListener((obs, oldVal, newVal) -> {
			header.setPrefWidth(newVal.doubleValue());
		});

		// Mise en forme des BorderPane
		this.setStyle("-fx-background-color: papayawhip; -fx-font-family: Arial");
		this.setPadding(new Insets(10, 10, 10, 10));
		this.setCenter(borderPaneInterRechercheAvancee);
		this.setTop(header);
		borderPaneInterRechercheAvancee
				.setStyle("-fx-background-color: #454343; -fx-font-family: Arial;-fx-background-radius: 10px");
		borderPaneInterRechercheAvancee.setPadding(new Insets(20, 20, 20, 20));
		borderPaneInterRechercheAvancee.setCenter(gridPane);

// Action sur les boutons

		// Action sur le bouton "Aide/Info"
		aideBtn.setOnAction(e -> {

			Desktop desktop = Desktop.getDesktop();
			File pdfFile = new File("src/pdf/annuaire.pdf");
			try {
				desktop.open(pdfFile);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});

		// Action sur le bouton "Retour"
		retourBtn.setOnAction(event -> {
			App.getStage().setScene(FenetreAuthentification.getScenePrincipale());
		});

		// Action sur le bouton "Rechercher"
		rechercherBtn.setOnAction(event -> {
			fenetrePrincipale.getStagiaireTableView().getItems().clear();
			// Récupérer les valeurs de texte entrées dans les champs de recherche
			String nom = nomTextField.getText().isEmpty() ? null : nomTextField.getText();
			String prenom = prenomTextField.getText().isEmpty() ? null : prenomTextField.getText();
			String departement = departementTextField.getText().isEmpty() ? null : departementTextField.getText();
			String formation = formationTextField.getText().isEmpty() ? null : formationTextField.getText();
			String annee = anneeTextField.getText().isEmpty() ? null : anneeTextField.getText();
			// Effectuer la recherche multicrière et mettre à jour la tableView
			stagiaireTrouve = arbreImporte.rechercherMultiCritere(nom, prenom, departement, formation, annee);
			stagiaireTrouve.afficherStagiaire();
			ObservableList<Stagiaire> stagiaires = FXCollections
					.observableArrayList(stagiaireTrouve.getListeStagiaire());
			fenetrePrincipale.getStagiaireTableView().getItems().addAll(stagiaires);
			fenetrePrincipale.getStagiaireTableView().refresh();

			App.getStage().setScene(FenetreAuthentification.getScenePrincipale());
		});

	}
}
