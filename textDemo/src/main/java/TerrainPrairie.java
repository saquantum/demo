public class TerrainPrairie extends Terrain {
    @Override
    public String describe() {
        return "a block of prairie. you can plant tree on this.";
    }

    @Override
    public String print() {
        return "prairie";
    }

    @Override
    public boolean plantable() {
        return true;
    }
}
