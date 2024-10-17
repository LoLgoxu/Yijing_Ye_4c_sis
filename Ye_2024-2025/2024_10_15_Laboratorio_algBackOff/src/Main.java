import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random rand = new Random();

        //microseconds Slot Time = 64B/10Mbps
        final double slotTime = 51.2;

        //k(bOL) = min(n(num of collisions), 10)
        int backOffLimit = rand.nextInt(10) + 1;

        //variable needed for calculate the range of time
        int r = rand.nextInt(1 << backOffLimit);

        //simulating an example of collisions based on backOffLimits
        for (int i = 0; i != backOffLimit; i++) {
            System.out.println("Collision number " + (i + 1) );
        }

        //r * Slot Time
        double waitingTime = r * slotTime;

        System.out.println("Waiting time " + waitingTime + "µs");
    }
}
