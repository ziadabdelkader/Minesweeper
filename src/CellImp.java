import javafx.scene.control.Button;

public class CellImp implements Cell {
    private Boolean bomb;
    private CellStatus status;
    private Integer aroundBombs;
    public CellImp(){
        this.bomb = false;
        this.status = CellStatus.active;
        aroundBombs = 0;
    }
    @Override
    public Boolean getBomb() {
        return bomb;
    }

    @Override
    public CellStatus getStatus() {
        return status;
    }

    @Override
    public Boolean dig() {
        if(status == CellStatus.active){
            status = CellStatus.dig;
            return true;
        }
        return false;
    }

    @Override
    public Boolean flag() {
        if(status == CellStatus.active){
            status = CellStatus.flag;
            return true;
        }else if(status == CellStatus.flag){
            status = CellStatus.active;
            return true;
        }
        return false;
    }

    @Override
    public void setBomb(Boolean bomb) {
        this.bomb = bomb;
    }

    @Override
    public char printCell() {
        char character;
        if(status == CellStatus.active)
            character = '?';
        else if(status == CellStatus.flag)
            character = 'F';
        else{
            if(getBomb())
                character = 'B';
            else{
                //System.out.println("aroundBombs = " + aroundBombs);
                character = (char)(aroundBombs+'0');
            }

        }
        return character;
    }

    @Override
    public void setAroundBombs(Integer aroundBombs) {
        this.aroundBombs = aroundBombs;
    }

    @Override
    public Integer getAroundBombs() {
        return this.aroundBombs;
    }

    @Override
    public void updateView(Button button) {
        if(this.status == CellStatus.flag){
            button.setText("F");
        }else if(status == CellStatus.active){
            button.setText("");
        }else{
            if(this.getBomb())
                button.setText("B");
            else
                button.setText(String.valueOf(this.getAroundBombs()));
        }
    }


}
