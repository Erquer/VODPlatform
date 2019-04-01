package GUI;

import Produkcje.Produkcja;
import Threads.User;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class ClientController {
    @FXML
    private TextField id;
    @FXML
    private TextField data;
    @FXML
    private TextField email;
    @FXML
    private TextField card;
    @FXML
    private TextField abonament;
    @FXML
    private TextField terminAbo;
    @FXML
    private ListView<String> produkcje ;
    @FXML
    private ListView<LocalDate> terminy;


    public void setData(User user){
        id.setText(user.getID());
        data.setText(user.getBirth().toString());
        email.setText(user.getEmail());
        card.setText(user.getCardNumber());
        if(user.getAbonament() != null) {
            abonament.setText(user.getAbonament().name());
            terminAbo.setText(user.getTerminAbonamentu().toString());
        }
        else{
            abonament.setText("Brak abonamentu");
            terminAbo.setText("------");
        }
        if(user.getKupioneFilmy().size()>0)
            for(Produkcja produkcja: user.getKupioneFilmy()){
                produkcje.getItems().add(produkcja.getNazwa());
                terminy.getItems().add(user.getDatyKupionych().get(produkcja));
            }
    }

}
