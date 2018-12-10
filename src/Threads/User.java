package Threads;

import Enums.Abonament;
import GUI.Main;
import Produkcje.Film;
import Produkcje.Live;
import Produkcje.Produkcja;
import Produkcje.Serial;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class User implements Runnable, Serializable {
    private String ID;
    private LocalDate birth;
    private String email;
    private String cardNumber;
    private Abonament abonament;
    private LocalDate terminAbonamentu;
    private List<Produkcja> kupioneFilmy;
    private Map<Produkcja, LocalDate> datyKupionych;
    private transient boolean canWork;
    private LocalDate mainDate;

    @Override
    public void run() {

        while(Main.isCanUserWork()) {
            mainDate = Main.getDate();
            System.out.println("Mamy dzisiaj: " + mainDate);
            if(canWork){
                Random random = new Random();
                int wybor = random.nextInt(10000);
                System.out.println("wybór" + wybor);
               if(this.kupioneFilmy.size() > 0){
                   if(abonament == null){
                        if(wybor < 3000) kupAbonament();
                        else if(wybor > 3500 && wybor < 6000) kupFilm();
                        else if(wybor >6500 && wybor < 9500) watch();
                        else{
                            System.out.println(this.ID + " Nic nie robi i czeka, a ma abonament do: " + terminAbonamentu);
                            System.out.println("Biblioteka Filmów: ");
                            for(Produkcja prod: this.kupioneFilmy){
                                System.out.println("Tytuł: " + prod + "w bibliotece do: " + this.datyKupionych.get(prod));
                            }
                            try {
                                Thread.sleep(8000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                   }else if(abonament != null) {
                       checkAbonament();
                       if (wybor < 3000) kupFilm();
                       else if (wybor > 3500 && wybor < 7000) watch();
                       else {
                           System.out.println(this.ID + " Nic nie robi i czeka");
                           System.out.println("Biblioteka Filmów: ");
                           for (Produkcja prod : this.kupioneFilmy) {
                               System.out.println("Tytuł: " + prod + "w bibliotece do: " + this.datyKupionych.get(prod));
                           }
                           try {
                               Thread.sleep(8000);
                           } catch (InterruptedException e) {
                               e.printStackTrace();
                           }
                       }
                   }
                   checkDates();
               }else if(this.kupioneFilmy.size() == 0){
                   if(abonament == null){
                       if(wybor < 3000) kupAbonament();
                       else if(wybor > 3500 && wybor < 7000) kupFilm();
                       else{
                           System.out.println(this.ID + " Nic nie robi i czeka, nie ma nic w bibliotece filmów i nie ma abonamentu");
                           try {
                               Thread.sleep(8000);
                           } catch (InterruptedException e) {
                               e.printStackTrace();
                           }
                       }
                   }else {
                       checkAbonament();
                       if(wybor < 6000) kupFilm();
                       else{
                           System.out.println(this.ID + " Nic nie robi i czeka, nie ma nic w bibliotece filmów i ma abonament " + abonament);
                           try {
                               Thread.sleep(8000);
                           } catch (InterruptedException e) {
                               e.printStackTrace();
                           }
                       }

                   }

               }



            }
            //przypadek kiedy użytkownik chce usunąć danego klienta
            else if(!canWork) break ;
        }



        }

    public User() {
        this.datyKupionych = new HashMap<>();
        this.kupioneFilmy = new CopyOnWriteArrayList<>();
        this.canWork = true;
        String alfa = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        int temp;
        temp = random.nextInt(6)+10;
        for(int i = 0; i < temp; i++){
            sb.append(alfa.charAt(random.nextInt(alfa.length())));
        }
        this.ID = sb.toString();
        sb.append("@gmail.com");
        this.email = sb.toString();
        sb.delete(0,sb.length());

        for(int i=0;i<16; i++){
            sb.append(random.nextInt(10));
        }
        this.cardNumber = sb.toString();

        long minDay = LocalDate.of(1970,1,1).toEpochDay();
        long maxDay = LocalDate.of(2020,12,31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay,maxDay);
        this.birth = LocalDate.ofEpochDay(randomDay);
        this.mainDate = Main.getDate();
        this.terminAbonamentu = null;
        this.abonament = null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.ID + " ");
        sb.append(this.email + " ");
        sb.append(this.cardNumber + " ");
        sb.append(this.birth + " ");
        return sb.toString();
    }

    public void kupFilm() {
        if(Main.getFilmy().size() > 0) {
            int los = new Random().nextInt(Main.getFilmy().size() + 1);
            if (los > 0) los = los - 1;
            Produkcja prod = Main.getFilmy().get(los);
            if (!kupioneFilmy.contains(prod)) {
                if (abonament == null) {
                    if (prod instanceof Film) {
                        this.kupioneFilmy.add(prod);
                        Main.buyProduction(prod);
                        this.datyKupionych.put(prod, mainDate.plusWeeks(3));
                        System.out.println(this.ID + " kupiłem Produkcje.Film " + prod.getNazwa() + " i mogę go oglądać do" + this.datyKupionych.get(prod));
                    } else if (prod instanceof Live && ((Live) prod).getLiveStream().isBefore(mainDate)) {
                        this.kupioneFilmy.add(prod);
                        this.datyKupionych.put(prod, ((Live) prod).getLiveStream());
                        Main.buyProduction(prod);
                        System.out.println(this.ID + " kupiłem Produkcje.Live " + prod.getNazwa() + " i jest dostępny w " + this.datyKupionych.get(prod));
                    } else if (prod instanceof Serial) {
                        this.kupioneFilmy.add(prod);
                        this.datyKupionych.put(prod, mainDate.plusWeeks(3));
                        Main.buyProduction(prod);
                        System.out.println(this.ID + " kupiłem Produkcje.Serial " + prod.getNazwa() + " i mogę go oglądać do" + this.datyKupionych.get(prod));
                    }

                } else if (abonament != null) {
                    if (prod instanceof Film) {
                        this.kupioneFilmy.add(prod);
                       // GUI.Main.buyProduction(prod);
                        this.datyKupionych.put(prod, this.terminAbonamentu);
                        System.out.println(this.ID + " dodałem do biblioteki " + prod.getNazwa() + " i mogę go oglądać do " + this.datyKupionych.get(prod));
                    } else if (prod instanceof Live && prod.getData().isBefore(mainDate)) {
                        this.kupioneFilmy.add(prod);
                        this.datyKupionych.put(prod, ((Live) prod).getLiveStream());
                        Main.buyProduction(prod);
                        System.out.println(this.ID + " kupiłem Produkcje.Live " + prod.getNazwa() + " i jest dostępny w " + this.datyKupionych.get(prod));
                    } else if (prod instanceof Serial) {
                        this.kupioneFilmy.add(prod);
                        this.datyKupionych.put(prod, this.terminAbonamentu);
                     //   GUI.Main.buyProduction(prod);
                        System.out.println(this.ID + " dodałem do biblioteki " + prod.getNazwa() + " i mogę go oglądać do " + this.datyKupionych.get(prod));
                    }
                }

                //czas potrzebny do kupienia filmu
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            } else {
                System.out.println("Brak filmów w bazie");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


    public void kupAbonament(){
            int i = new Random().nextInt(3);
            this.abonament = Abonament.values()[i];
            this.terminAbonamentu = Main.getDate().plus(1,ChronoUnit.MONTHS);
            if(this.abonament == Abonament.Premium){
                Main.buyAbonament(50);
            }
            else if(this.abonament == Abonament.Family){
                Main.buyAbonament(40);
            }
            else if(this.abonament == Abonament.Basic){
                Main.buyAbonament(30);
            }
        System.out.println( this.ID + " Kupiłem abonament: " + this.abonament);
        //po kupieniu abonamentu użytkownik musi poczekać.
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void watch(){
        Random random = new Random();

            int wybor = random.nextInt(this.kupioneFilmy.size());

            Produkcja produkcja = this.kupioneFilmy.get(wybor);
            if ((produkcja instanceof Film || produkcja instanceof Serial)) {
                System.out.println(this.ID + " ogląda -" + produkcja.getNazwa());
                if (produkcja instanceof Film) {
                    try {
                        Thread.sleep(12000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (produkcja instanceof Serial) {
                    try {
                        Thread.sleep(8000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else if (produkcja instanceof Live && datyKupionych.get(produkcja).isEqual(Main.getDate())) {
                System.out.println(this.ID + " Produkcje.Live ogląda- " + produkcja.getNazwa());
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

    }

    private void checkDates(){
        if(abonament == null){
            for(Iterator<Produkcja> iterator = this.kupioneFilmy.iterator();iterator.hasNext();){
                Produkcja produkcja = iterator.next();
                if(this.datyKupionych.get(produkcja).isEqual(Main.getDate()) && !(produkcja instanceof Live)){
                    System.out.println("Bez Abonamentu|Usuwam z biblioteki " + produkcja.getNazwa());
                    this.datyKupionych.remove(produkcja);
                    this.kupioneFilmy.remove(produkcja);
                }else if(produkcja instanceof Live && this.datyKupionych.get(produkcja).isAfter(Main.getDate())){
                    System.out.println("Bez Abonamentu|Usuwam Produkcje.Live: " + produkcja.getNazwa());
                    this.datyKupionych.remove(produkcja);
                    this.kupioneFilmy.remove(produkcja);
                }
            }
        }else if(abonament != null){
            for(Iterator<Produkcja> iterator = this.kupioneFilmy.iterator();iterator.hasNext();){
                Produkcja produkcja = iterator.next();
                if(this.datyKupionych.get(produkcja).isEqual(Main.getDate()) && !(produkcja instanceof Live)){
                    System.out.println("Enums.Abonament|Usuwam z biblioteki: " + produkcja.getNazwa());
                    this.kupioneFilmy.remove(produkcja);
                    this.datyKupionych.remove(produkcja);
                }else if(produkcja instanceof Live && this.datyKupionych.get(produkcja).isEqual(Main.getDate())){
                    System.out.println("Enums.Abonament|Usuwam Produkcje.Live: " + produkcja.getNazwa());
                    this.datyKupionych.remove(produkcja);
                    this.kupioneFilmy.remove(produkcja);
                }
            }
        }
    }
    public void checkAbonament(){
        if((abonament != null)){
            if(terminAbonamentu.isEqual(mainDate)){
                abonament = null;
                terminAbonamentu = null;
                System.out.println("Enums.Abonament się skończył u " + this.ID );
            }
        }
    }

    public List<Produkcja> getKupioneFilmy() {
        return kupioneFilmy;
    }


}
