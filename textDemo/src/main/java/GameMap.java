import java.util.ArrayList;

public class GameMap {
    private Placable[][] grid;
    private Terrain[][] terrains;

    protected class TreeSeedInfo{
        public TreeSeed seed;
        public int x;
        public int y;
        public TreeSeedInfo(TreeSeed seed, int x, int y){
            this.seed = seed;
            this.x = x;
            this.y = y;
        }
    }

    private ArrayList<TreeSeedInfo> seeds = new ArrayList<>();

    protected class EnemyInfo{
        public Placable enemy;
        public int x;
        public int y;
        public EnemyInfo(Placable enemy, int x, int y){
            this.enemy = enemy;
            this.x = x;
            this.y = y;
        }
    }

    private ArrayList<EnemyInfo> enemies = new ArrayList<>();

    public enum Stages {STAGE1}

    public GameMap(Stages stage) {
        if (stage == Stages.STAGE1) {
            this.initGameStage1();
        }
    }

    public ArrayList<TreeSeedInfo> getSeeds() {
        return seeds;
    }

    public void insertToSeeds(TreeSeed seed, int x, int y) {
        this.seeds.add(new TreeSeedInfo(seed, x, y));
    }

    public ArrayList<EnemyInfo> getEnemies() {
        return enemies;
    }

    public void insertToEnemies(Placable enemy, int x, int y) {
        this.enemies.add(new EnemyInfo(enemy, x, y));
    }

    public int getSize() {
        return grid.length;
    }

    public Placable getCell(int x, int y) {
        return grid[x][y];
    }

    public void initGrid(int size) {
        this.grid = new Placable[size][size];
    }

    private void initTerrain(int size) {
        this.terrains = new Terrain[size][size];
    }

    public boolean placeItem(Placable item, int x, int y) {
        // x and y starts from 0
        if (grid[x][y] != null || !terrains[x][y].plantable()) {
            System.out.println("this grid is occupied or cannot plant on it.");
            return false;
        }
        this.grid[x][y] = item;
        return true;
    }

    public void forcePlaceItem(Placable item, int x, int y) {
        // x and y starts from 0
        this.grid[x][y] = item;
    }

    private boolean changeTerrain(Terrain terrain, int x, int y) {
        if (x < 0 || y < 0 || x >= terrains.length || y >= terrains[0].length) {
            return false;
        }
        this.terrains[x][y] = terrain;
        return true;
    }

    private void initGameStage1() {
        // set size of grid and terrain
        this.initGrid(8);
        this.initTerrain(8);

        // set terrains
        for (int i = 0; i < this.terrains.length; i++) {
            for (int j = 0; j < this.terrains[0].length; j++) {
                this.terrains[i][j] = new TerrainPrairie();
            }
        }
        this.changeTerrain(new TerrainBase(), 4, 4);
        this.changeTerrain(new TerrainMountain(), 4, 3);
    }

    public String printGridCell(int x, int y) {
        if (grid[x][y] == null) {
            return "empty";
        } else {
            return grid[x][y].print();
        }
    }

    public String printTerrainCell(int x, int y) {
        if (terrains[x][y] == null) {
            return "empty";
        } else {
            return terrains[x][y].print();
        }
    }

    public String describeGridCell(int x, int y) {
        if (grid[x][y] == null) {
            return "empty";
        } else {
            return grid[x][y].describe();
        }
    }

    public String describeTerrainCell(int x, int y) {
        if (terrains[x][y] == null) {
            return "empty";
        } else {
            return terrains[x][y].describe();
        }
    }

    public void printGrid(boolean combine) {
        if (!combine) {
            System.out.println("Plants: ");
            for (int i = 0; i < grid.length; i++) {
                System.out.print("[ ");
                for (int j = 0; j < grid[0].length; j++) {
                    System.out.print(printGridCell(i, j) + " , ");
                }
                System.out.println(" ]");
            }

            System.out.println("Terrains: ");
            for (int i = 0; i < terrains.length; i++) {
                System.out.print("[ ");
                for (int j = 0; j < terrains[0].length; j++) {
                    System.out.print(printTerrainCell(i, j) + " , ");
                }
                System.out.println(" ]");
            }
        } else {
            for (int i = 0; i < grid.length; i++) {
                System.out.print("[ ");
                for (int j = 0; j < grid[0].length; j++) {
                    if (grid[i][j] == null){
                        System.out.print(terrains[i][j].print());
                    }else{
                        System.out.print(grid[i][j].print());
                }
                    System.out.print(", ");
                }
                System.out.println(" ]");
            }
        }
    }
}
