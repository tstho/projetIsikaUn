package fr.isika.cda24.front;

import java.util.ArrayList;

import fr.isika.cda24.model.ListeStagiaire;
import fr.isika.cda24.model.Stagiaire;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class StagiaireTableView extends TableView<Stagiaire> {

	public StagiaireTableView() {
		super();

		// Création de la tableView qui contiendra la liste des éléments de l'arbre
		this.setEditable(true);
		this.setPrefWidth(800);
		// Créer des colonnes pour la TableView
		TableColumn<Stagiaire, String> nomCol = new TableColumn<>("Nom");
		nomCol.setMinWidth(100);
		nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
		TableColumn<Stagiaire, String> prenomCol = new TableColumn<>("Prénom");
		prenomCol.setMinWidth(100);
		prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
		TableColumn<Stagiaire, String> departementCol = new TableColumn<>("Département");
		departementCol.setMinWidth(100);
		departementCol.setCellValueFactory(new PropertyValueFactory<>("departement"));
		TableColumn<Stagiaire, String> formationCol = new TableColumn<>("Formation");
		formationCol.setMinWidth(100);
		formationCol.setCellValueFactory(new PropertyValueFactory<>("formation"));
		TableColumn<Stagiaire, String> anneeCol = new TableColumn<>("Année");
		anneeCol.setMinWidth(100);
		anneeCol.setCellValueFactory(new PropertyValueFactory<>("annee"));
		// Ajouter les colonnes à la TableView
		this.getColumns().addAll(nomCol, prenomCol, departementCol, formationCol, anneeCol);

	}
	
	
}
