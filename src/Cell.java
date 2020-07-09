import javafx.scene.control.Button;

public interface Cell {
    public Boolean getBomb();
    public void setBomb(Boolean bomb);
    public CellStatus getStatus();
    public Boolean dig();
    public Boolean flag();
    public void setAroundBombs(Integer aroundBombs);
    public Integer getAroundBombs();
    public void updateView(Button button);

    public char printCell();
}
