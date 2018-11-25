import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.List;

public class Main extends Application {

    private static volatile LinkedList<Film> filmy = new LinkedList<>();
    private static volatile LinkedList<Live> live = new LinkedList<>();
    private static volatile LinkedList<Serial> seriale = new LinkedList<>();

    private static volatile LinkedList<Dealer> dealers = new LinkedList<>();


    public static LinkedList<Live> getLive() {
        return live;
    }

    public static LinkedList<Serial> getSeriale() {
        return seriale;
    }

    public static LinkedList<Dealer> getDealers() {
        return dealers;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("VOD Platform");
        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);


    }

    private static class Test implements Runnable{
        @Override
        public void run() {
            while (true)
            try {
                Thread.sleep(10000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }


        }
        private void kupUserowi(User user){
            user.kupFilm(new Film(new Dealer()));
        }

    }

    public static List<Film> getFilmy() {
        return filmy;
    }





    public static void addProdukcja(Produkcja prod){
        if(prod instanceof Film){
            Main.filmy.add((Film)prod);
        }else if(prod instanceof Serial){
            Main.seriale.add((Serial) prod);
        }else if(prod instanceof Live) Main.live.add((Live) prod);
    }

    public static void addDealer(Dealer dealer){
        Main.getDealers().add(dealer);
    }

}
