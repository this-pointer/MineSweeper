import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws InterruptedException, InvocationTargetException {
        EventQueue.invokeAndWait(()->{
            GUI g = new GUI();
        });
    }
}
