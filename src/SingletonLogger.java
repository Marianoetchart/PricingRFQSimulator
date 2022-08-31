import java.util.logging.*; 

public class SingletonLogger {        
    private static SingletonLogger singletonLogger;
    private static Logger logger;    
    private static FileHandler fh;
    private static Formatter sf;

    public SingletonLogger() {
        // if instance exists, return. Adhering to singleton design
        if (singletonLogger != null) {
            return;
        }

        
        try {
            fh = new FileHandler("Audit.log");
        } catch (Exception e) {
            e.printStackTrace();
        }

        sf = new SimpleFormatter();
        fh.setFormatter(sf);  
        logger = Logger.getLogger("SingletonLogger");          
        logger.addHandler(fh);
        singletonLogger = this;   
    }

    public Logger getLogger() {
        return SingletonLogger.logger;
    }
}