import java.util.Scanner;

public class Game {
    private Controller controller;

    public static void main(String[] args) {
        new Game();
    }

    public Game() {
        controller = new Controller();
        Scanner scanner = new Scanner(System.in);
        inputHandler(scanner);
    }

    private void inputHandler(Scanner scanner){
        System.out.println("test game has started.");
        System.out.println("-----------------");
        this.controller.getStatus().setStatusCode(Status.STANDBY);
        while(this.controller.getStatus().getStatusCode() != Status.FINISHED) {
            while (this.controller.getStatus().getStatusCode() == Status.STANDBY) {
                if (!this.controller.standby(scanner)) {
                    break;
                }
            }
            while(this.controller.getStatus().getStatusCode() == Status.INGAME) {
                if(!this.controller.proceedGame(scanner)){
                    break;
                }
            }
        }
    }
}
