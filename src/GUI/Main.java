package GUI;

import Produkcje.Film;
import Produkcje.Live;
import Produkcje.Produkcja;
import Threads.Dealer;
import Threads.User;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 *
 */
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
    private static boolean promocja;
    private static int pro;
    private static volatile double saldo;

    private static volatile LinkedList<Dealer> dealers = new LinkedList<>();
    private static volatile LinkedList<User> users = new LinkedList<>();


    public static volatile LinkedList<Thread> dealerThreads = new LinkedList<>();
    public static volatile LinkedList<Thread> userThreads = new LinkedList<>();

    private static volatile LocalDate date = LocalDate.of(2015,1,1);

    private static Controller controller;

    //public static Controller getController() {
       // return controller;
   // }

    private static final String pathDealers = "Dealers.bin";
    private static final String pathUsers = "Users.txt";

    public static LinkedList<User> getUsers() {
        return users;
    }

    public static LinkedList<Thread> getDealerThreads() {
        return dealerThreads;
    }

    public static LinkedList<Thread> getUserThreads() {
        return userThreads;
    }

    public static LinkedList<Dealer> getDealers() {
        return dealers;
    }
    public static LocalDate getDate() {
        return date;
    }

    public static double getSaldo() {
        return saldo;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
       FXMLLoader loader = new FXMLLoader();
       loader.setLocation(this.getClass().getResource("/GUI/sample.fxml"));

        Parent root = loader.load();
        primaryStage.setTitle("VOD Platform");
        primaryStage.setScene(new Scene(root, 680, 800));
        primaryStage.show();
        dniStrat = 0;
        saldo = 0;
        controller = loader.getController();
        //Controller do zmiennej (nullPointerExeption)

        System.out.println(controller.toString());


        // symulacja czasu.
        synchronized (guardianUser) {
            new Thread() {
                @Override
                public void run() {
                    while (canProgramWork) {
                        //System.out.println("było:" + date);
                        setDate(date.plusDays(1));
                        isPromocja();
                        try {
                            sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        checkLooses();
                        Platform.runLater(
                                ()->{
                                    controller.setDataLabel(date);
                                    controller.setSaldoLabel(Double.toString(saldo));
                                });

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


    /**
     * @param prod
     * dodanie nowej Produkcji do list
     */
    public static void addProdukcja(Produkcja prod) {
        synchronized (guardianProduction) {
          production.add(prod);
          controller.addProd(prod);
        }
    }

    /**
     * @param dealer
     * dodanie do list nowego dealera i stworzenie nowego wątku Dealera
     */
    public static void addDealer(Dealer dealer){
        synchronized (guardianDealer) {
            Main.getDealers().add(dealer);
            dealerThreads.addLast(new Thread(dealer));
            dealerThreads.getLast().setDaemon(true);
            dealerThreads.getLast().start();
            System.out.println("nowy dealer działą");
        }
    }

    /**
     * @param user
     * dodanie usera do listy Userów, listy wątków oraz stworzenie nowego wątku Usera
     */
    public static void addUser(User user){
        synchronized (guardianUser){
            users.addLast(user);
            userThreads.addLast(new Thread(user));
            userThreads.getLast().setDaemon(true);
            userThreads.getLast().start();
            System.out.println("nowy user działą");
        }
    }

    public static Controller getController() {
        return controller;
    }

    /**
     * @param dealer
     * Usunięcie dealera
     */
    synchronized
    public static void deleteDealer(Dealer dealer){
        dealers.remove(dealer);
        dealerThreads.remove(dealer);
        for(User user: users){
            for(int i = 0; i < dealer.getStworzoneProdukcje().size(); i++) {
                if(user.getKupioneFilmy().contains(dealer.getStworzoneProdukcje().get(i))){
                    user.getKupioneFilmy().remove(dealer.getStworzoneProdukcje().get(i));
                    user.getDatyKupionych().remove(dealer.getStworzoneProdukcje().get(i));
                }
            }
        }
        production.removeAll(dealer.getStworzoneProdukcje());
        dealer.setCanWork(false);
    }

    /**
     * @param dealer
     * Funkcja płatności platformy dealerowi za usługę.
     */
    synchronized
    public static void payToDealer(Dealer dealer){
        synchronized (guardianPayment) {
            if(dealer.getLastPayment() != null && dealer.getLastPayment().isEqual(Main.getDate().minusDays(30))) {
                saldo -= dealer.getCenaUslug();
                dealer.setLastPayment(Main.getDate());
            }else if(dealer.getLastPayment() == null){
                saldo -= dealer.getCenaUslug();
                dealer.setLastPayment(Main.getDate());
            }
        }
    }

    /**
     * @param produkcja
     * pobranie platformy od Usera za produkcję określoną w parametrze.
     */
    synchronized
    public static void buyProduction(Produkcja produkcja){
        synchronized (guardianPayment) {
            if(promocja && (produkcja instanceof Film || produkcja instanceof Live)){
                saldo += (produkcja.getCena() - (produkcja.getCena() * (pro/100)));
            }else
                saldo += produkcja.getCena();
        }
    }

    /**
     * @param cena
     * Kupienie przez usera abonamentu.
     */
    synchronized
    public static void buyAbonament(int cena){
        synchronized (guardianPayment) {
            saldo += cena;
        }
    }

    /**
     *
     * Sprawdzanie, czy platforma generuje straty.
     */
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

    /**
     * Losowanie czy promocja obowiązuje
     */
    public static void isPromocja(){
        Random random = new Random();
        int isPromocja = random.nextInt(1000);
        int promocjaInt;
        promocjaInt = random.nextInt(5);
        if(isPromocja < 30){
            promocja = true;
            pro = isPromocja;
        }else if(promocja && isPromocja > 600){
            promocja = false;
            pro = 0;
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


    /**
     * zapis Dealrów
     */
    public static void saveDealer(){
        File file = new File(pathDealers);
        if (file.exists())
        try {
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
            synchronized (guardianDealer) {
                if (dealers.size() != 0)
                    for (Dealer dealer : dealers) {
                        output.writeObject(dealer);
                    }
            }
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /**
     * Zapis Userów.
     */
    public static void saveUser(){
        File file = new File(pathUsers);
        if(file.exists()){
            try {
                ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
                synchronized (guardianUser) {
                    if (users.size() != 0)
                        for (User user : users) {
                            outputStream.writeObject(user);
                        }
                }
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ładowanie dealerów z pliku.
     */
    public static void loadDealer(){
        File file = new File(pathDealers);
        if(file.exists())
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
            while (true){
                    Dealer dealer = (Dealer) inputStream.readObject();
                    dealer.setCanWork(true);
                    System.out.println(dealer.getID());
                    addDealer(dealer);
            }
        } catch (IOException e) {
            System.out.println("Koniec pliku dealerów");
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            file=null;
        }
        file = null;
    }

    /**
     * Ładowanie Userów z pliku
     */
    public static void loadUser(){
        File file = new File(pathUsers);
        if(file.exists()){
            try {
                    ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
                    while (true){
                        User user = (User) inputStream.readObject();
                        addUser(user);
                    }

            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                System.out.println("Koniec pliku");
            }finally {
                file = null;
            }
            file = null;
        }
    }
}
