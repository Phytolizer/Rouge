package engine.logic;

public class Clock {
    private static Clock instance = null;
    
    private static double currentTime;
    private static double pastTime;
    private static double deltaTime;
    
    private Clock() {
    }
    
    public static void setTime(double aTime) {
        if (instance == null) {
            instance = new Clock();
        }
        
        pastTime = currentTime;
        currentTime = aTime;
        deltaTime = currentTime - pastTime;
    }
    
    public static double getDeltaTime() {
        if (instance == null) {
            instance = new Clock();
        }
        
        return deltaTime;
    }
}
