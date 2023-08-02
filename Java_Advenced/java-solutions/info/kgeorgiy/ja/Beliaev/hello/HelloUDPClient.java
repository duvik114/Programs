package info.kgeorgiy.ja.Beliaev.hello;

import info.kgeorgiy.java.advanced.hello.HelloClient;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class HelloUDPClient implements HelloClient {

    private InetSocketAddress address;
    private ExecutorService clients;

    /**
     * Runs Hello client.
     * This method should return when all requests completed.
     *
     * @param host     server host
     * @param port     server port
     * @param prefix   request prefix
     * @param threads  number of request threads
     * @param requests number of requests per thread.
     */
    @Override
    public void run(String host, int port, String prefix, int threads, int requests) {
        connectionInit(host, port, threads);

        List<Runnable> tasks = new ArrayList<>();
        IntStream.range(0, threads).forEach(i -> tasks.add(() -> {
            try(DatagramSocket socket = new DatagramSocket()) {
                socket.setSoTimeout(500);

                IntStream.range(0, requests).forEach(j -> {
                    try {
                        String request = (prefix + i + "_" + j);
                        byte[] byteRequest = request.getBytes(StandardCharsets.UTF_8);
                        DatagramPacket outputPacket = new DatagramPacket(byteRequest, byteRequest.length, address);
                        DatagramPacket inputPacket = new DatagramPacket(
                                new byte[socket.getReceiveBufferSize()],
                                socket.getReceiveBufferSize());
                        System.out.println(request);

                        doTask(socket, request, outputPacket, inputPacket);
                    } catch (SocketException e) {
                        System.err.println("Error during making requests: " + e.getMessage());
                    }
                });
            } catch (SocketException e) {
                System.err.println("Error during creating new socket: " + e.getMessage());
            }
        }));

        tasks.forEach(t -> clients.submit(t));

        try {
            clients.shutdown();
            if (!clients.awaitTermination((long) threads * requests * 111, TimeUnit.SECONDS)) {
                System.out.println("Cannot shutdown threads");
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted during shutdown: " + e.getMessage());
        }
    }

    private void connectionInit(String host, int port, int threads) {
        address = new InetSocketAddress(host, port);
        clients = Executors.newFixedThreadPool(threads);
    }

    private void doTask(DatagramSocket socket, String request, DatagramPacket outputPacket, DatagramPacket inputPacket) {
        while (!socket.isClosed()) {
            try {
                socket.send(outputPacket);
            } catch (IOException e) {
                continue;
            }

            try {
                socket.receive(inputPacket);
            } catch (IOException e) {
                continue;
            }

            String answer = new String(inputPacket.getData(),
                    inputPacket.getOffset(),
                    inputPacket.getLength(),
                    StandardCharsets.UTF_8);
            if (answer.equals("Hello, " + request)) {
                System.out.println(answer);
                break;
            }
        }
    }

    public static void main(String[] args) {
        Objects.requireNonNull(args);
        if (args.length != 5 || args[0] == null || args[1] == null || args[2] == null || args[3] == null || args[4] == null) {
            System.out.println("Usage: HelloUDPClient [host] [port] [prefix] [threads] [requests]");
            return;
        }

        int port, threads, requests;
        try {
            port = Integer.parseInt(args[1]);
            threads = Integer.parseInt(args[3]);
            requests = Integer.parseInt(args[4]);
        } catch (NumberFormatException e) {
            System.err.println("Arguments [port] [threads] and [requests] should be int: " + e.getMessage());
            return;
        }

        new HelloUDPClient().run(args[0], port, args[2], threads, requests);
    }
}
