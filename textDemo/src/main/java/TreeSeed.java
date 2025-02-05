public class TreeSeed implements Placable, PlantSeed{
    private int totalGrowTurns;
    private int turnsToGrow;
    private Placable plant = new Tree();

    public TreeSeed(int totalGrowTurns, Placable plant) {
        this.totalGrowTurns = totalGrowTurns;
        this.turnsToGrow = totalGrowTurns;
        this.plant = plant;
    }

    @Override
    public String describe() {
        return "a seed of the tree. " + this.turnsToGrow + " turns to grow up";
    }

    @Override
    public String print() {
        return "seed";
    }

    @Override
    public int getTotalGrowTurns() {
        return this.totalGrowTurns;
    }

    @Override
    public void setTotalGrowTurns(int totalGrowTurns) {
        this.totalGrowTurns = totalGrowTurns;
    }

    @Override
    public int getTurnsToGrow() {
        return this.turnsToGrow;
    }

    @Override
    public void setTurnsToGrow(int turnsToGrow) {
        this.turnsToGrow = turnsToGrow;
    }

    @Override
    public Placable getPlant() {
        if(this.turnsToGrow != 0){
            this.turnsToGrow--;
        }
        if(this.turnsToGrow == 0){
            return this.plant;
        }else{
            return null;
        }
    }
}
