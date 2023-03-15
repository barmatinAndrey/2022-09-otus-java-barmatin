import io.grpc.Server;
import io.grpc.ServerBuilder;
import service.NumberGeneratorServiceImpl;


import java.io.IOException;

public class GRPCServer {
    public static final int SERVER_PORT = 8190;

    public static void main(String[] args) throws IOException, InterruptedException {
        var numberGeneratorService = new NumberGeneratorServiceImpl();

        Server server = ServerBuilder
                .forPort(SERVER_PORT)
                .addService(numberGeneratorService)
                .build();

        server.start();
        System.out.println("server waiting for client connections...");
        server.awaitTermination();

    }

}
