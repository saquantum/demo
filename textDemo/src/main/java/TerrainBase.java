public class TerrainBase extends Terrain{
    @Override
    public String describe() {
        return "player's home.";
    }

    @Override
    public String print() {
        return "home";
    }

    @Override
    public boolean plantable() {
        return false;
    }
}
