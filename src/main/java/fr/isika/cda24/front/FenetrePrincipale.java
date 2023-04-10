package fr.isika.cda24.front;

import java.awt.Cursor;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Optional;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import fr.isika.cda24.front.FenetreRechercheAvancee;
import fr.isika.cda24.model.ArbreBinaireRecherche;
import fr.isika.cda24.model.ListeStagiaire;
import fr.isika.cda24.model.Stagiaire;
import javafx.application.HostServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DialogPane;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * La classe FenetrePrincipale est une fenêtre graphique qui contient les
 * éléments suivants : un arbre binaire de recherche, des listes de stagiaires
 * pour la recherche simple, la recherche avancée, l'ajout, la modification et
 * la suppression de stagiaires, une table view pour afficher les résultats de
 * recherche, ainsi que des fenêtres pour la recherche avancée, l'ajout, la
 * modification et l'authentification.
 */
public class FenetrePrincipale extends BorderPane {

	private ArbreBinaireRecherche arbreBinaire;
	private ListeStagiaire stagiairesImportes;
	private ListeStagiaire stagiaireTrouveSimple;
	private ListeStagiaire stagiaireImportesEtAjoutee;
	private ListeStagiaire stagiaireActualiser;
	private ListeStagiaire stagiaireTrouve;
	private ListeStagiaire stagiaireApresSupression;
	private ListeStagiaire liste;
	private StagiaireTableView stagiaireTableView;
	private FenetreRechercheAvancee fenetreRechercheAvancee;
	private FenetreAjout fenetreAjout;
	private FenetreModification fenetreModification;
	private FenetreAuthentification fenetreAuthentification;
	private Stagiaire stagiaireOriginal;

	private static Scene sceneRechercheAvancee;
	private static Scene sceneAjout;
	private static Scene sceneModification;
	private static Scene sceneAuthentification;

