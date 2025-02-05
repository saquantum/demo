public class TerrainMountain extends Terrain{
    @Override
    public String describe() {
        return "a mountain which stops storm.";
    }

    @Override
    public String print() {
        return "mountain";
    }

    @Override
    public boolean plantable() {
        return false;
    }
}
