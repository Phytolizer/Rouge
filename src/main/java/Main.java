import engine.eventlisteners.CursorPosListener;
import engine.eventlisteners.MouseButtonListener;
import engine.graphics.Window;
import engine.eventlisteners.KeyListener;
import engine.logic.Timer;

public class Main {
    public static void main(String[] args) {
        var mainWindow = new Window();
        var rouge = new Rouge();
    
        mainWindow.setTimer(new Timer());
        mainWindow.setCursorPosListener(new CursorPosListener());
        mainWindow.setMouseButtonListener(new MouseButtonListener());
        mainWindow.setKeyListener(new KeyListener());
        
        mainWindow.run(rouge);
    }
}
