import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Tst {
    public static void main(String[] args){
        gameTest();
    }
    public static void gameTest(){
        Panel game = new PanelImp(5,5,9);
        game.printPanel();
        Scanner scanner = new Scanner(System.in);
        int type;
        int row;
        int col;
        while (true){
            type = scanner.nextInt();
            if(type == 1){
                row = scanner.nextInt();
                col = scanner.nextInt();
                game.select(row,col);
            }else if(type == 2){
                game.dig();
            }else
                game.flag();
            game.printPanel();
        }
    }
    public static void arrTest(){
        Integer arr[] = new Integer[5];
        for(int i=0 ; i<5 ;i++)
            arr[i] = i;
        Integer target = arr[0];
        target = 8;
        System.out.println(arr[0]);
    }
    public static void timerTest(){
        int delay = 0; // delay for 5 sec.
        int period = 1000; // repeat every sec.
        final Integer[] count = {0};
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask()
        {
            public void run()
            {

                count[0]++;
                System.out.println(count[0]);

            }
        }, delay, period);
    }
}
