package GUI;

import Produkcje.Film;
import Produkcje.Live;
import Produkcje.Produkcja;
import Produkcje.Serial;
import Threads.Dealer;
import Threads.User;
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
    private ListView<Produkcja> produkcje;
    @FXML
    private HBox filmBox;
    @FXML
    private HBox serialBox;
    @FXML
    private HBox liveBox;

    public HBox getFilmBox() {
        return filmBox;
    }

    public HBox getSerialBox() {
        return serialBox;
    }

    public HBox getLiveBox() {
        return liveBox;
    }

   public void addProd(Produkcja produkcja){
        if(produkcja instanceof Film){
            filmBox.getChildren().add(new ImageView(produkcja.getObrazek()));
        }else if(produkcja instanceof Serial){
            serialBox.getChildren().add(new ImageView(produkcja.getObrazek()));
        }
        else if(produkcja instanceof Live){
            liveBox.getChildren().add(new ImageView(produkcja.getObrazek()));
        }
   }
    @FXML
    public void initialize(){
        listaDealer.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listaDealer.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Dealer>() {
            @Override
            public void changed(ObservableValue<? extends Dealer> observable, Dealer oldValue, Dealer newValue) {

                filmBox.getChildren().clear();
                serialBox.getChildren().clear();
                liveBox.getChildren().clear();



                if(Main.getFilmy().size() > 0) {
                    for (Produkcja produkcja : Main.getFilmy()) {
                        produkcje.getItems().add(produkcja);
                        if (produkcja instanceof Film) {
                            filmBox.getChildren().add(new ImageView(produkcja.getObrazek()));
                        } else if (produkcja instanceof Serial) {
                            serialBox.getChildren().add(new ImageView(produkcja.getObrazek()));
                        } else if (produkcja instanceof Live) {
                            liveBox.getChildren().add(new ImageView(produkcja.getObrazek()));
                        }
                    }
                }

           }
        });


        produkcje.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton().equals(MouseButton.PRIMARY)){
                    if(event.getClickCount() == 2){
                        Dialog<ButtonType> dialog = new Dialog<>();
                        dialog.initOwner(mainBorderPane.getScene().getWindow());
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("GUI/productionDetails.fxml"));
                        try {
                            dialog.getDialogPane().setContent(fxmlLoader.load());
                        }catch (IOException e){
                            e.printStackTrace();
                            return;
                        }
                        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                        ProductionController controller = fxmlLoader.getController();
                        controller.setTextFields(produkcje.getSelectionModel().getSelectedItem());
                        Optional<ButtonType> result = dialog.showAndWait();
                        if(result.isPresent() && result.get() == ButtonType.OK){
                            dialog.close();
                        }
                    }
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
                        fxmlLoader.setLocation(getClass().getResource("GUI/details.fxml"));
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
    public void showImage(MouseEvent event){

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
//        Threads.Dealer deal = listaDealer.getSelectionModel().getSelectedItem();
//        System.out.println("=============================================");
//        for(Produkcje.Produkcja produkcja:deal.getStworzoneProdukcje()){
//            System.out.println(produkcja);
//        }
//        System.out.println("=============================================");
//        if(films.size() == 0) films.addAll(deal.getStworzoneProdukcje());
//        daneProdukcji.setItems(films);



       // deal.stop();
    }
}
