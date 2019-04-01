package Produkcje;

import Enums.Aktorzy;
import Enums.Kraje;
import Enums.Typ;
import Threads.Dealer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Film extends Produkcja {
   // private boolean promocja;
    private Typ typ;
    private List<String> linki;

    public Typ getTyp() {
        return typ;
    }

    public List<String> getLinki() {
        return linki;
    }

    public Film(ProdImage obrazek, String nazwa, String opis, LocalDate data, Dealer dealer, List<Kraje> eKraje, List<Aktorzy> obsada, double ocena, int cena, Typ typ, List<String> linki) {
        super(obrazek, nazwa, opis, data, dealer, eKraje, obsada, ocena, cena);
        this.typ = typ;
        this.linki = linki;

    }

    public Film(Dealer dealer) {
        super(dealer);
        this.linki = new ArrayList<>();
        Random random = new Random();
       // this.promocja = false;
        this.typ = Typ.values()[random.nextInt(Typ.values().length)];
        String alfa = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();

        int temp1, temp2;
        temp1 = random.nextInt(4)+1;
        for(int i = 0; i<temp1;i++){
            temp2 = random.nextInt(10)+10;
            sb.append("www.");
            for(int j=0;j<temp2;j++){
                sb.append(alfa.charAt(random.nextInt(alfa.length())));
            }
            sb.append(".com");
            this.linki.add(sb.toString());
            sb.delete(0,sb.length());
        }
        //dealer.addProd(this);

    }

    @Override
    public void showData(){
        System.out.println("Produkcje.Film: \n"+
                "Tytuł: " +this.getNazwa() +
                "\nGatunek: " + this.typ +
                "\nOpis: " + this.getOpis() +
                "\nCena: " + this.getCena() +
                "\nData premiery: " + this.getData() +
                "\nOcena: " + this.getOcena()+ "\nLinki do zwiastunów: ");

        for(int i = 0; i < linki.size();i++){
            System.out.println(linki.get(i));
        }
        System.out.println("Enums.Kraje produkcji: ");
        for(int i = 0; i < this.geteKraje().size();i++){
            System.out.println(this.geteKraje().get(i));
        }

    }

    @Override
    public String toString() {

        return "Film: " +super.toString() + " Typ: " + this.typ;
    }
}