	private Boolean admin = false;
	
	
	/**
	 * Constructeur de la classe FenetrePrincipale.
	 * 
	 * @param admin un booléen indiquant si l'utilisateur est administrateur ou non.
	 * @param listeRecu une liste de stagiaires.
	 * @param abrRecu un arbre binaire de recherche construit depuis un fichier binaire.
	 */
	public FenetrePrincipale(Boolean admin, ListeStagiaire listeRecu, ArbreBinaireRecherche abrRecu) {
		super();
		arbreBinaire = new ArbreBinaireRecherche();
		stagiaireTableView = new StagiaireTableView();
		liste = new ListeStagiaire();

		liste = listeRecu;
		arbreBinaire = abrRecu;

// Création des éléments graphiques

		// BorderPane(s) contenant tout les élements

		BorderPane rootInter = new BorderPane();
		rootInter.setPadding(new Insets(20, 20, 20, 20));

		// Création de la HBox de recherche et de ses élements
		HBox hboxRecherche = new HBox(10);
		// Création du textField dans lequel on effectue la recherche simple et action
		// de la souris dessus
		TextField rechercheTextField = new TextField("Recherche");
		rechercheTextField.setPrefWidth(30);
		rechercheTextField.setPrefHeight(30);

		rechercheTextField.setOnMouseClicked(event -> {
			if (rechercheTextField.getText().equals("Recherche")) {
				rechercheTextField.setText("");
			}
		});

		rechercheTextField.focusedProperty().addListener((obs, oldVal, newVal) -> {
			if (!newVal) { // Lorsque le focus est perdu
				if (rechercheTextField.getText().isEmpty()) {
					rechercheTextField.setText("Recherche");
				}
			}
		});

		// Création d'une forme personnalisée pour le textfield
		Rectangle rect = new Rectangle(10, 10);
		rect.setArcWidth(10);
		rect.setArcHeight(10);
		BackgroundFill bgFill = new BackgroundFill(Color.WHITE, new CornerRadii(15), new Insets(0));
		Background bg = new Background(bgFill);
		rechercheTextField.setBackground(bg);

		// Permet au TextField de remplir l'espace restant
		HBox.setHgrow(rechercheTextField, Priority.ALWAYS);
		hboxRecherche.setPadding(new Insets(0, 0, 10, 0));

		// Bouton déroulant pour choisir le critère dans lequel effectuer la recherche
		ComboBox<String> attributsComboBox = new ComboBox<>();
		attributsComboBox.getItems().addAll("Tout", "Nom", "Prénom", "Département", "Formation", "Année");
		attributsComboBox.setValue("Tout");
		attributsComboBox.setPrefHeight(30);

		// Mise en forme des boutons
		Button rechercheSimpleBtn = new Button("");
		// fixe la taille du bouton
		rechercheSimpleBtn.setPrefSize(30, 30);
		Image imageRechercheSimple;
		try {
			imageRechercheSimple = new Image(new FileInputStream("src/image/loupe.png"));
			ImageView imageViewRechSimple = new ImageView(imageRechercheSimple);
			imageViewRechSimple.setPreserveRatio(true);
			// ajuste la largeur de l'image pour qu'elle soit légèrement plus petite que le
			// bouton
			imageViewRechSimple.setFitHeight(rechercheSimpleBtn.getPrefHeight() - 10);
			rechercheSimpleBtn.setGraphic(imageViewRechSimple);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Button rechercheAvanceBtn = new Button("");
		rechercheAvanceBtn.setPrefSize(30, 30);
		Image imageRechercheAvancee;
		try {
			imageRechercheAvancee = new Image(new FileInputStream("src/image/recherche-et-developpement.png"));
			ImageView imageViewRechercheAvancee = new ImageView(imageRechercheAvancee);
			imageViewRechercheAvancee.setPreserveRatio(true);
			imageViewRechercheAvancee.setFitHeight(rechercheAvanceBtn.getPrefHeight() - 10);
			rechercheAvanceBtn.setGraphic(imageViewRechercheAvancee);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Button updateBtn = new Button("");
		updateBtn.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		updateBtn.setPrefSize(30, 30);
		Image imageRetour;
		try {
			imageRetour = new Image(new FileInputStream("src/image/rafraichir (1).png"));
			ImageView imageViewRetour = new ImageView(imageRetour);
			imageViewRetour.setPreserveRatio(true);
			imageViewRetour.setFitHeight(updateBtn.getPrefHeight() - 10);
			updateBtn.setGraphic(imageViewRetour);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Création d'une forme personnalisée pour les boutons à coté de la barre de
		// recherche
		Rectangle rectBtn = new Rectangle(10, 10);
		rectBtn.setArcWidth(3);
		rectBtn.setArcHeight(3);
		BackgroundFill bgFillBtn = new BackgroundFill(Color.WHITE, new CornerRadii(15), new Insets(0));
		Background bgBtn = new Background(bgFillBtn);
		rechercheAvanceBtn.setBackground(bgBtn);
		rechercheSimpleBtn.setBackground(bgBtn);
		updateBtn.setBackground(bgBtn);
		attributsComboBox.setBackground(bgBtn);

		// Ajout des éléments à la Hbox de recherche
		hboxRecherche.getChildren().addAll(rechercheTextField, attributsComboBox, rechercheSimpleBtn,
				rechercheAvanceBtn, updateBtn);

		// AnchorPane contenant la tableview
		AnchorPane anchorTableView = new AnchorPane(stagiaireTableView);
		AnchorPane.setTopAnchor(stagiaireTableView, 0.0);
		AnchorPane.setBottomAnchor(stagiaireTableView, 0.0);
		AnchorPane.setLeftAnchor(stagiaireTableView, 0.0);
		AnchorPane.setRightAnchor(stagiaireTableView, 0.0);

		// Lier la largeur de l'HBox avec la largeur de l'AnchorPane
		hboxRecherche.prefWidthProperty().bind(anchorTableView.widthProperty());

		// Construction du footer (boutons en dessous de la tableView)
		HBox hboxFooter = new HBox(10);
		hboxFooter.setPadding(new Insets(20, 10, 10, 10));
		rootInter.setBottom(hboxFooter);

		Button ajouterBtn = new Button("Ajouter");
		ajouterBtn.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		ajouterBtn.setPrefHeight(50);
		ajouterBtn.setPrefWidth(200);
		Image imageAjouter;
		try {
			imageAjouter = new Image(new FileInputStream("src/image/add-user.png"));
			ImageView imageViewAjouter = new ImageView(imageAjouter);
			imageViewAjouter.setPreserveRatio(true);
			imageViewAjouter.setFitHeight(ajouterBtn.getPrefHeight() - 10);
			ajouterBtn.setGraphic(imageViewAjouter);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Button supprimerBtn = new Button("Supprimer");
		supprimerBtn.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		;
		supprimerBtn.setPrefWidth(200);
		supprimerBtn.setPrefHeight(50);// fixe la taille du bouton
		Image imageSuprimer;
		try {
			imageSuprimer = new Image(new FileInputStream("src/image/eraser.png"));
			ImageView imageViewSupprimer = new ImageView(imageSuprimer);
			imageViewSupprimer.setPreserveRatio(true);
			imageViewSupprimer.setFitHeight(supprimerBtn.getPrefHeight() - 10);
			supprimerBtn.setGraphic(imageViewSupprimer);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Button modifierBtn = new Button("Modifier");
		modifierBtn.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		modifierBtn.setPrefWidth(200);
		modifierBtn.setPrefHeight(50);
		Image imageModifier;
		try {
			imageModifier = new Image(new FileInputStream("src/image/pen.png"));
			ImageView imageViewModifier = new ImageView(imageModifier);
			imageViewModifier.setPreserveRatio(true);
			imageViewModifier.setFitHeight(supprimerBtn.getPrefHeight() - 10);
			modifierBtn.setGraphic(imageViewModifier);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Button imprimerBtn = new Button("Imprimer");
		imprimerBtn.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		imprimerBtn.setPrefWidth(200);
		imprimerBtn.setPrefHeight(50);
		Image imageImprimer;
		try {
			imageImprimer = new Image(new FileInputStream("src/image/pdf.png"));
			ImageView imageViewImprimer = new ImageView(imageImprimer);
			imageViewImprimer.setPreserveRatio(true);
			imageViewImprimer.setFitHeight(supprimerBtn.getPrefHeight() - 10);
			imprimerBtn.setGraphic(imageViewImprimer);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Création d'une forme personnalisée pour les boutons d'édition du footer
		Rectangle rectBtnFooter = new Rectangle(30, 30);
		rectBtnFooter.setArcWidth(3);
		rectBtnFooter.setArcHeight(3);
		BackgroundFill bgFillBtnFooter = new BackgroundFill(Color.WHITE, new CornerRadii(15), new Insets(0));
		Background bgBtnFooter = new Background(bgFillBtnFooter);
		ajouterBtn.setBackground(bgBtnFooter);
		supprimerBtn.setBackground(bgBtnFooter);
		modifierBtn.setBackground(bgBtnFooter);
		imprimerBtn.setBackground(bgBtnFooter);

		// Remplissage du footer
		hboxFooter.getChildren().addAll(ajouterBtn, supprimerBtn, modifierBtn, imprimerBtn);
		hboxFooter.setAlignment(Pos.CENTER);

		// Construction du header
		// La Hbox "header" contient deux Hbox, l'une avec le logo ISIKA et l'autre avec
		// les boutons "Connexion", "importer" et "Aide/info"
		HBox header = new HBox();

		// Hbox avec les boutons "Connexion", "importer" et "Aide/info"
		HBox hboxSubHeaderBtn = new HBox();

		Button aideBtn = new Button(" Aide");
		aideBtn.setFont(Font.font("Arial", FontWeight.BOLD, 10));
		aideBtn.setPrefWidth(80);
		aideBtn.setPrefHeight(35);
		Image imageAide;
		try {
			imageAide = new Image(new FileInputStream("src/image/bouton-web-daide.png"));
			ImageView imageViewAide = new ImageView(imageAide);
			imageViewAide.setPreserveRatio(true);
			imageViewAide.setFitHeight(aideBtn.getPrefHeight() - 10);
			aideBtn.setGraphic(imageViewAide);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Button importerFichierBtn = new Button("Importer");
		importerFichierBtn.setFont(Font.font("Arial", FontWeight.BOLD, 10));
		importerFichierBtn.setPrefWidth(90);
		importerFichierBtn.setPrefHeight(35);
		Image imageImportation;
		try {
			imageImportation = new Image(new FileInputStream("src/image/importer.png"));
			ImageView imageViewImportation = new ImageView(imageImportation);
			imageViewImportation.setPreserveRatio(true);
			imageViewImportation.setFitHeight(aideBtn.getPrefHeight() - 10);
			importerFichierBtn.setGraphic(imageViewImportation);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Button idBtn = new Button("Connexion");
		idBtn.setFont(Font.font("Arial", FontWeight.BOLD, 10));
		idBtn.setPrefWidth(100);
		idBtn.setPrefHeight(35);
		Image imageId;
		try {
			imageId = new Image(new FileInputStream("src/image/connexion.png"));
			ImageView imageViewId = new ImageView(imageId);
			imageViewId.setPreserveRatio(true);
			imageViewId.setFitHeight(aideBtn.getPrefHeight() - 10);
			idBtn.setGraphic(imageViewId);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Création d'une forme personnalisée pour les boutons du header
		Rectangle rectBtnHeader = new Rectangle(10, 10);
		rectBtnHeader.setArcWidth(3);
		rectBtnHeader.setArcHeight(3);
		BackgroundFill bgFillBtnHeader = new BackgroundFill(Color.TRANSPARENT, new CornerRadii(15), new Insets(0));
		Background bgBtnHeader = new Background(bgFillBtnHeader);
		aideBtn.setBackground(bgBtnHeader);
		importerFichierBtn.setBackground(bgBtnHeader);
		idBtn.setBackground(bgBtnHeader);

		// Remplissage du header
		hboxSubHeaderBtn.getChildren().addAll(idBtn, importerFichierBtn, aideBtn);
		hboxSubHeaderBtn.setAlignment(Pos.CENTER_RIGHT);
		hboxSubHeaderBtn.setPadding(new Insets(0, 0, 0, 0));
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
			double buttonFontSize = aideBtn.getFont().getSize();
			imageViewLogoIsika.setFitHeight(buttonFontSize + 30);
			// ajout du logo à la hbox
			hboxImage.getChildren().add(imageViewLogoIsika);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Remplissage du header
		header.getChildren().addAll(hboxImage, hboxSubHeaderBtn);
		// ajuste la taille de la Hbox à la largeur de la bordePane
		rootInter.widthProperty().addListener((obs, oldVal, newVal) -> {
			header.setPrefWidth(newVal.doubleValue());
		});

		// Style et insertion des éléments dans les borderPane

		this.setStyle("-fx-background-color: papayawhip; -fx-font-family: Arial");
		this.setPadding(new Insets(10, 10, 10, 10));
		this.setCenter(rootInter);
		this.setTop(header);
		rootInter.setStyle("-fx-background-color: #454343; -fx-font-family: Arial;-fx-background-radius: 10px");
		rootInter.setCenter(anchorTableView);
		rootInter.setBottom(hboxFooter);
		rootInter.setTop(hboxRecherche);

//On remplit la tableview avec l'initialisation

		// Effacer tous les éléments actuels de la table de visualisation
		stagiaireTableView.getItems().clear();
		// Créer une nouvelle ObservableList à partir de la ListeStagiaire
		ObservableList<Stagiaire> stagiairesFichier = FXCollections.observableArrayList(liste.getListeStagiaire());
		// Ajouter les stagiaires à la table de visualisation
		stagiaireTableView.getItems().addAll(stagiairesFichier);
		// Mettre à jour la table de visualisation
		stagiaireTableView.refresh();

// Administrateur ou non ?		

		// Accés aux boutons supprimer et modifier selon admin ou non
		this.admin = admin;
		if (admin == false) {
			supprimerBtn.setDisable(true);
			modifierBtn.setDisable(true);
		}
		if (admin == true) {
			supprimerBtn.setDisable(false);
			modifierBtn.setDisable(false);
		}

// Action des boutons

		// Action sur le bouton Importer
		importerFichierBtn.setOnAction(event -> {
			// Créer une boîte de dialogue de sélection de fichier
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Sélectionnez le fichier des stagiaires");
			fileChooser.getExtensionFilters().addAll();

			// Ouvrir la boîte de dialogue de sélection de fichier et récupérer le fichier
			// sélectionné
			File selectedFile = fileChooser.showOpenDialog(this.getScene().getWindow());

			// Si un fichier a été sélectionné
			if (selectedFile != null) {
				// Effacer tous les éléments actuels de la table de visualisation
				stagiaireTableView.getItems().clear();
				// Créer un nouvel objet ListeStagiaire
				stagiairesImportes = new ListeStagiaire();
				// Vider l'arbre binaire
				arbreBinaire.setRacine(null);
				// Charger la liste des stagiaires à partir du fichier sélectionné
				liste = liste.chargerStagiairesDepuisFichier(selectedFile.getAbsolutePath());
				// Ajouter chaque stagiaire de la liste à l'arbre binaire
				for (Stagiaire stagiaire : liste.getListeStagiaire()) {
					arbreBinaire.ajoutStagiaire(stagiaire);
				}
				// Ajouter tous les stagiaires de l'arbre binaire à la ListeStagiaire
				arbreBinaire.affichageInfixe(stagiairesImportes);
				// Créer une nouvelle ObservableList à partir de la ListeStagiaire
				ObservableList<Stagiaire> stagiaires = FXCollections
						.observableArrayList(stagiairesImportes.getListeStagiaire());
				// Ajouter les stagiaires à la table de visualisation
				stagiaireTableView.getItems().addAll(stagiaires);
				// Mettre à jour la table de visualisation
				stagiaireTableView.refresh();
			}
		});

		aideBtn.setOnAction(e -> {

			Desktop desktop = Desktop.getDesktop();
			File pdfFile = new File("src/pdf/annuaire.pdf");
			try {
				desktop.open(pdfFile);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});

		// Action du bouton "Authentification"
		idBtn.setOnAction(event -> {
			// Créer une nouvelle instance de la fenêtre d'authentification
			FenetreAuthentification fenetreAuthentification = new FenetreAuthentification(liste, arbreBinaire);
			sceneAuthentification = new Scene (fenetreAuthentification, 450, 600);
			// Remplacer la scène actuelle par la scène d'authentification initiale
			Stage stage = App.getStage();
			stage.setScene(sceneAuthentification);
		});

		// Action sur le bouton Rechercher (à cotés de la barre de recherche)
		rechercheSimpleBtn.setOnAction(event -> {
			stagiaireTableView.getItems().clear();
			String attribut = attributsComboBox.getValue();
			String valeur = rechercheTextField.getText().isEmpty() ? null : rechercheTextField.getText();
			// Rechercher les stagiaires correspondant aux critères de recherche dans
			// l'arbre binaire
			stagiaireTrouveSimple = arbreBinaire.rechercher(attribut, valeur);
			stagiaireTrouveSimple.afficherStagiaire();
			ObservableList<Stagiaire> stagiaires = FXCollections
					.observableArrayList(stagiaireTrouveSimple.getListeStagiaire());
			stagiaireTableView.getItems().addAll(stagiaires);
			stagiaireTableView.refresh();
		});

		// Action sur le bouton Actualiser
		updateBtn.setOnAction(event -> {
			stagiaireTableView.getItems().clear();
			stagiaireActualiser = new ListeStagiaire();
			// Mise à jour de la liste
			arbreBinaire.affichageInfixe(stagiaireActualiser);
			ObservableList<Stagiaire> stagiaires = FXCollections
					.observableArrayList(stagiaireActualiser.getListeStagiaire());
			stagiaireTableView.getItems().addAll(stagiaires);
			stagiaireTableView.refresh();
		});

		// Action sur le bouton Supprimer
		supprimerBtn.setOnAction(event -> {
			// Récupération du stagiaire à supprimer en cliquant dessus dans la tableView
			Stagiaire stagiaireSelectionne = stagiaireTableView.getSelectionModel().getSelectedItem();

			// Message d'alerte si aucun stagiaire n'est selectionné
			if (stagiaireSelectionne == null) {
				Alert alertSelec = new Alert(Alert.AlertType.WARNING);
				DialogPane dialogPaneSelec = alertSelec.getDialogPane();
				dialogPaneSelec.setStyle("-fx-font-family: Arial");
				alertSelec.setTitle("Aucun stagiaire sélectionné");
				alertSelec.setHeaderText("Aucun stagiaire sélectionné");
				alertSelec.setContentText("Veuillez sélectionner un stagiaire à supprimer.");
				alertSelec.showAndWait();
				return;
			}

			// Afficher une boîte de dialogue de confirmation
			Alert alertSupp = new Alert(Alert.AlertType.CONFIRMATION);
			alertSupp.setTitle("Confirmation de suppression");
			alertSupp.setHeaderText("Êtes-vous sûr de vouloir supprimer ce stagiaire ?");
			alertSupp.setContentText(stagiaireSelectionne.toString());
			DialogPane dialogPaneSupp = alertSupp.getDialogPane();
			dialogPaneSupp.setStyle("-fx-font-family: Arial; -fx-font-size: 11px; fx-font-weight:bold");

			Optional<ButtonType> result = alertSupp.showAndWait();
			if (result.get() == ButtonType.OK) {
				// Supprimer le stagiaire
				stagiaireTableView.getItems().clear();
				stagiaireApresSupression = new ListeStagiaire();
				arbreBinaire.supprimerStagiaire(stagiaireSelectionne);
				// Mise à jour des listes et tableView à partir du nouvel arbre
				arbreBinaire.initialiserArbreFichier();
				arbreBinaire.affichageInfixe(stagiaireApresSupression);
				ObservableList<Stagiaire> stagiaires = FXCollections
						.observableArrayList(stagiaireApresSupression.getListeStagiaire());
				stagiaireTableView.getItems().addAll(stagiaires);
				stagiaireTableView.refresh();
			} else {
				// Ne rien faire, le stagiaire ne sera pas supprimé
			}
		});

		// Action sur le bouton Imprimer
		
		imprimerBtn.setOnAction(event -> {
			try {
				// Créer un fileChooser pour sélectionner le dossier de destination
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Enregistrer le fichier PDF");
				fileChooser.getExtensionFilters().add(new ExtensionFilter("PDF Files", "*.pdf"));
				File selectedFile = fileChooser.showSaveDialog(this.getScene().getWindow());
				if (selectedFile != null) {
					Document document = new Document();
					PdfWriter writter = PdfWriter.getInstance(document, new FileOutputStream(selectedFile));
					document.open();
					document.add(new Chunk(""));
					PdfPTable table = new PdfPTable(5);
					// Ajouter les en-têtes de colonne à la table
					PdfPCell cellNom = new PdfPCell(new Phrase("Nom"));
					table.addCell(cellNom);
					PdfPCell cellPrenom = new PdfPCell(new Phrase("Prénom"));
					table.addCell(cellPrenom);
					PdfPCell cellDepartement = new PdfPCell(new Phrase("Département"));
					table.addCell(cellDepartement);
					PdfPCell cellFormation = new PdfPCell(new Phrase("Formation"));
					table.addCell(cellFormation);
					PdfPCell cellAnnee = new PdfPCell(new Phrase("Année"));
					table.addCell(cellAnnee);
					// Ajouter les lignes de données à la table
					ListeStagiaire testListe = new ListeStagiaire();
					ArbreBinaireRecherche abr = new ArbreBinaireRecherche();
					File fichierBinaire = new File("src/fichiers/stagiaires.bin");
					if (fichierBinaire.isFile()) {
						abr.initialiserArbreFichier();
						testListe = abr.affichageInfixe(testListe);
					}
					testListe.afficherStagiaire();
					for (Stagiaire stagiaire : testListe.getListeStagiaire()) {
						PdfPCell cell = new PdfPCell(new Phrase(stagiaire.getNom()));
						table.addCell(cell);
						PdfPCell cell2 = new PdfPCell(new Phrase(stagiaire.getPrenom()));
						table.addCell(cell2);
						PdfPCell cell3 = new PdfPCell(new Phrase(stagiaire.getDepartement()));
						table.addCell(cell3);
						PdfPCell cell4 = new PdfPCell(new Phrase(stagiaire.getFormation()));
						table.addCell(cell4);
						PdfPCell cell5 = new PdfPCell(new Phrase(stagiaire.getAnnee()));
						table.addCell(cell5);
					}
					// Ajouter la table au document PDF
					document.add(table);
					document.close();
				}
			} catch (Exception e) {

				e.printStackTrace();
			}
		});

// Transition vers les fenêtre avec formulaire (Ajout, Recherche Avancée, Modifier)

		// Envoi vers le formulaire Recherche avancée
		fenetreRechercheAvancee = new FenetreRechercheAvancee(arbreBinaire, stagiairesImportes, this);
		sceneRechercheAvancee = new Scene(fenetreRechercheAvancee, 600, 600);
		rechercheAvanceBtn.setOnAction(event -> {
			// Aller vers le formulaire
			App.getStage().setScene(sceneRechercheAvancee);
		});

		// Envoi vers le formulaire Ajouter
		fenetreAjout = new FenetreAjout(arbreBinaire, stagiairesImportes, this);
		sceneAjout = new Scene(fenetreAjout, 600, 600);
		ajouterBtn.setOnAction(event -> {
			// Aller vers le formulaire
			App.getStage().setScene(sceneAjout);
		});

		// Envoi vers le formulaire Modifier
		fenetreModification = new FenetreModification(arbreBinaire, stagiairesImportes, this, stagiaireOriginal);
		sceneModification = new Scene(fenetreModification, 600, 600);
		modifierBtn.setOnAction(event -> {
			// Recupérer le stagiaire à modifier en cliquant dessus dans la tableview
			stagiaireOriginal = stagiaireTableView.getSelectionModel().getSelectedItem();
			// Message d'alerte si aucun stagiaire n'est selectionné
			if (stagiaireOriginal == null) {
				Alert alertSelecMod = new Alert(Alert.AlertType.WARNING);
				DialogPane dialogPaneSelec = alertSelecMod.getDialogPane();
				dialogPaneSelec.setStyle("-fx-font-family: Arial");
				alertSelecMod.setTitle("Aucun stagiaire sélectionné");
				alertSelecMod.setHeaderText("Aucun stagiaire sélectionné");
				alertSelecMod.setContentText("Veuillez sélectionner un stagiaire à modifier.");
				alertSelecMod.showAndWait();
				return;
			}
			// Pré-remplir les champ de texte de la fenêtre FenetreModification
			fenetreModification.getNomTextField().setText(stagiaireOriginal.getNom());
			fenetreModification.getPrenomTextField().setText(stagiaireOriginal.getPrenom());
			fenetreModification.getDepartementTextField().setText(stagiaireOriginal.getDepartement());
			fenetreModification.getFormationTextField().setText(stagiaireOriginal.getFormation());
			fenetreModification.getAnneeTextField().setText(stagiaireOriginal.getAnnee());
			// Aller vers le formulaire
			App.getStage().setScene(sceneModification);
		});

	}
	
    /**
     * Obtient l'objet StagiaireTableView.
     * @return L'objet StagiaireTableView.
     */
    public StagiaireTableView getStagiaireTableView() {
        return stagiaireTableView;
    }
    
    /**
     * Définit l'objet StagiaireTableView.
     * @param stagiaireTableView L'objet StagiaireTableView à définir.
     */
    public void setStagiaireTableView(StagiaireTableView stagiaireTableView) {
        this.stagiaireTableView = stagiaireTableView;
    }
    
    /**
     * Obtient le statut d'administrateur.
     * @return Le statut d'administrateur.
     */
    public Boolean getAdmin() {
        return admin;
    }
    
    /**
     * Définit le statut d'administrateur.
     * @param admin Le statut d'administrateur à définir.
     */
    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
    
    /**
     * Obtient l'arbre binaire de recherche.
     * @return L'arbre binaire de recherche.
     */
    public ArbreBinaireRecherche getArbreBinaire() {
        return arbreBinaire;
    }
    
    /**
     * Définit l'arbre binaire de recherche.
     * @param arbreBinaire L'arbre binaire de recherche à définir.
     */
    public void setArbreBinaire(ArbreBinaireRecherche arbreBinaire) {
        this.arbreBinaire = arbreBinaire;
    }
    
    /**
     * Obtient la liste de stagiaires importés.
     * @return La liste de stagiaires importés.
     */
    public ListeStagiaire getStagiairesImportes() {
        return stagiairesImportes;
    }
    
    /**
     * Obtient la liste de stagiaires importés.
     * @return La liste de stagiaires importés.
     */
    public ListeStagiaire getListeStagiairesImportes() {
        return stagiairesImportes;
    }
    
    /**
     * Obtient l'objet Stagiaire d'origine.
     * @return L'objet Stagiaire d'origine.
     */
    public Stagiaire getStagiaireOriginal() {
        return stagiaireOriginal;
    }
    
    /**
     * Définit l'objet Stagiaire d'origine.
     * @param stagiaireOriginal L'objet Stagiaire d'origine à définir.
     */
    public void setStagiaireOriginal(Stagiaire stagiaireOriginal) {
        this.stagiaireOriginal = stagiaireOriginal;
    }

}