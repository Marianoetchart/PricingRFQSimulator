

public class MarketSimulator {
    public static void main (String[] args) {
    new Thread (new RFQSimulator(), "RFQSimulator Thread").start();	
    new Thread (new OrderBookSimulator(), "OrderBookSimulator Thread").start();
    }
}
class RFQSimulator implements Runnable {
    @Override
    public void run (){
		System.out.println ("RFQ Sim Runnable interface");
	}

}

class OrderBookSimulator implements Runnable {

    private double midPrice;

    public OrderBookSimulator(){
        this.midPrice = 2.40; // taken from Euro 10 yr Swap on 30/8/2022 close
    }

    @Override
    public void run (){
		System.out.println ("OrderBook Sim Runnable interface");
        OrderBookSimulator obs = new OrderBookSimulator(); 

        while(true){
            obs.randomWalk();
            System.out.println("Mid Price: " + obs.midPrice);
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

}