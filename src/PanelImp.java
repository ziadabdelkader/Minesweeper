import javafx.util.Pair;

import java.util.*;

public class PanelImp implements Panel {
    private Integer numOfFlags;
    private Cell[][] cells;
    private Pair<Integer, Integer> selected;
    private GameStatus gameStatus;
    private Integer remainDigs;

    public PanelImp(Integer rows ,Integer cols,Integer numOfFlags){
        //default values
        this.numOfFlags = numOfFlags;
        cells = new CellImp[rows][cols];
        for(int i=0 ; i< rows  ; i++ )
            for(int j=0 ; j< cols ; j++)
                cells[i][j] = new CellImp();

        gameStatus = GameStatus.going;
        remainDigs = rows*cols-numOfFlags;

        //adding bombs
        Random r = new Random();
        int addedBombs = 0;
        while (addedBombs < numOfFlags){
            int row = r.nextInt(rows);
            int col = r.nextInt(cols);
            if(!cells[row][col].getBomb()){
                cells[row][col].setBomb(true);
                increaseAroundBomb(row,col);
                addedBombs++;
            }
        }
    }

    private void increaseAroundBomb(int row , int col){
        row--;
        col--;
        int d1 = cells.length;
        int d2 = cells[0].length;
        for(int i=0 ; i< 3 ;i++)
            for(int j=0 ;j < 3 ; j++)
                if(row+i < d1 && col+j < d2 && row+i>=0 && col+j>=0)
                    cells[row+i][col+j].setAroundBombs(cells[row+i][col+j].getAroundBombs()+1);
    }

    @Override
    public Integer getNumOfFlags() {
        return numOfFlags;
    }

    @Override
    public GameStatus getGameStatus() {
        return gameStatus;
    }

    @Override
    public void select(int row, int col) {
        selected = new Pair<>(row,col);
    }

    @Override
    public Boolean dig() {
        if(selected == null || gameStatus!=GameStatus.going)
            return false;

        Integer row = selected.getKey();
        Integer col = selected.getValue();
        Cell target = cells[row][col];

        if(target.dig()){
            if(target.getBomb())
                gameStatus = GameStatus.lose;
            else{
                remainDigs--;
                if(remainDigs == 0)
                    gameStatus = GameStatus.win;
            }
            cells[row][col] = target;
            if(gameStatus!=GameStatus.going)
                digAllBombs();
            return true;
        }
        return false;
    }

    @Override
    public Boolean flag() {
        if(selected == null || gameStatus!=GameStatus.going)
            return false;

        Integer row = selected.getKey();
        Integer col = selected.getValue();
        Cell target = cells[row][col];

        if(target.flag()){
            if(target.getStatus() == CellStatus.flag)
                numOfFlags--;
            else
                numOfFlags++;
            cells[row][col] = target;
            return true;
        }
        return false;
    }

    @Override
    public void printPanel() {
        for (Cell[] cell : cells){
            for (Cell value : cell){
                System.out.print(value.printCell()+" ");
            }
            System.out.println();
        }
    }

    @Override
    public Cell getCell(int row, int col) {
        return cells[row][col];
    }


    @Override
    public Pair<Integer, Integer> getSelectedIndex() {
        return selected;
    }


    private void digAllBombs(){
        for (Cell[] cell : cells)
            for (Cell value : cell)
                if(value.getBomb())
                    value.dig();
    }
}
