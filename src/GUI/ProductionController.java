package GUI;

import Produkcje.Film;
import Produkcje.Produkcja;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ProductionController {

    @FXML
    private TextField tytul;
    @FXML
    private TextField opis;
    @FXML
    private TextField ocena;
    @FXML
    private TextField cena;
    @FXML
    private ListView<String> linki;
    @FXML
    private ListView<String> kraje;
    @FXML
    private TextField dealer;
    @FXML
    private TextField data;
    @FXML
    private ListView<String> aktorzy;



    public void setTextFields(Produkcja produkcja){

        tytul.textProperty().setValue(produkcja.getNazwa());
        opis.textProperty().setValue(produkcja.getOpis());
        ocena.setText(Double.toString(produkcja.getOcena()));
        cena.setText(Integer.toString(produkcja.getCena()));
        aktorzy.getItems().addAll(produkcja.getObsada().toString());
        kraje.getItems().addAll(produkcja.geteKraje().toString());
        dealer.setText(produkcja.getDealer().getID());
        data.setText(produkcja.getData().toString());
        if(produkcja instanceof Film){
            linki.getItems().addAll(((Film) produkcja).getLinki());
            tytul.setText(produkcja.getNazwa() + " Typ: " + ((Film) produkcja).getTyp());
        }


    }

}
