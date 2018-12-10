package Produkcje;

import Enums.Aktorzy;
import Enums.Kraje;
import GUI.Main;
import Threads.Dealer;
import javafx.scene.image.Image;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Live extends Produkcja {
//private boolean promocja;
    private LocalDate liveStream;

    public LocalDate getLiveStream() {
        return liveStream;
    }

    public void setLiveStream(LocalDate liveStream) {
        this.liveStream = liveStream;
    }

    public Live(Dealer dealer) {
        super(dealer);
        LocalDate tempData;
        Random random = new Random();
        long minDay = Main.getDate().toEpochDay();
        long maxDay = Main.getDate().plusWeeks(random.nextInt(7)+3).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay,maxDay);
        tempData = LocalDate.ofEpochDay(randomDay);
        this.liveStream= tempData;

    }

    public Live(Image obrazek, String nazwa, String opis, LocalDate data, Dealer dealer, List<Kraje> eKraje, List<Aktorzy> obsada, double ocena, int cena) {
        super(obrazek, nazwa, opis, data, dealer, eKraje, obsada, ocena, cena);
    }
}
