package fr.isika.cda24.front;

import java.util.ArrayList;

import javax.security.auth.callback.Callback;

import fr.isika.cda24.model.ListeStagiaire;
import fr.isika.cda24.model.Stagiaire;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

/**
 * Cette classe étend TableView et contient des colonnes pour afficher les
 * informations des objets Stagiaire.
 */
public class StagiaireTableView extends TableView<Stagiaire> {
	/**
	 * Constructeur de la classe StagiaireTableView.
	 */
	public StagiaireTableView() {
		super();

		this.setStyle("-fx-background-color: transparent; -fx-table-cell-border-color: whitesmoke;");
		// Définir les styles pour les en-têtes de colonnes par défaut
		System.setProperty("com.sun.javafx.scene.control.skin.ColumnHeaderBackgroundFill", "transparent");
		System.setProperty("com.sun.javafx.scene.control.skin.ColumnHeaderFontStyle",
				"-fx-text-fill: transparent; -fx-font-weight: bold;");

		this.setEditable(true);
		this.setPrefWidth(800);

		// Appliquer des styles en ligne à la TableView
		// this.lookup(".column-header").setStyle("-fx-background-color: #2c3e50;
		// -fx-text-fill: white; -fx-font-weight: bold; -fx-border-color: lightgrey;");
			
		
		// Créer des colonnes pour la TableView
		TableColumn<Stagiaire, String> nomCol = new TableColumn<>("Nom");
		nomCol.setMinWidth(50);
		nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
		nomCol.setStyle("-fx-font-size: 11px; -fx-font-family: Verdana;  -fx-text-fill: black;");

		TableColumn<Stagiaire, String> prenomCol = new TableColumn<>("Prénom");
		prenomCol.setMinWidth(50);
		prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
		prenomCol.setStyle("-fx-font-size: 11px; -fx-font-family: Verdana;  -fx-text-fill: black;");
		// -fx-background-color: azure;
		TableColumn<Stagiaire, String> departementCol = new TableColumn<>("Département");
		departementCol.setMinWidth(50);
		departementCol.setCellValueFactory(new PropertyValueFactory<>("departement"));
		departementCol.setStyle("-fx-font-size: 11px; -fx-font-family: Verdana;  -fx-text-fill: black;");

		TableColumn<Stagiaire, String> formationCol = new TableColumn<>("Formation");
		formationCol.setMinWidth(50);
		formationCol.setCellValueFactory(new PropertyValueFactory<>("formation"));
		formationCol.setStyle("-fx-font-size: 11px; -fx-font-family: Verdana;  -fx-text-fill: black;");

		TableColumn<Stagiaire, String> anneeCol = new TableColumn<>("Année");
		anneeCol.setMinWidth(50);
		anneeCol.setCellValueFactory(new PropertyValueFactory<>("annee"));
		anneeCol.setStyle("-fx-font-size: 11px; -fx-font-family: Verdana;  -fx-text-fill: black;");

		// Ajouter les colonnes à la TableView
		this.getColumns().addAll(nomCol, prenomCol, departementCol, formationCol, anneeCol);

		// Appliquer la politique de redimensionnement des colonnes
		this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		// Configurer les colonnes pour qu'elles occupent tout l'espace horizontal
		// disponible
		nomCol.setMaxWidth(1f * Integer.MAX_VALUE * 20); // 20% de largeur
		prenomCol.setMaxWidth(1f * Integer.MAX_VALUE * 20); // 20% de largeur
		departementCol.setMaxWidth(1f * Integer.MAX_VALUE * 20); // 20% de largeur
		formationCol.setMaxWidth(1f * Integer.MAX_VALUE * 20); // 20% de largeur
		anneeCol.setMaxWidth(1f * Integer.MAX_VALUE * 20); // 20% de largeur

	}

}
