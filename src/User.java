import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class User implements Runnable {
    private String ID;
    private LocalDate birth;
    private String email;
    private String cardNumber;
    private Abonament abonament;
    private List<Produkcja> kupioneFilmy;

    @Override
    public void run() {
        while(true) {
            System.out.println("Uzytkownik wyswietlany aktualnie to: " + ID + " " + birth + " " + email + " " + cardNumber + " " + abonament);
            System.out.println("Ma zakupione: ");
            for(Produkcja prod : kupioneFilmy){
                System.out.println(prod);
            }
            System.out.println("========================================");
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

        }
        }

    public User() {
        this.kupioneFilmy = new ArrayList<>();
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

    public void kupFilm(Produkcja prod){
        this.kupioneFilmy.add(prod);
        System.out.println("User " + this.toString() + "kupił: ");
        prod.showData();
    }

    public void kupAbonament(){
        int i = new Random().nextInt(3);
        this.abonament = Abonament.values()[i];
    }
}
