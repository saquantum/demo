public class Storm implements Placable{

    private int countdown;
    private Pair direction;

    @Override
    public String describe() {
        return "a storm.";
    }

    @Override
    public String print() {
        return "storm";
    }

    public Storm(int countdown, Pair direction) {
        this.countdown = countdown;
        this.direction = direction;
    }

    public int getCountdown() {
        return countdown;
    }

    public void setCountdown(int countdown) {
        this.countdown = countdown;
    }

    public Pair getDirection() {
        return direction;
    }

    public void setDirection(Pair direction) {
        this.direction = direction;
    }

    public boolean countdown(){
        if(countdown == 0){
            return false;
        }
        countdown--;
        return true;
    }
}
