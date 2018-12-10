package Produkcje;

import Enums.Aktorzy;
import Enums.Kraje;
import Enums.Typ;
import Threads.Dealer;
import javafx.scene.image.Image;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class Serial extends Produkcja {
private Typ typ;
private List<Sezon> sezons;

    public Serial(Image obrazek, String nazwa, String opis, LocalDate data, Dealer dealer, List<Kraje> eKraje, List<Aktorzy> obsada, double ocena, int cena, Typ typ, List<Sezon> sezons) {
        super(obrazek, nazwa, opis, data, dealer, eKraje, obsada, ocena, cena);
        this.typ = typ;
        this.sezons = sezons;
    }

    public Serial(Dealer dealer) {
        super(dealer);
        this.sezons = new ArrayList<>();
        Random random = new Random();
        for(int i = random.nextInt(8)+1; i > 0; i--){
            this.sezons.add(new Sezon());
        }
        typ = Typ.values()[random.nextInt(Typ.values().length)];
       // dealer.addProd(this);
    }

    public void showData(){
        System.out.println("Produkcje.Serial o: " + sezons.size()+" sezonach \nnazwa: " + this.getNazwa() +
                "\nOpis: " + this.getOpis() +
                "\nCena: " + this.getCena() +
                "\nData produkcji: " + this.getData().toString() +
                "\nOcena: " + this.getOcena());
        for(int i = 0 ; i < this.geteKraje().size(); i++){
            System.out.println(this.geteKraje().get(i));
        }
        for(int i = 0; i < sezons.size();i++){
            System.out.println("Sezon " + (i+1) + " ma " + sezons.get(i).getOdcinki().size() + " odcinkow");
        }

    }

    private class Sezon {
    private List<Odcinek> odcinki;

        public List<Odcinek> getOdcinki() {
            return odcinki;
        }

        public Sezon() {
        this.odcinki = new ArrayList<>();
        Random random = new Random();
        int pomoc = random.nextInt(20)+1;
        for(int i = 0; i < pomoc;i++){
            this.odcinki.add(new Odcinek());
        }
    }

    private class Odcinek {
        private int dlugosc;
        private Date premiera;

        public Odcinek() {
            Random random = new Random();
            this.dlugosc = random.nextInt(50) + 30;
            this.premiera = new Date(random.nextInt(30) + 1, random.nextInt(11) + 1, random.nextInt(100) + 2003);
        }
        public int getDlugosc() {
            return dlugosc;
        }
        public Date getPremiera() {
            return premiera;
        }
    }
}

    public Typ getTyp() {
        return typ;
    }

    public List<Sezon> getSezons() {
        return sezons;
    }

    public void setSezons(List<Sezon> sezons) {
        this.sezons = sezons;
    }

    @Override
    public String toString() {
        return "Produkcje.Serial o: " + sezons.size() + " sezonach";
    }
}
