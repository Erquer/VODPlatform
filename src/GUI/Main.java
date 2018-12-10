package GUI;

import Produkcje.Produkcja;
import Threads.Dealer;
import Threads.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Main extends Application {

    //Monitory
    private final static Object guardianUser = new Object();
    private final static Object guardianDealer = new Object();
    private final static Object guardianProduction = new Object();
    private final static Object guardianPayment = new Object();

    //zmienne boolowskie
    private static boolean canUserWork = true;
    private static boolean canDealerWork = true;
    private static boolean canProgramWork = true;

    public static boolean isCanUserWork() {
        return canUserWork;
    }

    public static boolean isCanDealerWork() {
        return canDealerWork;
    }

    //produkcje
    private static volatile LinkedList<Produkcja> production = new LinkedList<>();


    //sekcje krytyczne
    private static int dniStrat;
    private static volatile double saldo;

    private static volatile LinkedList<Dealer> dealers = new LinkedList<>();
    private static volatile LinkedList<User> users = new LinkedList<>();


    public static volatile LinkedList<Thread> dealerThreads = new LinkedList<>();
    public static volatile LinkedList<Thread> userThreads = new LinkedList<>();

    private static volatile LocalDate date = LocalDate.of(2015,1,1);

    private static Controller controller;

    public static Controller getController() {
        return controller;
    }

    private static final String pathDealers = "Dealers.txt";
    private static final String pathUsers = "Users.txt";
    private static final String pathFilms = "Films.txt";
    private static final String pathSerials = "Serials.txt";
    private static final String pathLives = "Lives.txt";


    public static LinkedList<Dealer> getDealers() {
        return dealers;
    }
    public static LocalDate getDate() {
        return date;
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("sample.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("VOD Platform");
        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.show();
        dniStrat = 0;
        saldo = 0;
        controller = loader.getController();




        // symulacja czasu.
        synchronized (guardianUser) {
            new Thread() {
                @Override
                public void run() {
                    while (canProgramWork) {
                        //System.out.println("było:" + date);
                        setDate(date.plusDays(1));
                        try {
                            sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //checkLooses();
                        // System.out.println("Po sprawdzeniu strat jest " + saldo + " i dni z rzędu strat: " + dniStrat );
                       // System.out.println("jest:" + date);

                    }
                }
            }.start();
        }
    }

    public static void setDate(LocalDate date) {
        Main.date = date;
    }

    public static void main(String[] args) {
        launch(args);
    }


    public static List<Produkcja> getFilmy() {
        return production;
    }



    public static void addProdukcja(Produkcja prod) {
        synchronized (guardianProduction) {
          production.add(prod);
        }
    }

    public static void addDealer(Dealer dealer){
        synchronized (guardianDealer) {
            Main.getDealers().add(dealer);
            dealerThreads.addLast(new Thread(dealer));
            dealerThreads.getLast().setDaemon(true);
            dealerThreads.getLast().start();
            System.out.println("nowy dealer działą");
        }
    }

    public static void addUser(User user){
        synchronized (guardianUser){
            users.addLast(user);
            userThreads.addLast(new Thread(user));
            userThreads.getLast().setDaemon(true);
            userThreads.getLast().start();
            System.out.println("nowy user działą");
        }
    }

    synchronized
    public static void pay(Dealer dealer){
        synchronized (guardianPayment) {
            saldo -= dealer.getCenaUslug();
        }
    }

    synchronized
    public static void buyProduction(Produkcja produkcja){
        synchronized (guardianPayment) {
            saldo += produkcja.getCena();
        }
    }

    synchronized
    public static void buyAbonament(int cena){
        synchronized (guardianPayment) {
            saldo += cena;
        }
    }

    synchronized
    public static void checkLooses(){
        if(saldo < 0){
            dniStrat++;
        }
        else{
            dniStrat = 0;
        }
        if(dniStrat >= 90){
            canDealerWork = false;
            canUserWork = false;
            canProgramWork = false;
        }
    }


    public void removeAllThreads(){
            for(int i = 0; i < users.size();i++){
                if(userThreads.get(i).isAlive()){
                    userThreads.remove(i);
                    System.out.println("usunąłem wątek usera");
                }
            }
            for(int i = 0; i < dealers.size();i++){
                if(dealerThreads.get(i).isAlive()){
                    dealerThreads.remove(i);
                    System.out.println("usunąłem wątek Dealera");
                }
            }

    }

    @Override
    public void stop() throws Exception {
        canDealerWork = false;
        canProgramWork = false;
        canUserWork = false;
    //    removeAllThreads();
        Thread.sleep(100);
        super.stop();
    }
}
