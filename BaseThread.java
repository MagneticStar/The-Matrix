public abstract class BaseThread extends Thread{
    private Thread thread;
    private int ID;

    public static int maxThreads = 0;

    public BaseThread(int ID){
        this.ID = ID;
    }

    public int getIntID(){
        return this.ID;
    }

    @Override
    public void run(){

    }

    @Override
    public void start(){
        if(thread == null){
            thread = new Thread(this);
            thread.start();
        }
    }

    public static void createThreads(){
        maxThreads = Runtime.getRuntime().availableProcessors();
        while(maxThreads > Database.tickThreads.size()){
            Database.tickThreads.add(new TickThread(Database.tickThreads.size()));
        }
        while(maxThreads < Database.tickThreads.size()){
            Database.tickThreads.remove(Database.tickThreads.size()-1);
        }
    }

}