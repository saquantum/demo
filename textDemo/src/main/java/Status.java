import java.util.LinkedList;

public class Status {
    private int statusCode;

    protected static final int START = 1;
    protected static final int STANDBY = 2;
    protected static final int INGAME = 4;
    protected static final int FINISHED = 8;

    private LinkedList<Placable> inventory = new LinkedList<>();
    private LinkedList<Integer> inventoryCounts = new LinkedList<>();

    private GameMap map;
    private GameMap.Stages currentStage;

    private int currentTurn;
    private int totalTurns;

    private static final int[] STAGETURNS = {10};

    public int getStatusCode() {
        return statusCode;
    }
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public GameMap getMap() {
        return map;
    }

    public void setMap(GameMap map) {
        this.map = map;
    }

    public LinkedList<Placable> getInventory() {
        return inventory;
    }

    public void setInventory(LinkedList<Placable> inventory) {
        this.inventory = inventory;
    }

    public LinkedList<Integer> getInventoryCounts() {
        return inventoryCounts;
    }

    public void setInventoryCounts(LinkedList<Integer> inventoryCounts) {
        this.inventoryCounts = inventoryCounts;
    }

    public GameMap.Stages getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(GameMap.Stages currentStage) {
        this.currentStage = currentStage;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(int currentTurn) {
        this.currentTurn = currentTurn;
    }

    public int getTotalTurns() {
        return totalTurns;
    }

    public void setTotalTurns(int totalTurns) {
        this.totalTurns = totalTurns;
    }

    public boolean isInventoryEmpty(){
        return inventory.isEmpty();
    }

    public void toStandby(){
        this.statusCode = STANDBY;
        this.currentTurn = 0;
        this.totalTurns = 0;
    }

    public void toIngame(GameMap.Stages stage){
        this.statusCode = INGAME;
        this.currentStage = stage;
        this.currentTurn = 1;
        this.totalTurns = Status.STAGETURNS[stage.ordinal()];
        this.map = new GameMap(stage);
    }

    public void addToInventory(Placable item){
        for (int i = 0; i < this.inventory.size(); i++) {
            if (this.inventory.get(i).getClass() == item.getClass()) {
                this.inventoryCounts.set(i, this.inventoryCounts.get(i) + 1);
                return;
            }
        }
        this.inventory.add(item);
        this.inventoryCounts.add(1);
    }

    public boolean removeFromInventory(Placable item){
        for (int i = 0; i < this.inventory.size(); i++) {
            if (this.inventory.get(i).getClass() == item.getClass()) {
                this.inventoryCounts.set(i, this.inventoryCounts.get(i) - 1);
                if (this.inventoryCounts.get(i) == 0) {
                    this.inventory.remove(i);
                    this.inventoryCounts.remove(i);
                }
                return true;
            }
        }
        return false;
    }

    public void printInventory(){
        if(this.inventory == null || this.inventory.isEmpty()){
            System.out.println("your inventory is empty.");
            return;
        }
        System.out.print("you have ");

        for (int i = 0; i < this.inventory.size(); i++) {
            System.out.print(inventoryCounts.get(i) + " " + inventory.get(i).print() + ", ");
        }
        System.out.println("in your inventory.");
    }

    private boolean hasItemInInventory(Placable item){
        for (int i = 0; i < this.inventory.size(); i++) {
            if (this.inventory.get(i).equals(item)) {
                return true;
            }
        }
        return false;
    }

    public boolean plantItem(Placable item, int x, int y){
        if(!this.hasItemInInventory(item)){
            return false;
        }
        if(!this.map.placeItem(item,x,y)){
            return false;
        }
        return true;
    }

    public void nextTurn(){
        this.currentTurn++;
    }

    public void printMap(){
        map.printGrid(true);
    }

    public void growPlants(){
        for (GameMap.TreeSeedInfo seed : map.getSeeds()) {
            Placable tree = seed.seed.getPlant();
            if(tree != null){
                map.forcePlaceItem(tree, seed.x, seed.y);
            }
        }
    }
}
