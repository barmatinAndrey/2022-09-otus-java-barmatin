package service;

import io.grpc.stub.StreamObserver;
import ru.barmatin.grpc.generated.NumberGeneratorServiceGrpc;
import ru.barmatin.grpc.generated.NumberRequest;
import ru.barmatin.grpc.generated.NumberResponse;

public class NumberGeneratorServiceImpl extends NumberGeneratorServiceGrpc.NumberGeneratorServiceImplBase {

    @Override
    public void sendRequest(NumberRequest request, StreamObserver<NumberResponse> responseObserver) {
        for (long i = request.getFirstValue(); i <= request.getLastValue(); i++) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            responseObserver.onNext(NumberResponse.newBuilder().setResponseValue(i).build());
        }
        responseObserver.onCompleted();
    }
}
