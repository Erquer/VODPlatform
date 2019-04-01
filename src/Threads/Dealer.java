package Threads;

import GUI.Main;
import Produkcje.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dealer implements Runnable, Serializable {
        private int cenaUslug;
        private String ID;
        private List<Produkcja> stworzoneFilmy;
        private  transient LocalDate lastPayment;

    public void setLastPayment(LocalDate lastPayment) {
        this.lastPayment = lastPayment;
    }

    public LocalDate getLastPayment() {
        return lastPayment;
    }

    private transient boolean canWork;

        @Override
        public void run() {

            while (Main.isCanDealerWork()) {
                if (canWork) {
                    if(canMakeProduction())
                                this.addProd();
                    Main.payToDealer(this);
                   // System.out.println("stworzyłem produkcje");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }else if (!canWork) {
                    this.stworzoneFilmy.removeAll(this.stworzoneFilmy);
                    System.out.println("Teoretycznie usunąłem wszystko." + this.stworzoneFilmy.size());
                    break;
                }

        }


        }

    public void setCanWork(boolean canWork) {
        this.canWork = canWork;
    }

    public Dealer() {
                this.stworzoneFilmy = new ArrayList<>();
                StringBuilder sb = new StringBuilder();
                this.lastPayment = null;
                for(int i = 0; i < 10; i++){
                        sb.append(new Random().nextInt(9));
                }
                this.ID = sb.toString();
                this.cenaUslug = new Random().nextInt(100)+50;
                canWork = true;
        }



        public Produkcja addProd(){
            int i;
            Random random = new Random();
            Produkcja temp;
            i = random.nextInt(3);
            switch (i){
                case 0:
                    temp = new Film(this) ;
                    this.stworzoneFilmy.add(temp);
                    Main.addProdukcja(temp);
                    return temp;
                case 1:
                    temp = new Serial(this);
                    this.stworzoneFilmy.add(temp);
                    Main.addProdukcja(temp);
                    return temp;
                case 2:
                   temp = new Live(this);
                   this.stworzoneFilmy.add(temp);
                    Main.addProdukcja(temp);
                   return temp;
                default:
                    System.out.println("coś poszło źle, wylosowano: " + i);
                    return null;
            }

        }
        public void  stop(){
            System.out.println(this + " Kończy pracę.");
            canWork = false;
        }

        public int getCenaUslug() {
                return cenaUslug;
        }

        public String getID() {
                return ID;
        }

        public List<Produkcja> getStworzoneProdukcje(){
            return this.stworzoneFilmy;
        }

    public String toStringForDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.ID + " " + this.cenaUslug + "\n");
        if(stworzoneFilmy.size() > 0)
        for(int i = 0; i < this.stworzoneFilmy.size();i++){
            sb.append(this.stworzoneFilmy.get(i).toString() + "\n");
        }

        return sb.toString();
    }

    private boolean canMakeProduction(){
            int prodSize;
            int userSize;
            prodSize = Main.getFilmy().size();
            userSize = Main.getUsers().size();
            if(prodSize/4 < userSize && userSize > 0) return true;
            else return false;
    }

}
