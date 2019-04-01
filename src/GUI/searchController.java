package GUI;

import Enums.Aktorzy;
import Produkcje.Produkcja;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class searchController {
    @FXML
    private TextField nameSearch;
    @FXML
    private RadioButton name;
    @FXML
    private RadioButton actor;
    @FXML
    private ListView<Aktorzy> actors;
    @FXML
    private ListView<Produkcja> searched;
    @FXML
    private GridPane gridPane;

@FXML
public void initialize(){
    nameSearch.setDisable(true);
    searched.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if(event.getClickCount() == 2){
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.initOwner(gridPane.getScene().getWindow());
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("GUI/productionDetails.fxml"));
                try {
                    dialog.getDialogPane().setContent(fxmlLoader.load());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                ProductionController controller = fxmlLoader.getController();
                Produkcja produkcja = searched.getSelectionModel().getSelectedItem();
                controller.setTextFields(produkcja);
                Optional<ButtonType> result = dialog.showAndWait();
                if(result.isPresent()) dialog.close();
            }
        }
    });
}


    public void searchPrep(){
        if(name.selectedProperty().get() == true){
            nameSearch.setDisable(false);
        }
        else if(actor.selectedProperty().get() == true){
            nameSearch.setDisable(true);
            Label label = new Label("Proszę wybrać aktora z listy aktorów obok.");
            gridPane.add(label,1,1,2,1);
            Set<Aktorzy> set = new HashSet<>();
            if(Main.getFilmy().size() >0) {
                for (int i = 0; i < Main.getFilmy().size(); i++) {
                    set.addAll(Main.getFilmy().get(i).getObsada());
                }
                actors.getItems().addAll(set);
            }
        }

    }

    public void searchButtonClicked(){
        if(name.selectedProperty().get()==true){
            String nazwa = nameSearch.getText();
            for(int i = 0; i < Main.getFilmy().size(); i++){
                if(Main.getFilmy().get(i).getNazwa().contains(nazwa)) searched.getItems().add(Main.getFilmy().get(i));
            }
        }
        else if(actor.selectedProperty().get() == true){
            Aktorzy aktor = actors.getSelectionModel().getSelectedItem();
            for(int i = 0; i < Main.getFilmy().size();i++){
                if(Main.getFilmy().get(i).getObsada().contains(aktor)) searched.getItems().add(Main.getFilmy().get(i));
            }
        }
    }

}
