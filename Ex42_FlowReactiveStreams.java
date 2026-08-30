package jls;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * JLS 42/50: Java 9+ - Flow API Reactive Streams Specification (JLS §17)
 * Demonstrates Reactive Streams (Publisher, Subscriber, Subscription).
 */
public class Ex42_FlowReactiveStreams {

    public static void main(String[] args) throws InterruptedException {
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();

        publisher.subscribe(new Flow.Subscriber<String>() {
            private Flow.Subscription subscription;

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                this.subscription = subscription;
                subscription.request(1);
            }

            @Override
            public void onNext(String item) {
                System.out.println("Reactive Item received: " + item);
                subscription.request(1);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onComplete() {
                System.out.println("Reactive Stream complete.");
            }
        });

        publisher.submit("Data Event 1");
        publisher.submit("Data Event 2");
        publisher.close();

        Thread.sleep(100);
    }
}
