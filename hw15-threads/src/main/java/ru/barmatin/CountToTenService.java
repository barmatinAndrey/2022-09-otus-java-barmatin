package ru.barmatin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CountToTenService {
    private final Logger logger = LoggerFactory.getLogger(CountToTenService.class);

    private static final String FIRST_THREAD_NAME = "firstThread";
    private int counter1;
    private boolean isIncrease1;

    private static final String SECOND_THREAD_NAME = "secondThread";
    private int counter2;
    private boolean isIncrease2;

    private String lastThreadName;

    public void startCount() {
        counter1 = 0;
        isIncrease1 = true;

        counter2 = 0;
        isIncrease2 = true;

        lastThreadName = SECOND_THREAD_NAME;

        new Thread(() -> action(counter2, isIncrease2), SECOND_THREAD_NAME).start();
        new Thread(() -> action(counter1, isIncrease1), FIRST_THREAD_NAME).start();
    }

    private synchronized void action(int counter, boolean isIncrease) {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                while (Thread.currentThread().getName().equals(lastThreadName)) {
                    wait();
                }

                if (counter == 0) {
                    isIncrease = true;
                } else if (counter == 10) {
                    isIncrease = false;
                }

                if (isIncrease) {
                    counter++;
                } else {
                    counter--;
                }

                lastThreadName = Thread.currentThread().getName();
                sleep();
                logger.info(String.valueOf(counter));

                notify();
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

}
