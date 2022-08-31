import java.util.concurrent.BlockingQueue;

class OrderBookSimulator implements Runnable {

    private double midPrice;
    private final BlockingQueue<String> queue;
    private final BlockingQueue<String> responseQueue;

    public OrderBookSimulator(BlockingQueue<String> q, BlockingQueue<String> rq){
        this.queue = q;
        this.responseQueue = rq;
        this.midPrice = 2.40; // taken from Euro 10 yr Swap on 30/8/2022 close
    }

    @Override
    public void run (){
		System.out.println ("OrderBook Simulator starting");

        while(true){
            this.processRFQs();
            this.randomWalk();
            System.out.println("Mid Price: " + this.midPrice);
        }
	}

    private void randomWalk(){
        double p = Math.random(); 
        if (p > 0.5){
            this.midPrice = this.midPrice + 0.001 ; // moves up by 10 basis points
        }else{
            this.midPrice = this.midPrice - 0.001 ; // moves down 10 bps 
        }
    }
    
    public double getMidPrice(){
        return this.midPrice;
    }

    private void processRFQs(){
        try{
            String value = this.queue.take();
            while (!value.equals("*")) {
                System.out.println( value );

                // retrieves RFQ identifier, then replies back to response queue the mid price
                String RFQID = value.split(System.lineSeparator())[0];
                this.responseQueue.put( RFQID + "\n" + "Mid Price: " + this.getMidPrice() ); 
                this.responseQueue.put("*"); 

                //start again
                value = queue.take();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}