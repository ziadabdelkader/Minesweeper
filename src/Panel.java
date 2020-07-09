import javafx.util.Pair;

public interface Panel {
    public Integer getNumOfFlags();
    public GameStatus getGameStatus();
    public void select(int row , int col);
    public Boolean dig();
    public Boolean flag();
    public void printPanel();
    public Cell getCell(int row , int col);
    public Pair<Integer,Integer> getSelectedIndex();
}
