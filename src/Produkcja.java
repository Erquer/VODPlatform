import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class Produkcja {
    //wspólne zmienne wszystkich produkcji

    private String nazwa;
    private String opis;
    private LocalDate data;
    private Dealer dealer;
    private List<Kraje> eKraje;
    private double ocena;
    private int cena;


    public int getCena() {
        return cena;
    }

    public Produkcja(Dealer dealer) {
        this.eKraje = new ArrayList<>();
        String alfa = "ABCDEFGHIJKLMNOPQRSTUVWXYZ abcdefghijklmnopqrstuvwxyz 1234567890";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        int ind;
        int temp = random.nextInt(20) + 10;

        //Ustawianie Nazwy
        for(int i = 0; i < temp; i++){
            ind = random.nextInt(alfa.length());
            sb.append(alfa.charAt(ind));
        }
        this.nazwa = sb.toString();

        sb.delete(0,sb.length());

        //Ustawianie opisu
        temp = random.nextInt(100) + 50;
        for(int i = 0; i < temp; i++){
            ind = random.nextInt(alfa.length());
            sb.append(alfa.charAt(ind));
        }
        this.opis = sb.toString();

        //ustalanie daty
        long minDay = LocalDate.of(1970,1,1).toEpochDay();
        long maxDay = LocalDate.of(2020,12,31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay,maxDay);
        this.data = LocalDate.ofEpochDay(randomDay);

        this.dealer = dealer;
        temp = random.nextInt(4) +1;
        for(int i = 0; i < temp; i++){
            eKraje.add(Kraje.values()[random.nextInt(Kraje.values().length)]);
        }

        //Ustalanie oceny
        this.ocena += random.nextDouble() * 10;
        this.ocena = Math.round(this.ocena);
        this.ocena /= 10;
        this.ocena += random.nextInt(10);
        //cena
        this.cena = random.nextInt(50)+25;

    }

    public String getNazwa() {
        return nazwa;
    }

    public String getOpis() {
        return opis;
    }

    public LocalDate getData() {
        return data;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public List<Kraje> geteKraje() {
        return eKraje;
    }

    public double getOcena() {
        return ocena;
    }
}
