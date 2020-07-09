import javafx.scene.Node;
import javafx.scene.control.Button;
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
        List<Cell> neighbours = getNeighbours(row,col);
        for (Cell nei : neighbours){
            nei.setAroundBombs(nei.getAroundBombs() +1);
        }

    }

    private List<Cell> getNeighbours(int row,int col){
        // return the cell[row][col] along with its neighbours
        List<Cell> neighbours = new ArrayList<Cell>();
        for(int i = row -1 ; i <= row+1 ; i++ )
            for(int j = col -1 ; j <= col+1 ; j++)
                if(i>=0 && i<cells.length    &&     j>=0 && j<cells[0].length)
                    neighbours.add(cells[i][j]);
        return neighbours;
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
        if(myDig(row,col))
            return true;
        else if(specialDigCheck(row,col)){
            digAround(row,col);
            return true;
        }
        return false;
    }

    private Boolean myDig(int i , int j){

        if(cells[i][j].dig()){
            if(cells[i][j].getBomb())
                gameStatus = GameStatus.lose;
            else{
                remainDigs--;
                if(remainDigs == 0)
                    gameStatus = GameStatus.win;
                // if cell has no bombs around dig around it
                if(cells[i][j].getAroundBombs() == 0)
                    digAround(i,j);

            }
            if(gameStatus!=GameStatus.going)
                digAllBombs();
            return true;
        }
        return false;
    }

    private void digAround(int row,int col){
        for(int i = row -1 ; i <= row+1 ; i++ )
            for(int j = col -1 ; j <= col+1 ; j++)
                if(i>=0 && i<cells.length    &&     j>=0 && j<cells[0].length)
                    this.myDig(i,j);
    }

    private Boolean specialDigCheck(int row,int col){
        List<Cell> neighbours = this.getNeighbours(row,col);
        int countFlags = 0;
        for(Cell nei : neighbours)
            if(nei.getStatus() == CellStatus.flag)
                countFlags++;
        return cells[row][col].getStatus() == CellStatus.dig && countFlags == cells[row][col].getAroundBombs();
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
    public void updateView(List<Node> buttons) {
        int cols = cells[0].length;
        for(int i=0 ; i< buttons.size() ; i++ )
            cells[i/cols][i%cols].updateView((Button) buttons.get(i));
    }


    private void digAllBombs(){
        for (Cell[] cell : cells)
            for (Cell value : cell)
                if(value.getBomb())
                    value.dig();
    }
}
