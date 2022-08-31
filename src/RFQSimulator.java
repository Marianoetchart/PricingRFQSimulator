import java.util.concurrent.BlockingQueue;
import java.util.UUID;
import java.util.logging.*; 

public class RFQSimulator implements Runnable {

    private final BlockingQueue<String> queue;
    private final BlockingQueue<String> responseQueue;

    private SingletonLogger slogger; 
    private Logger logger;
    

    @Override
    public void run (){
		System.out.println ("RFQ Simimulator starting");

        while(true){
            this.randomRFQGenerator(100000); // maximum quantity is 100k, if needed to be moved to program params
    
        }

	}

    public RFQSimulator(BlockingQueue<String> q, BlockingQueue<String> rq){

        slogger = new SingletonLogger();
        this.logger = slogger.getLogger(); 

        this.queue = q;
        this.responseQueue = rq;
    }

    public void randomRFQGenerator(int maxQ){

        try {
            double r = Math.random(); 
            long lag = (long)(r * 5000); // 5000 ms == 5 secs 
            System.out.println("Adding RFQ random time lag of "+ lag/1000 +" seconds" );
            Thread.sleep(lag); 

            int quantity = (int)Math.round(Math.random() * maxQ) ; // quantity is any number between 0 and the input parameter 

            // random binomial decision 
            String direction; 
            if (r > 0.5){
                direction = "BUY"; 
            }else{direction = "SELL"; }

            UUID uniqueKey = UUID.randomUUID(); 

            String msg = "\nRFQ: " + uniqueKey.toString() + "\nQuantity: " + String.valueOf(quantity) + "\nDirection: " + direction + "\n";   

            this.logger.info("Sending RFQ \n" + msg);

            this.queue.put(msg); 

            this.queue.put("*"); 

        } catch(InterruptedException e) {
            System.out.println("Got interrupted whilst generating RFQ");
        }
    }

}