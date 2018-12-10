package GUI;

import Threads.Dealer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;


public class DetailsController {
    @FXML
    private TextArea detailsArea;
    @FXML
    private Button actionButton;



    public void showData(Dealer dealer){
        detailsArea.setText(dealer.toStringForDetails());
    }

    public void buttonClicked(MouseEvent event){
        detailsArea.appendText("Button Clicked");
    }
}
