package edu.uob;

import java.io.Serial;
import java.io.Serializable;

public class OXOController implements Serializable {
    @Serial private static final long serialVersionUID = 1;
    OXOModel gameModel;

    public OXOController(OXOModel model) {
        gameModel = model;
    }

    public void handleIncomingCommand(String command) throws OXOMoveException {
        // 0. if someone has won, reject any more commands
        if(gameModel.getWinner() != null){
            return;
        }

        // 1. verify current command is valid and within grids bound
        if(command == null || command.length() != 2){
            int len = command != null ? command.length() : 0;
            throw new OXOMoveException.InvalidIdentifierLengthException(len);
        }
        if(!command.matches("(?i)[a-z]\\d")){
            if(String.valueOf(command.charAt(0)).matches("(?i)[a-z]")){
                throw new OXOMoveException.InvalidIdentifierCharacterException(OXOMoveException.RowOrColumn.ROW, command.charAt(0));
            }else{
                throw new OXOMoveException.InvalidIdentifierCharacterException(OXOMoveException.RowOrColumn.COLUMN, command.charAt(1));
            }
        }
        int col = gameModel.getNumberOfColumns();
        int row = gameModel.getNumberOfRows();
        int r = command.toLowerCase().charAt(0) - 'a';
        int c = command.charAt(1) - '1';
        if(r >= row || r < 0){
            throw new OXOMoveException.OutsideCellRangeException(OXOMoveException.RowOrColumn.ROW,r+1);
        }
        if(c >= col || c < 0 ){
            throw new OXOMoveException.OutsideCellRangeException(OXOMoveException.RowOrColumn.COLUMN,c+1);
        }

        // 2. verify the demanding cell is unoccupied
        if(gameModel.getCellOwner(r,c) != null){
            throw new OXOMoveException.CellAlreadyTakenException(r+1, c+1);
        }

        // 3. set unoccupied cell to current player
        gameModel.setCellOwner(r, c, gameModel.getPlayerByNumber(gameModel.getCurrentPlayerNumber()));

        // 4. detect and set winner, if no one wins, decrease counter,
        // once counter goes to 0, game is drawn
        gameModel.detectWinner(r, c);
        gameModel.decrementUnoccupiedCells();
        System.out.println("unoccupied cells = " + gameModel.getUnoccupiedCells());
        if(gameModel.getUnoccupiedCells() == 0){
            gameModel.setGameDrawn(true);
        }

        // 5. shift player
        gameModel.setCurrentPlayerNumber((gameModel.getCurrentPlayerNumber()+1)%gameModel.getNumberOfPlayers());
    }

    public void addRow() {
        gameModel.addNewRow();
    }
    public void removeRow() {
        gameModel.removeLastRow();
    }
    public void addColumn() {
        gameModel.addNewColumn();
    }
    public void removeColumn() {
        gameModel.removeLastColumn();
    }

    public void increaseWinThreshold() {
        gameModel.increaseWinThreshold();
    }
    public void decreaseWinThreshold() {
        gameModel.decreaseWinThreshold();
    }

    public void addAnotherPlayer(){
        gameModel.addAnotherPlayer();
    }

    public void removePlayer(){
        gameModel.removePlayer();
    }

    public void reset() {
        gameModel.setWinner(null);
        gameModel.setGameDrawn(false);
        for(int i = 0; i < gameModel.getNumberOfRows(); i++){
            for(int j = 0; j < gameModel.getNumberOfColumns(); j++){
                gameModel.setCellOwner(i, j, null);
            }
        }
        gameModel.setUnoccupiedCells(gameModel.getNumberOfColumns() * gameModel.getNumberOfRows());
        gameModel.setCurrentPlayerNumber(0);
    }
}
