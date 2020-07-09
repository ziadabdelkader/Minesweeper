import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Pair;

public class GUI extends Application {
    private int rows = 10;
    private int cols = 5;
    private int flags = 5;
    private Panel game = new PanelImp(rows,cols,flags);
    private GridPane buttons = new GridPane();
    private Button dig = new Button("DIG");
    private Button flag = new Button("FLAG");
    private GridPane root = new GridPane();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        buttons.setAlignment(Pos.CENTER);
        for(int i=0 ;i < rows ; i++ ) for(int j=0 ;j < cols ; j++){
            Button btn = new Button("");
            btn.setId(String.valueOf(j + i*cols));
            btn.setPrefSize(40,40);
            btn.setOnAction(event -> {
                Button btnClicked = (Button) event.getTarget();
                int id = Integer.parseInt(btnClicked.getId());
                game.select(id/cols,id%cols);
            });

            btn.setOnMouseClicked(mouseEvent -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    if(mouseEvent.getClickCount() == 2)
                        digEvent();
                }
                else if(mouseEvent.getButton().equals(MouseButton.SECONDARY)){
                    Button btnClicked = (Button) mouseEvent.getTarget();
                    int id = Integer.parseInt(btnClicked.getId());
                    game.select(id/cols,id%cols);
                    flagEvent();
                }
            });
            buttons.add(btn,j,i);
        }
        dig.setOnAction(event -> {
            digEvent();
        });
        flag.setOnAction(event -> {
            flagEvent();
        });
        dig.setPrefSize(40*cols/2, 40*rows/10);
        flag.setPrefSize(40*cols/2,40*rows/10);

        root.setAlignment(Pos.CENTER);
        root.add(buttons , 0 , 0 , 2 , 10);
        root.add(dig,0,10,1,1);
        root.add(flag,1,10,1,1);

        primaryStage.setTitle("Minesweeper");
        primaryStage.setScene(new Scene(root, 800, 950));
        primaryStage.show();
    }
    private void digEvent(){
        game.dig();
        game.updateView(buttons.getChildren());
    }
    private void flagEvent(){
        game.flag();
        game.updateView(buttons.getChildren());
    }
}
