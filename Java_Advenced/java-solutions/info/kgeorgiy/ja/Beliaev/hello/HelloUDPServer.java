package info.kgeorgiy.ja.Beliaev.hello;

import info.kgeorgiy.java.advanced.hello.HelloServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HelloUDPServer implements HelloServer {
    private ExecutorService servers;
    private DatagramSocket socket;

    /**
     * Starts a new Hello server.
     * This method should return immediately.
     *
     * @param port    server port.
     * @param threads number of working threads.
     */
    @Override
    public void start(int port, int threads) {
        try {
            connectionInit(port, threads);

            List<Runnable> tasks = Stream
                    .generate(() -> (Runnable) () -> {
                        try {
                            while (!socket.isClosed()) {
                                doTask();
                            }
                        } catch (IOException e) {
                            System.err.println("Error during receiving input packet: " + e.getMessage());
                        }
                    })
                    .limit(threads)
                    .collect(Collectors.toList());

            tasks.forEach(t -> servers.submit(t));
        } catch (SocketException e) {
            System.err.println("Error during starting new socket: " + e.getMessage());
        }
    }

    private void connectionInit(int port, int threads) throws SocketException {
        socket = new DatagramSocket(port);
        servers = Executors.newFixedThreadPool(threads);
    }

    private void doTask() throws IOException {
        DatagramPacket inputPacket = new DatagramPacket(
                new byte[socket.getReceiveBufferSize()],
                socket.getReceiveBufferSize());

        socket.receive(inputPacket);

        String inputString = new String(
                inputPacket.getData(),
                inputPacket.getOffset(),
                inputPacket.getLength(),
                StandardCharsets.UTF_8);

        String outputString = "Hello, " + inputString;
        DatagramPacket outputPacket = new DatagramPacket(
                outputString.getBytes(StandardCharsets.UTF_8),
                outputString.getBytes().length,
                inputPacket.getSocketAddress());

        socket.send(outputPacket);
    }

    /**
     * Stops server and deallocates all resources.
     */
    @Override
    public void close() {
        socket.close();

        try {
            servers.shutdownNow();
            if (!servers.awaitTermination(1, TimeUnit.SECONDS)) {
                System.err.println("Error during shutdown servers");
            }
        } catch (InterruptedException e) {
            System.err.println("Interrupted during shutdown servers: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Objects.requireNonNull(args);
        if (args.length != 2 || args[0] == null || args[1] == null) {
            System.out.println("Usage: HelloUDPServer [port] [threads]");
            return;
        }

        int port, threads;
        try {
            port = Integer.parseInt(args[0]);
            threads = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.err.println("Parameters [port] and [threads] should be int: " + e.getMessage());
            return;
        }

        new HelloUDPServer().start(port, threads);
    }
}
