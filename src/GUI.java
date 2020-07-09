import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Pair;

public class GUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        int rows = 20;
        int cols = 10;
        int flags = 35;
        Panel game = new PanelImp(rows,cols,flags);


        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        for(int i=0 ;i < rows ; i++ ) for(int j=0 ;j < cols ; j++){
            Button btn = new Button();
            btn.setId(String.valueOf(j + i*cols));
            btn.setPrefSize(40,40);
            btn.setOnAction(event -> {
                Button btnClicked = (Button) event.getTarget();
                int id = Integer.parseInt(btnClicked.getId());
                game.select(id/cols,id%cols);
            });
            gridPane.add(btn,j,i);
        }

        Button dig = new Button("DIG");
        dig.setOnAction(event -> {
            game.dig();
            Pair<Integer,Integer> index = game.getSelectedIndex();
            update((Button) gridPane.getChildren().get(index.getKey()*cols+index.getValue()), game.getCell(index.getKey(),index.getValue()));
            if(game.getGameStatus() != GameStatus.going){
                for(int i=0 ;i < rows ; i++ ) for(int j=0 ;j < cols ; j++)
                    update((Button) gridPane.getChildren().get(i*cols+j), game.getCell(i,j));
            }
        });

        Button flag = new Button("FLAG");
        flag.setOnAction(event -> {
            game.flag();
            Pair<Integer,Integer> index = game.getSelectedIndex();
            update((Button) gridPane.getChildren().get(index.getKey()*cols+index.getValue()), game.getCell(index.getKey(),index.getValue()));
        });
        dig.setPrefSize(40*cols/2,
                40*rows/10);
        flag.setPrefSize(40*cols/2,40*rows/10);
        gridPane.add(dig,0,rows,cols/2,rows/10);
        gridPane.add(flag,cols/2,rows,cols/2,rows/10);

        primaryStage.setTitle("Minesweeper");
        primaryStage.setScene(new Scene(gridPane, 800, 950));
        primaryStage.show();
    }
    private void update(Button btn, Cell cell){
        CellStatus status = cell.getStatus();
        if(status == CellStatus.flag){
            btn.setText("F");
        }else if(status == CellStatus.active){
            btn.setText("");
        }else{
            if(cell.getBomb())
                btn.setText("B");
            else
                btn.setText(String.valueOf(cell.getAroundBombs()));
        }
    }
}
