package edu.uob;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class OXOModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1;
    private ArrayList<ArrayList<OXOPlayer>> cells;
    private OXOPlayer[] players;
    private int currentPlayerNumber;
    private OXOPlayer winner;
    private boolean gameDrawn;
    private int winThreshold;
    private int unoccupiedCells;

    public OXOModel(int numberOfRows, int numberOfColumns, int winThresh) {
        this.winThreshold = winThresh;
        this.cells = new ArrayList<>();
        for (int i = 0; i < numberOfRows; i++) {
            this.cells.add(new ArrayList<>());
            for (int j = 0; j < numberOfRows; j++) {
                this.cells.get(i).add(null);
            }
        }
        this.unoccupiedCells = numberOfRows * numberOfColumns;
        this.players = new OXOPlayer[2];
    }

    public int getNumberOfPlayers() {
        return this.players.length;
    }

    public void addPlayer(OXOPlayer player) {
        for (int i = 0; i < players.length; i++) {
            if (players[i] == null) {
                players[i] = player;
                return;
            }
        }
    }

    public OXOPlayer getPlayerByNumber(int number) {
        return players[number];
    }

    public OXOPlayer getWinner() {
        return this.winner;
    }

    public void setWinner(OXOPlayer player) {
        this.winner = player;
    }

    public int getCurrentPlayerNumber() {
        return this.currentPlayerNumber;
    }

    public void setCurrentPlayerNumber(int playerNumber) {
        this.currentPlayerNumber = playerNumber;
    }

    public int getNumberOfRows() {
        return this.cells.size();
    }

    public int getNumberOfColumns() {
        return this.cells.get(0).size();
    }

    public OXOPlayer getCellOwner(int rowNumber, int colNumber) {
        return this.cells.get(rowNumber).get(colNumber);
    }

    public void setCellOwner(int rowNumber, int colNumber, OXOPlayer player) {
        this.cells.get(rowNumber).set(colNumber, player);
    }

    public void setWinThreshold(int winThresh) {
        this.winThreshold = winThresh;
    }

    public int getWinThreshold() {
        return this.winThreshold;
    }

    public void setGameDrawn(boolean isDrawn) {
        this.gameDrawn = isDrawn;
    }

    public boolean isGameDrawn() {
        return this.gameDrawn;
    }

    public void decrementUnoccupiedCells() {
        this.unoccupiedCells--;
    }

    public int getUnoccupiedCells() {
        return this.unoccupiedCells;
    }

    public void setUnoccupiedCells(int unoccupiedCells) {
        this.unoccupiedCells = unoccupiedCells;
    }

    public void addNewRow() {
        if (this.cells.size() >= 9 || this.getWinner() != null) {
            return;
        }
        this.cells.add(new ArrayList<>());
        for (int i = 0; i < this.cells.get(0).size(); i++) {
            this.cells.get(this.cells.size() - 1).add(null);
        }
        this.unoccupiedCells += this.cells.get(0).size();
    }

    private boolean isEmptyRow(int row) {
        for (OXOPlayer player : this.cells.get(row)) {
            if (player != null) {
                return false;
            }
        }
        return true;
    }

    public void removeLastRow() {
        if (this.cells.size() <= 3 || this.getWinner() != null) {
            return;
        }
        if (!isEmptyRow(this.cells.size() - 1)) {
            return;
        }
        this.cells.remove(this.cells.size() - 1);
        this.unoccupiedCells -= this.cells.get(0).size();
    }

    public void addNewColumn() {
        if (this.cells.get(0).size() >= 9 || this.getWinner() != null) {
            return;
        }
        for (ArrayList<OXOPlayer> cell : this.cells) {
            cell.add(null);
        }
        this.unoccupiedCells += this.cells.size();
    }

    private boolean isEmptyColumn(int col) {
        for (ArrayList<OXOPlayer> cell : this.cells) {
            if (cell.get(col) != null) {
                return false;
            }
        }
        return true;
    }

    public void removeLastColumn() {
        if (this.cells.get(0).size() == 3 || this.getWinner() != null) {
            return;
        }
        if (!isEmptyColumn(this.cells.get(0).size() - 1)) {
            return;
        }
        for (ArrayList<OXOPlayer> cell : this.cells) {
            cell.remove(cell.size() - 1);
        }
        this.unoccupiedCells -= this.cells.size();
    }

    public void detectWinner(int row, int col) {
        System.out.println("rol = " + row + ", col = " + col);
        OXOPlayer currentPlayer = this.getPlayerByNumber(this.currentPlayerNumber);
        // an array for the directions
        class XYPair {
            int x, y;
            XYPair(int x, int y) {
                this.x = x;
                this.y = y;
            }
        }
        XYPair[] directions = {new XYPair(-1, 0), new XYPair(0, -1),
                new XYPair(-1, -1), new XYPair(-1, 1)};
        // loop the directions to achieve auto win check
        for (XYPair direction : directions) {
            int dx = direction.x;
            int dy = direction.y;
            if(inConsecutive(row,col,dx,dy,currentPlayer)) {
                this.setWinner(currentPlayer);
                return;
            }
        }
    }

    private boolean inConsecutive(int row, int col, int dx, int dy, OXOPlayer currentPlayer) {
        int count = 0;
        int offset = this.getWinThreshold() - 1;
        for (int i = -offset; i <= offset; i++) {
            if (!isInBound(row + i * dx, col + i * dy)) {
                continue;
            }
            if (currentPlayer.equals(this.getCellOwner(row + dx * i, col + dy * i))){
                count++;
            }else{
                count = 0;
            }
            if(count == this.getWinThreshold()){
                return true;
            }
        }
        return false;
    }

    private boolean isInBound(int row, int col) {
        if (row < 0 || col < 0) {
            return false;
        }
        if (row >= this.getNumberOfRows() || col >= this.getNumberOfColumns()) {
            return false;
        }
        return true;
    }

    public void increaseWinThreshold() {
        // cannot increase after game won
        if(this.getWinner() != null) {
            return;
        }
        // win threshold smaller than size
        if(this.getWinThreshold() >= Math.min(this.getNumberOfColumns(), this.getNumberOfRows())) {
            return;
        }
        System.out.println("increaseWinThreshold");
        this.setWinThreshold(this.getWinThreshold() + 1);
    }
    public void decreaseWinThreshold() {
        // minimum threshold 3
        if(this.getWinThreshold() == 3) {
            return;
        }
        // cannot reduce threshold during a game
        if(this.getUnoccupiedCells() != this.getNumberOfColumns() * this.getNumberOfRows()) {
            return;
        }
        System.out.println("decreaseWinThreshold");
        this.setWinThreshold(this.getWinThreshold() - 1);
    }

    private static Map<Integer, Character> chars = new HashMap<>();
    static{
        chars.put(1,'x');
        chars.put(2,'o');
    }

    private static char newPlayerChar(){
        int size = chars.size();
        Random rand = new Random();
        while (true) {
            char c = (char) rand.nextInt(33, 126);
            if(Character.isDigit(c) || Character.isLetter(c)){
                chars.put(chars.size() + 1, c);
                if(chars.size() != size) {
                    return c;
                }
            }
        }
    }

    public void addAnotherPlayer(){
        // cannot increase during a game
        if(this.getUnoccupiedCells() != this.getNumberOfColumns() * this.getNumberOfRows()) {
            return;
        }
        // number of player should be smaller than grid size
        if(this.getNumberOfPlayers() >= Math.min(this.getNumberOfColumns(), this.getNumberOfRows())){
            return;
        }

        char c = OXOModel.newPlayerChar();
        System.out.println("another player with character " + c);
        this.players = Arrays.copyOf(this.players, this.players.length + 1);
        this.addPlayer(new OXOPlayer(c));
    }

    public void removePlayer() {
        // minimum 2 players
        if(this.getNumberOfPlayers() == 2) {
            return;
        }
        // cannot reduce threshold during a game
        if(this.getUnoccupiedCells() != this.getNumberOfColumns() * this.getNumberOfRows()) {
            return;
        }
        System.out.println("remove last player");
        this.setWinThreshold(this.getWinThreshold() - 1);
        this.players = Arrays.copyOf(this.players, this.getNumberOfRows() - 1);
    }

}
