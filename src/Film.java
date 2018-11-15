import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Film extends Produkcja {
    private boolean promocja;
    private Typ typ;
    private List<String> linki;
    private int dataWaznosci;

    public Film(Dealer dealer) {
        super(dealer);
        this.linki = new ArrayList<>();
        Random random = new Random();
        this.promocja = false;
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
        this.dataWaznosci = random.nextInt(3)+1;

    }

    public void showData(){
        System.out.println("Film: \n"+
                "Tytuł: " +this.getNazwa() +
                "\nOpis: " + this.getOpis() +
                "\nCena: " + this.getCena() +
                "\nData premiery: " + this.getData() +
                "\nOcena: " + this.getOcena()+ "\nLinki do zwiastunów: ");

        for(int i = 0; i < linki.size();i++){
            System.out.println(linki.get(i));
        }
        System.out.println("Kraje produkcji: ");
        for(int i = 0; i < this.geteKraje().size();i++){
            System.out.println(this.geteKraje().get(i));
        }

    }


}
