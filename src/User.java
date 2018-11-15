import java.time.LocalDate;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class User implements Runnable {
    private String ID;
    private LocalDate birth;
    private String email;
    private String cardNumber;

    @Override
    public void run() {
        while(true) {
            System.out.println("Uzytkownik wyswietlany aktualnie to: " + ID + " " + birth + " " + email + " " + cardNumber);
            System.out.println("========================================");
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

        }
        }

    public User() {
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


    }
}
