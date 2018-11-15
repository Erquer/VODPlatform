import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private static volatile List<Film> filmy;
    private static volatile List<Live> live;
    private static volatile List<Serial> seriale;



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

        Thread threads = new Thread();
        Test test= new Test();
        Thread thread = new Thread(test);
        List<User> list = new ArrayList<>();
        list.add(new User());
        threads = new Thread(list.get(0));
        threads.start();
        thread.start();
        list.get(0).kupFilm(new Film(new Dealer()));
        list.get(0).kupAbonament();
        while(true){
         test.kupUserowi(list.get(0));
         try {
             Thread.sleep(10000);
         }catch (InterruptedException e){
             e.printStackTrace();
         }
        }





    }

    private static class Test implements Runnable{
        @Override
        public void run() {
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
}
