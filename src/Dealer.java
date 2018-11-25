import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dealer implements Runnable {
        private int cenaUslug;
        private String ID;
        private List<Film> stworzoneFilmy;
        private List<Serial> stworzoneSeriale;
        private List<Live> stworzoneLive;
        boolean canWork;

        @Override
        public void run() {

                while(true){
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    this.addProd();
                    if(!canWork){
                        System.out.println("Zakonczyłem prace");
                        break;
                    }
                }


        }

    public void setCanWork(boolean canWork) {
        this.canWork = canWork;
    }

    public Dealer() {
                this.stworzoneLive = new ArrayList<>();
                this.stworzoneSeriale = new ArrayList<>();
                this.stworzoneFilmy = new ArrayList<>();
                StringBuilder sb = new StringBuilder();
                for(int i = 0; i < 10; i++){
                        sb.append(new Random().nextInt(9));
                }
                this.ID = sb.toString();
                this.cenaUslug = new Random().nextInt(100)+50;
                canWork = true;
        }


       /* public void addProd(Produkcja prod){
            if(prod instanceof Film) this.stworzoneFilmy.add((Film)prod);
            else if(prod instanceof Serial) this.stworzoneSeriale.add((Serial) prod);
            else if(prod instanceof Live) this.stworzoneLive.add((Live) prod);
        }*/
        public Produkcja addProd(){
            int i;
            Random random = new Random();
            Produkcja temp;
            i = random.nextInt(3);
            switch (i){
                case 0:
                    temp = new Film(this) ;
                    this.stworzoneFilmy.add((Film)temp);
                    Main.addProdukcja(temp);
                    System.out.println("Stworzyłem film:" + temp);
                    return temp;
                case 1:
                    temp = new Serial(this);
                    this.stworzoneSeriale.add((Serial)temp);
                    Main.addProdukcja(temp);
                    System.out.println("Stworzyłem Serial:" + temp);
                    return temp;
                case 2:
                   temp = new Live(this);
                   this.stworzoneLive.add((Live)temp);
                    Main.addProdukcja(temp);
                    System.out.println("Stworzyłem Live:" + temp);
                   return temp;
                default:
                    System.out.println("coś poszło źle wylosowano: " + i);
                    return null;
            }

        }
        public void  stop(){
            System.out.println(this + " Kończy pracę.");
        }

        public int getCenaUslug() {
                return cenaUslug;
        }

        public String getID() {
                return ID;
        }

        public List<Produkcja> getStworzoneProdukcje(){
            List<Produkcja> prod = new ArrayList<>();
            for(Film film: stworzoneFilmy){
                prod.add(film);
            }
            for(Serial serial:stworzoneSeriale){
                prod.add(serial);
            }
            for(Live live:stworzoneLive){
                prod.add(live);
            }
            return prod;

        }

    public String toStringForDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.ID + " " + this.cenaUslug + "\n");
        if(stworzoneFilmy.size() > 0)
        for(int i = 0; i < this.stworzoneFilmy.size();i++){
            sb.append(this.stworzoneFilmy.get(i).toString() + "\n");
        }
        if(stworzoneLive.size() > 0)
        for(int i = 0; i < this.stworzoneLive.size();i++){
            sb.append(this.stworzoneLive.get(i).toString() + "\n");
        }
        if(stworzoneSeriale.size()>0)
        for(int i = 0; i < this.stworzoneSeriale.size();i++){
            sb.append(this.stworzoneSeriale.get(i).toString() + "\n");
        }

        return sb.toString();
    }
}
