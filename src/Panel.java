import javafx.scene.Node;
import javafx.util.Pair;

import java.util.List;

public interface Panel {
    public Integer getNumOfFlags();
    public GameStatus getGameStatus();
    public void select(int row , int col);
    public Boolean dig();
    public Boolean flag();

    public void updateView(List<Node> buttons);

    public void printPanel();
}
