import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dealer implements Runnable {
        private int cenaUslug;
        private String ID;
        List<Produkcja> stworzoneProdukcje;

        @Override
        public void run() {

        }

        public Dealer() {
                this.stworzoneProdukcje = new ArrayList<>();
                StringBuilder sb = new StringBuilder();
                for(int i = 0; i < 10; i++){
                        sb.append(new Random().nextInt(9));
                }
                this.ID = sb.toString();
                this.cenaUslug = new Random().nextInt(100)+50;
        }
        public void addProd(Produkcja prod){
                this.stworzoneProdukcje.add(prod);
        }

        public int getCenaUslug() {
                return cenaUslug;
        }

        public String getID() {
                return ID;
        }

        public List<Produkcja> getStworzoneProdukcje() {
                return stworzoneProdukcje;
        }
}
