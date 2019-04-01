package GUI;

import Produkcje.*;
import Threads.Dealer;
import Threads.User;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Controller {
    @FXML
    private ListView<Dealer> listaDealer;
    @FXML
    private Button addDealerButton;
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private Button addUserButton;
    @FXML
    private ListView<User> listaUser;
    @FXML
    private HBox filmBox;
    @FXML
    private HBox serialBox;
    @FXML
    private HBox liveBox;

    @FXML
    private Label saldo;
    @FXML
    private Label data;

    //zmienne ImageView do wyświetlania bazy filmów
    private List<ImageView> Iserial = new ArrayList<>();
    @FXML
    private ImageView s1;
    @FXML
    private ImageView s2;
    @FXML
    private ImageView s3;
    @FXML
    private ImageView s4;
    @FXML
    private ImageView s5;

    private List<ImageView> Ifilm = new ArrayList<>();
    @FXML
    private ImageView f1;
    @FXML
    private ImageView f2;
    @FXML
    private ImageView f3;
    @FXML
    private ImageView f4;
    @FXML
    private ImageView f5;
    private List<ImageView> Ilive = new ArrayList<>();
    @FXML
    private ImageView l1;
    @FXML
    private ImageView l2;
    @FXML
    private ImageView l3;
    @FXML
    private ImageView l4;
    @FXML
    private ImageView l5;
    @FXML
    private CheckBox showAll;
    //pomocnicze listy potrzebne do wyświetlania produkcji.
    private List<Film> films = new ArrayList<>();
    private List<Serial> serials = new ArrayList<>();
    private List<Live> lives = new ArrayList<>();

    public List<Film> getFilms() {
        return films;
    }

    public List<Serial> getSerials() {
        return serials;
    }

    public List<Live> getLives() {
        return lives;
    }

    public void addProd(Produkcja produkcja){
       if(produkcja instanceof Film){
           films.add((Film)produkcja);
       }else if(produkcja instanceof Serial){
           serials.add((Serial)produkcja);
       }else if(produkcja instanceof Live){
           lives.add((Live)produkcja);
       }
   }

   @FXML
    public void setSaldoLabel(String saldo) {
        this.saldo.setText(saldo);
    }
    @FXML
    public void setDataLabel(LocalDate data) {
        this.data.setText(data.toString());
    }

    @FXML
    public void initialize(){
        listaDealer.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        //dodatnie do listy ImageView elementów.
        Ifilm.add(f1);Ifilm.add(f2);Ifilm.add(f3);Ifilm.add(f4);Ifilm.add(f5);
        Iserial.add(s1);Iserial.add(s2);Iserial.add(s3);Iserial.add(s4);Iserial.add(s5);
        Ilive.add(l1);Ilive.add(l2);Ilive.add(l3);Ilive.add(l4);Ilive.add(l5);

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
                        fxmlLoader.setLocation(getClass().getResource("/GUI/dealerDetails.fxml"));
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

        saldo.setText(Double.toString(Main.getSaldo()));
        data.setText(Main.getDate().toString());

        listaDealer.getItems().addListener(new ListChangeListener<Dealer>() {
            @Override
            public void onChanged(Change<? extends Dealer> c) {
                if(listaDealer.getItems().isEmpty()){
                    System.out.println(Main.getFilmy().size());
                }
            }
        });



    }


    @FXML
    public void deleteButtonAction(MouseEvent event){
        if(listaDealer.getSelectionModel().getSelectedItem() != null){
            Dealer dealer = listaDealer.getSelectionModel().getSelectedItem();
            films.removeAll(dealer.getStworzoneProdukcje());
            serials.removeAll(dealer.getStworzoneProdukcje());
            lives.removeAll(dealer.getStworzoneProdukcje());
            Main.deleteDealer(dealer);
            listaDealer.getItems().remove(dealer);
            listaDealer.getSelectionModel().select(null);
        }else if(listaUser.getSelectionModel().getSelectedItem() != null){
            listaUser.getSelectionModel().getSelectedItem().stop();
            listaUser.getItems().remove(listaUser.getSelectionModel().getSelectedItem());
            listaUser.getSelectionModel().select(null);
        }

    }

    @FXML
    public void setAddDealerButton(MouseEvent event){
        Dealer dealer = new Dealer();
        Main.addDealer(dealer);
        listaDealer.getItems().add(dealer);
    }
    public void loadDealers(List<Dealer> dealers){
        for(Dealer dealer: dealers){
            listaDealer.getItems().add(dealer);
        }
    }
    @FXML
    public void setAddUserButton(MouseEvent event){
        User user = new User();
        Main.addUser(user);
        listaUser.getItems().add(user);

    }

    @FXML
    public void refresh(){
       //Ustawienie filmów, pierwsze 5 na liście.
       if(films.size() > 5){
           for(int i = 0; i < 5; i++){
               Ifilm.get(i).setImage(films.get(i).getObrazek());
           }
       }else if(films.size() >0 && films.size() < 5){
           for(int i = 0; i< films.size(); i++){
               Ifilm.get(i).setImage(films.get(i).getObrazek());
           }
       }
       //ustawienie seriali, pierwsze 5.
        if(serials.size() > 5){
            for(int i = 0; i < 5; i++){
                Iserial.get(i).setImage(serials.get(i).getObrazek());
            }
        }else if(serials.size() >0 && serials.size() < 5){
            for(int i = 0; i< serials.size(); i++){
                Iserial.get(i).setImage(serials.get(i).getObrazek());
            }
        }
        //ustawienie live'ów
        if(lives.size() > 5){
            for(int i = 0; i < 5; i++){
                Ilive.get(i).setImage(lives.get(i).getObrazek());
            }
        }else if(lives.size() >0 && lives.size() < 5){
            for(int i = 0; i< lives.size(); i++){
                Ilive.get(i).setImage(lives.get(i).getObrazek());
            }
        }
        if(lives.isEmpty()){
            for(int i = 0; i < 5;i++){
                Ilive.get(i).setImage(null);
            }
        }
        if(serials.isEmpty()){
            for(int i = 0; i < 5;i++){
                Iserial.get(i).setImage(null);
            }
        }
        if(films.isEmpty()){
            for(int i = 0; i < 5;i++){
                Ifilm.get(i).setImage(null);
            }
        }
    }

    @FXML
    public void showDetale(MouseEvent event){
        if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
          Dialog<ButtonType> dialog = new Dialog<>();
          dialog.initOwner(mainBorderPane.getScene().getWindow());
          FXMLLoader loader = new FXMLLoader();
          loader.setLocation(getClass().getResource("/GUI/productionDetails.fxml"));
            try {
                dialog.getDialogPane().setContent(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            ProductionController controller = loader.getController();
            ImageView imageView = (ImageView) event.getSource();
            Image image = imageView.getImage();
            controller.setTextFields(((ProdImage)image).getProdukcja());
            Optional<ButtonType> result = dialog.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                dialog.close();
            }
        }
    }

    @FXML
    public void onListaUserClicked(MouseEvent event) {
        if (event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY) {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initOwner(mainBorderPane.getScene().getWindow());
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/GUI/klientDetails.fxml"));
            try {
                dialog.getDialogPane().setContent(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            ClientController controller = loader.getController();
            User user = listaUser.getSelectionModel().getSelectedItem();
            
            controller.setData(user);
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                dialog.close();
            }
        }
    }
    @FXML
    public void searchButtonClicked(MouseEvent event){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/search.fxml"));
        try {
            dialog.getDialogPane().setContent(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        searchController controller = loader.getController();
        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            dialog.close();
        }
    }

    @FXML
    public void nextLiveButtonClicked(MouseEvent event){
        Produkcja produkcja = null;
        if(l5.getImage() != null){
            produkcja = ((ProdImage)l5.getImage()).getProdukcja();
            int lastind = lives.indexOf(produkcja) +1;
            //ustawienie live'ów
            if((lives.size() - lastind) > 5){
                for(int i = 0; i < 5; i++){
                    Ilive.get(i).setImage(lives.get(i).getObrazek());
                }
            }else if((lives.size() - lastind) >0 && (lives.size() - lastind) < 5){
                for(int i = 0; i< lives.size() - lastind; i++){
                    Ilive.get(i).setImage(lives.get(i).getObrazek());
                }
            }
        }

    }
    @FXML
    public void nextSerialButtonClicked(MouseEvent event){
        Produkcja lastProdukcja = null;
        for(int i = 0; i < Iserial.size(); i++){
            if(Iserial.get(i).getImage() != null) lastProdukcja = ((ProdImage)Iserial.get(i).getImage()).getProdukcja();
        }
        int index = serials.indexOf(lastProdukcja)+1;
        System.out.println("Indeks: " + index);
        if((serials.size() - index) > 5){
            for(int i = 0; i < 5; i++){
                Iserial.get(i).setImage(serials.get(index+i).getObrazek());
            }
        }else if((serials.size()-index) < 5 && (serials.size() - index) >0){
            for(int i = 0; i < 5; i++){
                Iserial.get(i).setImage(null);
            }
            for(int i=0;i< serials.size()-index;i++){
                Iserial.get(i).setImage(serials.get(index+i).getObrazek());
            }
        }

    }
    @FXML
    public void nextFilmButtonClicked(MouseEvent event){
        Produkcja lastProdukcja = null;
        for(int i = 0; i < Ifilm.size(); i++){
            if(Ifilm.get(i).getImage() != null) lastProdukcja = ((ProdImage)Ifilm.get(i).getImage()).getProdukcja();
        }
        int index = films.indexOf(lastProdukcja) +1;
        System.out.println("Indeks: " + index);
        if((films.size() - index) > 5){
            for(int i = 0; i < 5; i++){
                Ifilm.get(i).setImage(films.get(index+i).getObrazek());
            }
        }else if((films.size()-index) < 5 && (films.size() - index) >0){
            for(int i = 0; i < 5; i++){
                Ifilm.get(i).setImage(null);
            }
            for(int i=0;i< films.size()-index;i++){
                Ifilm.get(i).setImage(films.get(index+i).getObrazek());
            }
        }
    }
    @FXML
    public void prevSButton(MouseEvent event){
        Produkcja firstProdukcja = null;
        firstProdukcja = ((ProdImage) Iserial.get(0).getImage()).getProdukcja();
        int firstInd = serials.indexOf(firstProdukcja);
        if(firstInd - 5 >= 0){
            for(int i = 0; i < 5; i++){
                Iserial.get(i).setImage(serials.get(firstInd-5+i).getObrazek());
            }
        }else if(firstInd-5 < 0);

    }
    @FXML
    public void prevFButton(MouseEvent event){
        Produkcja firstProdukcja = null;
        firstProdukcja = ((ProdImage) Ifilm.get(0).getImage()).getProdukcja();
        int firstInd = films.indexOf(firstProdukcja)-1;
        if(firstInd - 5 >= 0){
            for(int i = 0; i < 5; i++){
                Ifilm.get(i).setImage(films.get(firstInd-5+i).getObrazek());
            }
        }else if(firstInd-5 < 0);
    }
    @FXML
    public void prevLButton(MouseEvent event){
        Produkcja firstProdukcja = null;
        firstProdukcja = ((ProdImage) Ilive.get(0).getImage()).getProdukcja();
        int firstInd = lives.indexOf(firstProdukcja);
        if(firstInd - 5 >= 0){
            for(int i = 0; i < 5; i++){
                Ilive.get(i).setImage(lives.get(firstInd-5+i).getObrazek());
            }
        }else if(firstInd-5 < 0);
    }

    @FXML
    public void saveAll(){
        Main.saveDealer();
        Main.saveUser();

    }
    @FXML
    public void loadAll(){
        Main.loadDealer();
        Main.loadUser();
        if(Main.getDealers().size() > 0){
            listaDealer.getItems().addAll(Main.getDealers());
        }
        if(Main.getUsers().size()>0){
            listaUser.getItems().addAll(Main.getUsers());
        }
        refresh();
    }

    /**
     * Pokazuje wszystkie produkcje w jednej tabeli.
     */
    @FXML
    public void showAllProd(){
        ListView<Produkcja>allProd = new ListView<>();
        if(showAll.isSelected()){
            mainBorderPane.getScene().getWindow().setWidth(866);
            allProd.setPrefWidth(200);
            mainBorderPane.setLeft(allProd);
            allProd.getItems().addAll(films);
            allProd.getItems().addAll(serials);
            allProd.getItems().addAll(lives);
            allProd.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(event.getClickCount() == 2){
                        Dialog<ButtonType> dialog = new Dialog<>();
                        dialog.initOwner(mainBorderPane.getScene().getWindow());
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/GUI/productionDetails.fxml"));
                        try {
                            dialog.getDialogPane().setContent(loader.load());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                        ProductionController controller = loader.getController();
                        Produkcja produkcja = allProd.getSelectionModel().getSelectedItem();
                        controller.setTextFields(produkcja);
                        Optional<ButtonType> result = dialog.showAndWait();
                        if(result.isPresent()) dialog.close();
                    }
                }
            });

        }else{
            mainBorderPane.getScene().getWindow().setWidth(666);
            mainBorderPane.setLeft(null);
            allProd.getItems().removeAll();
            allProd.setDisable(true);
        }
    }

}
