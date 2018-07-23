package labs;

import java.util.concurrent.TimeoutException;

public class SingleElementBufferTimed {

    private Integer elem = null;

    public synchronized void put(Integer newElem, long timeout) throws InterruptedException, TimeoutException {
        long waitTime = timeout;
        while (elem != null && waitTime > 0) {
            long t0 = System.currentTimeMillis();
            wait(waitTime);
            long t1 = System.currentTimeMillis();
            long elapsedTime = t1 - t0;
            waitTime -= elapsedTime;
        }
        if(elem != null && waitTime <= 0) throw new TimeoutException();
        //todo: insert throw new TimeoutException
        this.elem = newElem;
        this.notifyAll();
    }

    public synchronized Integer get(long timeout) throws InterruptedException, TimeoutException {
        long waitTime = timeout;
        while (elem == null && waitTime > 0) {
            long t0 = System.currentTimeMillis();
            wait(timeout);
            long t1 = System.currentTimeMillis();
            long elapsedTime = t1 - t0;
            waitTime -= elapsedTime;
        }
        //todo: insert throw new TimeoutException
        int result = this.elem;
        this.elem = null;
        this.notifyAll();
        return result;
    }
}
