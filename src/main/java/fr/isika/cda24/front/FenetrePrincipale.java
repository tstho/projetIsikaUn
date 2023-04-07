package fr.isika.cda24.front;

import java.io.File;
import java.io.FileOutputStream;

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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

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
	private Stagiaire stagiaireOriginal;

	private static Scene sceneRechercheAvancee;
	private static Scene sceneAjout;
	private static Scene sceneModification;

	private Boolean admin = false;

	public FenetrePrincipale(Boolean admin, ListeStagiaire listeRecu, ArbreBinaireRecherche abrRecu) {
		super();
		arbreBinaire = new ArbreBinaireRecherche();
		stagiaireTableView = new StagiaireTableView();
		liste = new ListeStagiaire();
		
		liste = listeRecu;
		arbreBinaire = abrRecu;

// Création des éléments graphique

		this.setPadding(new Insets(10, 10, 10, 10));

		BorderPane rootInter = new BorderPane();
		rootInter.setPadding(new Insets(20, 20, 20, 20));

		// Création / remplissage de la HBox permettant le recherche (située TOP
		// rootInter)
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

		// Création / remplissage de la HBox avec les boutons d'édition (située BOTTOM
		// rootInter)
		HBox hboxFooter = new HBox();
		hboxFooter.setPadding(new Insets(10, 10, 10, 10));
		rootInter.setBottom(hboxFooter);

		Button ajouterBtn = new Button("Ajouter");
		Button supprimerBtn = new Button("Supprimer");
		Button modifierBtn = new Button("Modifier");
		Button imprimerBtn = new Button("Imprimer");
		hboxFooter.getChildren().addAll(ajouterBtn, supprimerBtn, modifierBtn, imprimerBtn);
		hboxFooter.setAlignment(Pos.CENTER);
		
		// Création / remplissage de la HBox avec les boutons d'authification,
		// actualisation etc.(située TOP root : Hhader)
		HBox hboxTopRoot = new HBox();

		Button importerFichierBtn = new Button("Importer");
		Button idBtn = new Button("Authentification");
		Button actualiserBtn = new Button("Actualiser");
		Button aideBtn = new Button("Aide");

		hboxTopRoot.getChildren().addAll(importerFichierBtn, idBtn, actualiserBtn, aideBtn);
		hboxTopRoot.setAlignment(Pos.CENTER_RIGHT);
		hboxTopRoot.setPadding(new Insets(0, 0, 10, 0));
		
		//On remplit la tableview avec l'initialisation
		// Effacer tous les éléments actuels de la table de visualisation
		stagiaireTableView.getItems().clear();
		// Créer une nouvelle ObservableList à partir de la ListeStagiaire
		ObservableList<Stagiaire> stagiairesFichier = FXCollections
				.observableArrayList(liste.getListeStagiaire());
		// Ajouter les stagiaires à la table de visualisation
		stagiaireTableView.getItems().addAll(stagiairesFichier);
		// Mettre à jour la table de visualisation
		stagiaireTableView.refresh();
		
// Administrateur ou non ?		
		
		// Accés aux boutons supprimer et modifier
		this.admin = admin;
		System.out.println(admin);
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

		// Action sur le bouton Supprimer
		supprimerBtn.setOnAction(event -> {
			// Recupération du stagiaire à supprimer en clicuant dessus dans la tableView
			Stagiaire stagiaireSelectionne = stagiaireTableView.getSelectionModel().getSelectedItem();
			stagiaireTableView.getItems().clear();
			stagiaireApresSupression = new ListeStagiaire();
			// Effectuer la suppression 
			arbreBinaire.supprimerStagiaire(stagiaireSelectionne);
			//Mise à jour des liste et tableView à partir du nouvel arbre
			arbreBinaire.initialiserArbreFichier();
			arbreBinaire.affichageInfixe(stagiaireApresSupression);
			ObservableList<Stagiaire> stagiaires = FXCollections
					.observableArrayList(stagiaireApresSupression.getListeStagiaire());
			stagiaireTableView.getItems().addAll(stagiaires);
			stagiaireTableView.refresh();
		});

		// Action sur le bouton Actualiser
		actualiserBtn.setOnAction(event -> {
			stagiaireTableView.getItems().clear();
			stagiaireActualiser = new ListeStagiaire();
			// Mise à jour de la liste
			arbreBinaire.affichageInfixe(stagiaireActualiser);
			ObservableList<Stagiaire> stagiaires = FXCollections
					.observableArrayList(stagiaireActualiser.getListeStagiaire());
			stagiaireTableView.getItems().addAll(stagiaires);
			stagiaireTableView.refresh();
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
					System.out.println("Le fichier PDF a été généré avec succès !");
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
			// Pré-remplir les champ de texte de la fenêtre FenetreModification
			fenetreModification.getNomTextField().setText(stagiaireOriginal.getNom());
			fenetreModification.getPrenomTextField().setText(stagiaireOriginal.getPrenom());
			fenetreModification.getDepartementTextField().setText(stagiaireOriginal.getDepartement());
			fenetreModification.getFormationTextField().setText(stagiaireOriginal.getFormation());
			fenetreModification.getAnneeTextField().setText(stagiaireOriginal.getAnnee());
			// Aller vers le formulaire
			App.getStage().setScene(sceneModification);
		});
		
		

// Style et insertion des élément dans les borderPane

		this.setStyle("-fx-background-color: blanchedalmond; -fx-font-family: Arial");
		this.setCenter(rootInter);
		this.setTop(hboxTopRoot);
		rootInter.setStyle("-fx-background-color: grey; -fx-font-family: Arial;-fx-background-radius: 10px");
		rootInter.setCenter(stagiaireTableView);
		rootInter.setBottom(hboxFooter);
		rootInter.setTop(hboxRecherche);

	}

	public StagiaireTableView getStagiaireTableView() {
		return stagiaireTableView;
	}

	public void setStagiaireTableView(StagiaireTableView stagiaireTableView) {
		this.stagiaireTableView = stagiaireTableView;
	}

	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	public ArbreBinaireRecherche getArbreBinaire() {
		return arbreBinaire;
	}

	public void setArbreBinaire(ArbreBinaireRecherche arbreBinaire) {
		this.arbreBinaire = arbreBinaire;
	}

	public ListeStagiaire getStagiairesImportes() {
		return stagiairesImportes;
	}

	public ListeStagiaire getListeStagiairesImportes() {
		return stagiairesImportes;
	}

	public Stagiaire getStagiaireOriginal() {
		return stagiaireOriginal;
	}

	public void setStagiaireOriginal(Stagiaire stagiaireOriginal) {
		this.stagiaireOriginal = stagiaireOriginal;
	}

}



//private TextField rechercheTextField;
//private Button rechercheSimpleBtn;
//private Button rechercheAvanceBtn;
//private Button ajouterBtn;
//private Button supprimerBtn;
//private Button modifierBtn;
//private Button exportBtn;
//private Button imprimerBtn;
//private Button importerFichierBtn;
//private Button idBtn;
//private Button actualiserBtn;
//private Button homeBtn;
