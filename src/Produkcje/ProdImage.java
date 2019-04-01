package Produkcje;

import javafx.scene.image.Image;

import java.io.Serializable;

public class ProdImage extends Image implements Serializable {
    Produkcja produkcja;
    public ProdImage(String url, Produkcja produkcja) {
        super(url);
        this.produkcja = produkcja;
    }

    public Produkcja getProdukcja() {
        return produkcja;
    }
}
