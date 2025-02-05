import java.util.Scanner;

public class Controller {
    private Status status;

    public Controller() {
        this.status = new Status();
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status s) {
        this.status = s;
    }

    public boolean standby(Scanner scanner){
        System.out.println("you are in the standby phase.");
        System.out.println("enter I to view your inventory;");
        System.out.println("enter E to proceed to next stage.");
        System.out.println("enter ESC to exit game.");
        String command = scanner.nextLine();
        if("ESC".equals(command)) {
            System.out.println("exit game.");
            this.getStatus().setStatusCode(Status.FINISHED);
            return false;
        }else if("I".equals(command)) {
            this.getStatus().printInventory();
        }else if("E".equals(command)) {
            System.out.println("to game!");
            this.getStatus().setStatusCode(Status.INGAME);
            this.getStatus().toIngame(GameMap.Stages.STAGE1);
            this.getStatus().addToInventory(new Tree());
            this.getStatus().addToInventory(new TreeSeed(2, new Tree()));
        }else{
            System.out.println("invalid command, please try again.");
        }
        return true;
    }

    public boolean proceedGame(Scanner scanner){
        if(this.getStatus().getCurrentTurn() > this.getStatus().getTotalTurns()){
            System.out.println("you have cleared current stage, all living plants will go to your inventory.");
            this.getStatus().setStatusCode(Status.STANDBY);
            return false;
        }

        System.out.println("you are in the game phase.");
        System.out.println("enter ESC to go back to standby phase.");
        System.out.println("enter I to view your inventory;");
        System.out.println("enter M to view the map status;");
        System.out.println("enter `C x y`to view the detail of one grid.");
        System.out.println("enter `P n x y` to plant the n-th item from your inventory to the cell at (x,y).");
        System.out.println("enter E to next turn.");
        System.out.println("turn " + this.getStatus().getCurrentTurn() + " in " + this.getStatus().getTotalTurns() + " turns.");
        String command = scanner.nextLine();
        if("ESC".equals(command)) {
            System.out.println("go to standby phase.");
            this.getStatus().setStatusCode(Status.STANDBY);
            return false;
        }else if("I".equals(command)) {
            this.getStatus().printInventory();
        }else if("M".equals(command)) {
            this.getStatus().printMap();
        }else if("E".equals(command)) {
            enemyTurn();
            System.out.println("next turn.");
            this.getStatus().nextTurn();
            this.getStatus().growPlants();
        }else if('C' == command.charAt(0)) {
            String[] seg = command.split(" ");
            if(seg.length != 3){
                System.out.println("the check command has received incorrect format. type `C 5 4` to try this.");
            }
            int x = 0, y = 0;
            try {
                x = Integer.parseInt(seg[1]) - 1;
                y = Integer.parseInt(seg[2]) - 1;
            } catch (NumberFormatException e) {
                System.out.println("the second or third argument of the check command is not an integer.");
                return true;
            }

            if(x < 0 || x >= this.getStatus().getMap().getSize() || y < 0 || y >= this.getStatus().getMap().getSize()){
                System.out.println("the index of the check command is out of range.");
                return true;
            }
            System.out.println("(" + (x+1) + ", " + (y+1) + ") ");
            System.out.println("plant: " + this.getStatus().getMap().describeGridCell(x,y));
            System.out.println("terrain: " + this.getStatus().getMap().describeTerrainCell(x,y));
        }else if('P' == command.charAt(0)) {
            if(this.getStatus().isInventoryEmpty()){
                System.out.println("your inventory is empty, cannot plant.");
                return true;
            }
            String[] seg = command.split(" ");
            if(seg.length != 4){
                System.out.println("the plant command has received incorrect format. type `P 1 5 6` to try this.");
                return true;
            }
            int n = 0, x = 0, y = 0;
            try {
                n = Integer.parseInt(seg[1]) - 1;
                x = Integer.parseInt(seg[2]) - 1;
                y = Integer.parseInt(seg[3]) - 1;
            } catch (NumberFormatException e) {
                System.out.println("the argument of the plant command is not an integer.");
                return true;
            }
            if(n < 0 || n >= this.getStatus().getInventory().size()){
                System.out.println("the index of the plant command is out of inventory range.");
                return true;
            }
            if(x < 0 || x >= this.getStatus().getMap().getSize() || y < 0 || y >= this.getStatus().getMap().getSize()){
                System.out.println("the index of the check command is out of map range.");
                return true;
            }
            Placable item = this.getStatus().getInventory().get(n);
            if(!this.getStatus().plantItem(item, x, y)){
                return true;
            }
            if(item instanceof TreeSeed){
                this.getStatus().getMap().insertToSeeds((TreeSeed)item, x, y);
            }
            this.getStatus().removeFromInventory(item);
            System.out.println("you have planted a " + item.print() + " at (" + (x+1) + ", " + (y+1) + ").");
        }
        else{
            System.out.println("invalid command, please try again.");
        }
        return true;
    }

    public void enemyTurn(){
        System.out.println("enemy's strategy. player cannot access anything except setting.");
    }
}
