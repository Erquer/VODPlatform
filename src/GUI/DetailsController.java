package GUI;

import Produkcje.Produkcja;
import Threads.Dealer;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class DetailsController {
@FXML
    private TextField id;
@FXML
    private TextField cena;
@FXML
    private ListView<Produkcja> produkcje;

public void showData(Dealer dealer){
    id.setText(dealer.getID());
    cena.setText(Integer.toString(dealer.getCenaUslug()));
    produkcje.getItems().addAll(dealer.getStworzoneProdukcje());
}
}
