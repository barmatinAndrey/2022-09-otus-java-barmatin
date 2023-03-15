import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import ru.barmatin.grpc.generated.NumberGeneratorServiceGrpc;
import ru.barmatin.grpc.generated.NumberRequest;
import ru.barmatin.grpc.generated.NumberResponse;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

public class GRPCClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;

    public static void main(String[] args) throws InterruptedException {
        var channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();

        var stub = NumberGeneratorServiceGrpc.newStub(channel);

        AtomicLong valueFromServer = new AtomicLong();

        var latch = new CountDownLatch(1);
        stub.sendRequest(NumberRequest.newBuilder().setFirstValue(0).setLastValue(30).build(), new StreamObserver<NumberResponse>() {
            @Override
            public void onNext(NumberResponse value) {
                valueFromServer.set(value.getResponseValue());
                System.out.println("valueFromServer: " + valueFromServer);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {
                System.out.println("\n\nThat's all");
                latch.countDown();
            }
        });

        long currentValue = 0;
        for (long i = 0; i <= 50; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            currentValue = currentValue + valueFromServer.get() + 1;
            System.out.println("currentValue: " + currentValue);
            valueFromServer.set(0);
        }

        latch.await();

        channel.shutdown();

    }

}
