package fr.isika.cda24.projetIsikaUn;

import java.util.ArrayList;
import java.util.List;

import fr.isika.cda24.model.ListeStagiaire;
import fr.isika.cda24.model.Stagiaire;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class GridPaneTableauStagiaires extends GridPane {

	public GridPaneTableauStagiaires (ListeStagiaire liste) {
		super();
		
		TableView<Stagiaire> table = new TableView<Stagiaire>();
		table.setEditable(true);
		
		TableColumn<Stagiaire,String> nomCol = new TableColumn<Stagiaire,String>("Nom");
		nomCol.setMinWidth(100);
		nomCol.setCellValueFactory(
				new PropertyValueFactory<Stagiaire,String>("nom"));
		
		TableColumn<Stagiaire,String> prenomCol = new TableColumn<Stagiaire,String>("Prenom");
		prenomCol.setMinWidth(100);
		prenomCol.setCellValueFactory(
				new PropertyValueFactory<Stagiaire,String>("prenom"));
		
		
		TableColumn<Stagiaire,String> departementCol = new TableColumn<Stagiaire,String>("Departement");
		departementCol.setMinWidth(100);
		departementCol.setCellValueFactory(
				new PropertyValueFactory<Stagiaire,String>("departement"));
		
		TableColumn<Stagiaire,String> formationCol = new TableColumn<Stagiaire,String>("Formation");
		formationCol.setMinWidth(100);
		formationCol.setCellValueFactory(
				new PropertyValueFactory<Stagiaire,String>("formation"));
		
		
		TableColumn<Stagiaire,String> anneeCol = new TableColumn<Stagiaire,String>("Annee");
		anneeCol.setMinWidth(100);
		anneeCol.setCellValueFactory(
				new PropertyValueFactory<Stagiaire,String>("annee"));
		
		
		table.getColumns().addAll(nomCol, prenomCol, departementCol, formationCol, anneeCol);
		 //On rempli la table avec la liste observable
       table.setItems(FXCollections.observableArrayList(liste.getListeStagiaire()));
      
       this.add(table, 800, 500);
}
}