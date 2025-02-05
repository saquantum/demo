public class TerrainDeserted extends Terrain {

    @Override
    public String describe() {
        return "a deserted place.";
    }

    @Override
    public String print() {
        return "deserted";
    }

    @Override
    public boolean plantable() {
        return false;
    }
}
