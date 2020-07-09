public interface Cell {
    public Boolean getBomb();
    public CellStatus getStatus();
    public Boolean dig();
    public Boolean flag();
    public void setBomb(Boolean bomb);
    public char printCell();
    public void setAroundBombs(Integer aroundBombs);
    public Integer getAroundBombs();
}
