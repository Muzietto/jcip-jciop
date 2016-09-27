package net.faustinelli.concurrent.asyncunit;

import static org.junit.Assert.*;

import net.faustinelli.lift.movement.Move;
import net.faustinelli.lift.queue.Floor;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AsyncUnitTest {


    private void waitOnCallback(MyCallback cb) {
        synchronized (cb) {
            try {
                cb.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            assertTrue(false);
        }
    }

    @Test
    public void testMoveTwoFloorsNoBlock() throws InterruptedException, ExecutionException {

        Long now = System.currentTimeMillis();
        System.out.println("Test thread says start TimeMillis=" + now);

        Floor groundFloor = new Floor(0);
        Floor secondFloor = new Floor(2);

        Move myMove = new Move(groundFloor);
        MyCallback myCB = new MyCallback();

        myMove.moveTo(secondFloor).thenRun(() -> {
            myCB.timestamp();
            myCB.onDone();
        });

        waitOnCallback(myCB);

        System.out.println("Test thread says end TimeMillis=" + now);
    }

    public class MyCallback {

        public void timestamp() {
            System.out.println("Callback says TimeMills=" + System.currentTimeMillis());
        }

        public void onDone() {
            synchronized (this) {
                notifyAll();
            }
        }
    }
}
