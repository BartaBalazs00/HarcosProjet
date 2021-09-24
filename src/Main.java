import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Üdvözöllek az arénában!");

        Harcos viking = new Harcos("Viking", 1);
        Harcos lovag = new Harcos("Lovag", 2);
        Harcos oszman = new Harcos("Oszmán", 3);

        List<Harcos> harcosokLista = new ArrayList<>();

        harcosokLista.add(viking);
        harcosokLista.add(lovag);
        harcosokLista.add(oszman);

        Wait();
        System.out.println("Az ellenfeleid: ");
        for (Harcos harcos : harcosokLista) {
            Wait();
            System.out.println(harcos);
        }

        Wait();
        System.out.println("Mi legyen a neve a harcosodnak?");
        String bekertHarcosNev=sc.next();
        Wait();
        System.out.println("Milyen legyen a státuszsablona a harcosodnak?" +
                "\n1: alapEletero = 15, alapSebzes =3" +
                "\n2: alapEletero = 12, alapSebzes = 4" +
                "\n3: alapEletero = 8, alapSebzes = 5");

        boolean rendesenMegadtaStatuszt=false;
        int bekertStatuszSablonInt=4;
        String bekertStatuszSablonString="";
        while (!rendesenMegadtaStatuszt){
            bekertStatuszSablonString=sc.next();
            if(TryParseInt(bekertStatuszSablonString) != null){
                bekertStatuszSablonInt= Integer.parseInt(bekertStatuszSablonString);
                if (bekertStatuszSablonInt>3 || bekertStatuszSablonInt<1){
                    System.out.println("Nem jól adtad meg a számot!");
                }else{
                    rendesenMegadtaStatuszt=true;
                }
            }else {
                System.out.println("nem számot adtál meg!");
            }

        }

        //A felhasználónak a  harcosának a  létrehozása
        Harcos felhasznaloHarcosa = new Harcos(bekertHarcosNev, bekertStatuszSablonInt);

        //körök elkezdése
        int korSzama=1;
        boolean folytatja=true;
        while (folytatja){

            Wait();
            System.out.println("A te harcosod: "+ felhasznaloHarcosa.toString());

            System.out.println("Az ellenfelek: ");
            for (int i = 0; i < harcosokLista.size(); i++) {
                Wait();
                System.out.println((i+1)+": "+harcosokLista.get(i));
            }

            Wait();
            System.out.println("Mit szeretnél csinálni?");
            Wait();
            System.out.println("a.) Megküzdeni egy harcossal");
            Wait();
            System.out.println("b.) Gyógyulni");
            Wait();
            System.out.println("c.) Kilépni");
            char felhasznaloCsinal='d';
            boolean rendesenMegadtaMitCsinal=false;

            //rendesen megadja a felhasználó mit akar csinálni
            while(!rendesenMegadtaMitCsinal){
                felhasznaloCsinal=sc.next().toLowerCase().charAt(0);
                if(felhasznaloCsinal=='a' || felhasznaloCsinal=='b' || felhasznaloCsinal=='c'){
                    rendesenMegadtaMitCsinal=true;
                }
                else
                {
                    System.out.println("Nem adtad meg rendesen!");
                }
            }

            //ha harcolni szeretne
            if(felhasznaloCsinal=='a'){
                System.out.println("Hanyadik harcossal szeretnél megküzdeni?");
                //stringbe felvesszuk a válaszát,
                // majd ha azt lehet konvertálni akkor átrakjuk az értékét intbe egy másik változóba
                String hanyadikHarcossalKuzdString = "";
                int hanyadikHarcossalKuzdInt=0;
                boolean jolAdtamegASzamot=false;
                while (!jolAdtamegASzamot)
                {
                    hanyadikHarcossalKuzdString = sc.next();
                    if (TryParseInt(hanyadikHarcossalKuzdString) != null) {
                        hanyadikHarcossalKuzdInt = Integer.parseInt(hanyadikHarcossalKuzdString);
                        if(hanyadikHarcossalKuzdInt<harcosokLista.size()+1 && hanyadikHarcossalKuzdInt>0){
                            jolAdtamegASzamot=true;
                        }
                        else
                        {
                            System.out.println("Nem jó számot adtál meg");
                        }
                    }else{
                        System.out.println("Nem számot adtál meg!");
                    }
                }
                if(hanyadikHarcossalKuzdInt == 1){
                    Wait();
                    System.out.println("Az elso emberrel harcolsz");
                    felhasznaloHarcosa.megkuzd(harcosokLista.get(0));
                }
                else if(hanyadikHarcossalKuzdInt==2)
                {
                    Wait();
                    System.out.println("Az masodik emberrel harcolsz");
                    felhasznaloHarcosa.megkuzd(harcosokLista.get(1));
                }
                else
                {
                    Wait();
                    System.out.println("A harmadik emberrel harcolsz");
                    felhasznaloHarcosa.megkuzd(harcosokLista.get(2));
                }
            }
            //ha a felhasználó gyógyulni akar:
            else if(felhasznaloCsinal=='b')
            {
                felhasznaloHarcosa.gyogyul();
            }
            else if(felhasznaloCsinal=='c'){
                System.out.println("Megfutamodtál!! Gyáva!!");
                folytatja=false;
            }

            //Ha az egyik ellenfel meghal
            for (int i = 0; i < harcosokLista.size(); i++) {
                if(harcosokLista.get(i).getEletero()<0){
                    harcosokLista.remove(i);
                }
            }

            if(korSzama%3==0){
                if(harcosokLista.size()!=0){
                    int randomHarcos=(int)Math.round(Math.random()*(harcosokLista.size()-1));
                    Wait();
                    Wait();
                    System.out.println(randomHarcos+1 +". megtámadott téged");
                    harcosokLista.get(randomHarcos).megkuzd(felhasznaloHarcosa);
                    for (Harcos harcos : harcosokLista) {
                        harcos.gyogyul();
                    }
                }
            }
            //ha a felhasználó meghal
            if(felhasznaloHarcosa.getEletero()<0){
                System.out.println("Sajnos meghaltál");
                folytatja=false;
            }

            if(harcosokLista.size()==0){
                System.out.println("Legyőztad az összes ellenfeledet gratulálok!");
                folytatja=false;
            }


            korSzama+=1;
        }

    }
    public static Integer TryParseInt(String someText) {
        try {
            return Integer.parseInt(someText);
        } catch (NumberFormatException ex) {
            return null;
        }
    }
    public static void Wait(){
        try{
            Thread.sleep(1000);
        }catch(InterruptedException ex){
        }
    }
}
