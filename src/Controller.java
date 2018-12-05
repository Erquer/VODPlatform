import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.Optional;

public class Controller {
    @FXML
    private ImageView imageView;
    @FXML
    private ListView<Dealer> listaDealer;
    @FXML
    private ListView<Produkcja> daneProdukcji;
    @FXML
    private Button addDealerButton;
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private Button addUserButton;
    @FXML
    private ListView<User> listaUser;
    @FXML
    private HBox testHbox;


//    private ObservableList<Produkcja>  films = FXCollections.observableArrayList();
//    private ObservableList<Dealer> dealers = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        testHbox.scaleShapeProperty().setValue(true);
        testHbox.setSpacing(5);
        testHbox.getParent().autosize();

        listaDealer.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listaDealer.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Dealer>() {
            @Override
            public void changed(ObservableValue<? extends Dealer> observable, Dealer oldValue, Dealer newValue) {
//                daneProdukcji.getItems().clear();
//                daneProdukcji.refresh();
//                System.out.println("Zmieniono wybór dealera na: " + newValue );
//                Dealer dealer = listaDealer.getSelectionModel().getSelectedItem();
//                for(int i = 0; i < dealer.getStworzoneProdukcje().size();i++){
//                    daneProdukcji.getItems().add(dealer.getStworzoneProdukcje().get(i));
//                }
                testHbox.getChildren().removeAll();
                testHbox.getChildren().clear();
                Dealer dealer = listaDealer.getSelectionModel().getSelectedItem();
                for(int i = 0; i < dealer.getStworzoneProdukcje().size(); i++){
                    testHbox.getChildren().add(new ImageView(dealer.getStworzoneProdukcje().get(i).getObrazek()));
                }

            }
        });

        listaDealer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton().equals(MouseButton.PRIMARY)) {
                    if (event.getClickCount() == 2) {
                        Dialog<ButtonType> dialog = new Dialog<>();
                        dialog.initOwner(mainBorderPane.getScene().getWindow());
                        dialog.setTitle("Sczczegóły dealera");
                        dialog.setHeaderText("Tutaj wyświetlane są informacje nt. Dealera");
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("details.fxml"));
                        try {
                            dialog.getDialogPane().setContent(fxmlLoader.load());
                        } catch (IOException e) {
                            e.printStackTrace();
                            return;
                        }

                        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                        DetailsController controller = fxmlLoader.getController();
                        controller.showData(listaDealer.getSelectionModel().getSelectedItem());
                        Optional<ButtonType> result = dialog.showAndWait();

                        if(result.isPresent() && result.get() == ButtonType.OK){
                            dialog.close();
                        }

                    }
                }
            }
        });

    }



    @FXML
    public void showImage(){
    }

    @FXML
    public void setAddDealerButton(MouseEvent event){
        Dealer dealer = new Dealer();
        Main.addDealer(dealer);
        listaDealer.getItems().add(dealer);
    }
    @FXML
    public void setAddUserButton(MouseEvent event){
        User user = new User();
        Main.addUser(user);
        listaUser.getItems().add(user);

    }


    @FXML
    public void dealerList(){
       // if(daneProdukcji.getItems().size() > 0) daneProdukcji.getItems().removeAll();
//        daneProdukcji.refresh();
//        Dealer deal = listaDealer.getSelectionModel().getSelectedItem();
//        System.out.println("=============================================");
//        for(Produkcja produkcja:deal.getStworzoneProdukcje()){
//            System.out.println(produkcja);
//        }
//        System.out.println("=============================================");
//        if(films.size() == 0) films.addAll(deal.getStworzoneProdukcje());
//        daneProdukcji.setItems(films);



       // deal.stop();
    }
}
