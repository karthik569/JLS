package jls;

import java.util.concurrent.locks.StampedLock;

/**
 * JLS 48/50: Java 8+ - StampedLock Optimistic Reading (JLS §17.4)
 * Demonstrates non-blocking optimistic read validation under JMM.
 */
public class Ex48_StampedLockOptimisticRead {

    private double x, y;
    private final StampedLock lock = new StampedLock();

    public void move(double deltaX, double deltaY) {
        long stamp = lock.writeLock();
        try {
            x += deltaX;
            y += deltaY;
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    public double distanceFromOrigin() {
        // Optimistic read (does not block writes)
        long stamp = lock.tryOptimisticRead();
        double currentX = x, currentY = y;
        if (!lock.validate(stamp)) { // Check if a write occurred in between
            stamp = lock.readLock(); // Fallback to pessimistic read lock
            try {
                currentX = x;
                currentY = y;
            } finally {
                lock.unlockRead(stamp);
            }
        }
        return Math.sqrt(currentX * currentX + currentY * currentY);
    }

    public static void main(String[] args) {
        Ex48_StampedLockOptimisticRead demo = new Ex48_StampedLockOptimisticRead();
        demo.move(3, 4);
        System.out.println("Distance: " + demo.distanceFromOrigin());
    }
}
