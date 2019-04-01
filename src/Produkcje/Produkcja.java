package Produkcje;

import Enums.Aktorzy;
import Enums.Kraje;
import Enums.Obrazy;
import Threads.Dealer;
import javafx.scene.image.Image;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class Produkcja implements Serializable {
    //wspólne zmienne wszystkich produkcji
    private ProdImage obrazek;
    private String nazwa;
    private String opis;
    private LocalDate data;
    private Dealer dealer;
    private List<Kraje> eKraje;
    private List<Aktorzy> obsada;
    private double ocena;
    private int cena;


    private HashMap<LocalDate, Integer> ogladalnosc;


    public int getCena() {
        return cena;
    }

    public Image getObrazek() {
        return obrazek;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }



    public Produkcja(ProdImage obrazek, String nazwa, String opis, LocalDate data, Dealer dealer, List<Kraje> eKraje, List<Aktorzy> obsada, double ocena, int cena) {
        this.obrazek = obrazek;
        this.nazwa = nazwa;
        this.opis = opis;
        this.data = data;
        this.dealer = dealer;
        this.eKraje = eKraje;
        this.obsada = obsada;
        this.ocena = ocena;
        this.cena = cena;

    }

    public HashMap<LocalDate, Integer> getOgladalnosc() {
        return ogladalnosc;
    }

    public void wached(LocalDate data){
        if(this.ogladalnosc.containsKey(data)){
              Integer i = new Integer(this.ogladalnosc.get(data).intValue());
              i++;
              this.ogladalnosc.replace(data,i);
        }else{
            this.ogladalnosc.put(data, 1);
        }
    }

    public Produkcja(Dealer dealer) {
        this.ogladalnosc = new HashMap<>();
        this.eKraje = new ArrayList<>();
        this.obsada = new ArrayList<>();
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        int in = random.nextInt(Obrazy.values().length);
        sb.append("Produkcje\\"+Obrazy.values()[in]+".png");

        this.obrazek= new ProdImage(sb.toString(),this);

        sb.delete(0,sb.length());

        String alfa = "ABCDEFGHIJKLMNOPQRSTUVWXYZ abcdefghijklmnopqrstuvwxyz 1234567890";


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
        temp = random.nextInt(20)+5;
        for(int i = 0; i < temp; i++){
            this.obsada.add(Aktorzy.values()[random.nextInt(Aktorzy.values().length)]);
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

    public List<Aktorzy> getObsada() {
        return obsada;
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

    public void showData(){
        System.out.println("Tytuł: " +this.getNazwa() +
                " Opis: " + this.getOpis() +
                " Cena: " + this.getCena() +
                " Data premiery: " + this.getData() +
                " Ocena: " + this.getOcena()+ " Linki do zwiastunów: ");
        System.out.println("Kraje produkcji: ");
        for(int i = 0; i < this.geteKraje().size();i++){
            System.out.println(this.geteKraje().get(i));
        }
    }

    @Override
    public String toString() {
        return "Tytuł "  + nazwa +
                " Opis " + opis +
                " Data " + data +
                " Dealer " + dealer +
                " Kraje " + eKraje +
                " Obsada " + obsada +
                " Ocena " + ocena +
                " cena " + cena;
    }
}
