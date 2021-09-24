public class Harcos {

    private String nev;
    private int szint;
    private int tapasztalat;
    private int eletero;
    private int alapEletero;
    private int alapSebzes;

    public Harcos(String nev, int statuszSablon) {
        this.nev = nev;
        this.szint = 1;
        this.tapasztalat = 0;
        if(statuszSablon==1)
        {
            this.alapEletero = 15;
            this.alapSebzes = 3;
        }
        else if(statuszSablon==2)
        {
            this.alapEletero = 12;
            this.alapSebzes = 4;
        }
        else if(statuszSablon==3)
        {
            this.alapEletero = 8;
            this.alapSebzes = 5;
        }
        this.eletero=this.getMaxEletero();
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public int getSzint() {
        return szint;
    }

    public void setSzint(int szint) {
        this.szint = szint;
    }

    public int getTapasztalat() {
        return tapasztalat;
    }

    public void setTapasztalat(int tapasztalat) {
        //Ha tapasztalat eléri a szintlépéshez szükséges tapasztalatot, akkor a harcos szintjét növelje 1-el
        if(getSzintLepeshez()<=tapasztalat){
            tapasztalat=getSzintLepeshez()-tapasztalat;
            setSzint(getSzint()+1);
            Wait();
            System.out.println(this.getNev() +" szintet lépett!");
            setEletero(getMaxEletero());
        }
        this.tapasztalat = tapasztalat;
    }

    public int getEletero() {
        return eletero;
    }

    public void setEletero(int eletero) {

        // ha nagyobb az életerő mint az alap akkor az alap legyen
        if(eletero>this.getMaxEletero()){
            eletero=this.getMaxEletero();
        }

        //ha kissebb mint nulla az eleterő akkor a tapasztalat elveszik
        if(eletero<0){
            setTapasztalat(0);
        }

        this.eletero = eletero;
    }

    public int getAlapEletero() {
        return alapEletero;
    }


    public int getAlapSebzes() {
        return alapSebzes;
    }

    public int getSebzes(){
        return getAlapSebzes()+getSzint();
    }
    public int getSzintLepeshez(){
        return 10+getSzint()*5;
    }
    public int getMaxEletero(){
        return getAlapEletero()+getSzint()*3;
    }

    public void megkuzd(Harcos masikharcos){
        Boolean vanHiba=false;
        if(masikharcos.getNev() == this.getNev()){
            System.out.println("A parameterül adott harcos megegyezik azzal a harcossal akin az eljárást végrehajtjuk");
            vanHiba=true;
        }
        if(masikharcos.getEletero()<1 || this.getEletero()<1){
            System.out.println("Valamelyik harcos már halott");
            vanHiba=true;
        }
        if(!vanHiba){
            //kihívó támad először:
            //nem biztos hogy jó
            Wait();
            System.out.println(this.getNev()+" megtámadja: "+masikharcos.getNev());
            masikharcos.setEletero(masikharcos.getEletero()-this.getSebzes());

            //ha nem hal meg akkor visszatámad
            if (masikharcos.getEletero()>0){
                Wait();
                System.out.println(masikharcos.getNev()+" visszatámad");
                this.setEletero(this.getEletero()-masikharcos.getSebzes());
            }

            //ha nem haltak meg kapnak 5 tapasztalati pontot
            if(this.getEletero()>0){
                Wait();
                System.out.println(this.getNev()+ " +5xpt kapott");
                this.setTapasztalat(this.getTapasztalat()+5);
            }
            if(masikharcos.getEletero()>0){
                System.out.println(masikharcos.getNev()+ " +5xpt kapott");
                masikharcos.setTapasztalat(masikharcos.getTapasztalat()+5);
            }

            //ha valamelyik megöli a másikat +10 tapasztalati pont
            if(this.getEletero()<1){
                Wait();
                System.out.println(masikharcos.getNev()+" megölte "+this.getNev());
                System.out.println(masikharcos.getNev()+" +10xpt kapott");
                masikharcos.setTapasztalat(masikharcos.getTapasztalat()+10);
            }
            if(masikharcos.getEletero()<1){
                Wait();
                System.out.println(this.getNev()+" megölte "+masikharcos.getNev());
                System.out.println(this.getNev()+" +10xpt kapott");
                this.setTapasztalat(this.getTapasztalat()+10);
            }
        }
    }

    public void gyogyul(){
        if(this.getEletero()==0){
            this.setEletero(getMaxEletero());
        }
        else
        {
            this.setEletero(getEletero()+3+getSzint());
        }
    }
    public static void Wait(){
        try{
            Thread.sleep(1000);
        }catch(InterruptedException ex){
        }
    }
    @Override
    public String toString() {
        return nev +
                " - LVL:" + szint +
                " - EXP:" + tapasztalat+"/"+getSzintLepeshez() +
                " - HP:" + eletero+"/"+getMaxEletero() +
                " - DMG:" + getSebzes();
    }
}