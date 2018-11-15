import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);

        Serial serial = new Serial(new Dealer());

        serial.showData();

        Film film = new Film(new Dealer());

        film.showData();

        Thread[] threads = new Thread[10];
        List<User> list = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            list.add(new User());
            threads[i] = new Thread(list.get(i));
            threads[i].start();
        }



    }
}
