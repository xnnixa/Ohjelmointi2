import java.util.TimerTask;
import java.util.Timer;

class MyTimer extends Timer
{
    public void runTask(String msg, long timeout)
    {
        schedule(new MyTimerTask(msg), timeout);
    }

    class MyTimerTask extends TimerTask {
        private String imsg;

        public MyTimerTask(String imsg) {
            this.imsg = imsg;
        }

        public void run(){
            System.out.println(imsg);
        }
    }
    
}

// DON'T TOUCH THIS
public class MyTimerMain {
    // DON'T TOUCH THIS
    public static void main(String args[]) throws InterruptedException{// DON'T TOUCH THIS
// DON'T TOUCH THIS
        MyTimer timer = new MyTimer();// DON'T TOUCH THIS
// DON'T TOUCH THIS
        timer.runTask("2 seconds passed", 2000);// DON'T TOUCH THIS
        Thread.sleep(2000);// DON'T TOUCH THIS
        timer.runTask("2 seconds passed", 2000);// DON'T TOUCH THIS
        Thread.sleep(2000);// DON'T TOUCH THIS
        timer.cancel();// DON'T TOUCH THIS
    }// DON'T TOUCH THIS
}// DON'T TOUCH THIS
// DON'T TOUCH THIS