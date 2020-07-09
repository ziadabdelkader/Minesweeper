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

        GridPane buttons = new GridPane();
        buttons.setAlignment(Pos.CENTER);
        for(int i=0 ;i < rows ; i++ ) for(int j=0 ;j < cols ; j++){
            Button btn = new Button();
            btn.setId(String.valueOf(j + i*cols));
            btn.setPrefSize(40,40);
            btn.setOnAction(event -> {
                Button btnClicked = (Button) event.getTarget();
                int id = Integer.parseInt(btnClicked.getId());
                game.select(id/cols,id%cols);
            });
            buttons.add(btn,j,i);
        }

        Button dig = new Button("DIG");
        dig.setOnAction(event -> {
            game.dig();
            game.updateView(buttons.getChildren());
        });

        Button flag = new Button("FLAG");
        flag.setOnAction(event -> {
            game.flag();
            game.updateView(buttons.getChildren());
        });
        dig.setPrefSize(40*cols/2, 40*rows/10);
        flag.setPrefSize(40*cols/2,40*rows/10);

        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.add(buttons , 0 , 0 , 2 , 10);
        root.add(dig,0,10,1,1);
        root.add(flag,1,10,1,1);

        primaryStage.setTitle("Minesweeper");
        primaryStage.setScene(new Scene(root, 800, 950));
        primaryStage.show();
    }

}
