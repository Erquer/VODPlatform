package GUI;

import Enums.Aktorzy;
import Enums.Kraje;
import Produkcje.Film;
import Produkcje.Produkcja;
import Produkcje.Serial;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.time.LocalDate;

public class ProductionController {

    @FXML
    private TextField tytul;
    @FXML
    private TextArea opis;
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
    @FXML
    private Label link;
    @FXML
    private ImageView obraz;
    @FXML
    private LineChart<String,Number> wykres;


    @FXML
    public void initialize(){

    }



    public void setTextFields(Produkcja produkcja){

        StringBuilder sb = new StringBuilder();
        tytul.textProperty().setValue(produkcja.getNazwa());
        opis.textProperty().setValue(produkcja.getOpis());
        ocena.setText(Double.toString(produkcja.getOcena()));
        cena.setText(Integer.toString(produkcja.getCena()));
        obraz.setImage(produkcja.getObrazek());
        for(Aktorzy aktor: produkcja.getObsada()){
            aktorzy.getItems().add(aktor.toString());
        }
        for(Kraje kraj: produkcja.geteKraje()){
            kraje.getItems().add(kraj.toString());
        }
        dealer.setText(produkcja.getDealer().getID());
        data.setText(produkcja.getData().toString());
        if(produkcja instanceof Film){
            for(String link: ((Film) produkcja).getLinki()){
                linki.getItems().add(link);
            }
            tytul.setText(produkcja.getNazwa() + " Typ: " + ((Film) produkcja).getTyp());
        }else if(produkcja instanceof Serial){
            link.setText("Sezony");
            for(int i = 0; i < ((Serial) produkcja).getSezons().size();i++){
                sb.append("Sezon: " + i + " ma: " + ((Serial) produkcja).getOdcinkiSezonu(i).size() + " odcinków.");
                linki.getItems().add(sb.toString());
                sb.delete(0,sb.length());
            }

        }
        else {
            linki.getItems().add("Brak linków, to nie Film.");
        }
        if(produkcja.getOgladalnosc() != null) {

            CategoryAxis xAxis = new CategoryAxis();
            NumberAxis yAxis = new NumberAxis();

            LineChart<String, Number> chart = new LineChart<>(xAxis,yAxis);
            ObservableList<XYChart.Data<String, Number>> data = FXCollections.observableArrayList();

            for (LocalDate dats : produkcja.getOgladalnosc().keySet()) {
                data.add(new XYChart.Data<>(dats.toString(),produkcja.getOgladalnosc().get(dats)));
            }
            SortedList sortedData = new SortedList<>(data);

            chart.getData().addAll(new XYChart.Series<String, Number>(sortedData));

            wykres.getData().addAll(chart.getData());
        }
        else{
            wykres.getData().add(null);
        }

    }

}
