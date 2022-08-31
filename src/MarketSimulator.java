import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MarketSimulator {
    public static void main (String[] args) throws InterruptedException {
    BlockingQueue<String> q = new LinkedBlockingQueue<String>(); // queue to be shared amongst threads
    BlockingQueue<String> rq = new LinkedBlockingQueue<String>(); // Response queue to be shared amongst threads
    Thread RFQThread = new Thread (new RFQSimulator(q, rq), "RFQSimulator Thread");
    Thread OBThread = new Thread (new OrderBookSimulator(q, rq), "OrderBookSimulator Thread");
    
    RFQThread.start();
    OBThread.start();

    RFQThread.join();
    OBThread.join();
    }
}


