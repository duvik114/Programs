package info.kgeorgiy.ja.Beliaev.hello;

import info.kgeorgiy.java.advanced.hello.HelloServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HelloUDPNonblockingServer implements HelloServer {

    private DatagramChannel channel;
    private ExecutorService service;
    private Selector selector;

    private final static int BUFSIZE = 999;
    private final int TIMEOUT = 999;

    /**
     * Starts a new Hello server.
     * This method should return immediately.
     *
     * @param port    server port.
     * @param threads number of working threads.
     */
    @Override
    public void start(int port, int threads) {
        InetSocketAddress address = new InetSocketAddress(port);
        try {
            init(address);
            service.submit(() -> {
                try {
                    run();
                } catch (IOException e) {
                    System.err.println("Error while selecting: " + e.getMessage());
                }
            });
        } catch (IOException e) {
            System.err.println("Cannot open channel: " + e.getMessage());
        }
    }

    private void init(InetSocketAddress address) throws IOException {
        service = Executors.newSingleThreadExecutor();
        channel = DatagramChannel.open();
        selector = Selector.open();
        channel.setOption(StandardSocketOptions.SO_REUSEADDR, true)
                .bind(address)
                .configureBlocking(false)
                .register(selector, SelectionKey.OP_READ, new Attachment());
    }

    private static class Attachment {
        ByteBuffer buffer;
        SocketAddress address;

        Attachment() {
            buffer = ByteBuffer.allocate(BUFSIZE);
        }

        void addData(String s) {
            buffer = ByteBuffer.allocate(s.length());
            buffer.put(s.getBytes(StandardCharsets.UTF_8));
        }
    }


    private void run() throws IOException {
        while (channel.isOpen()) {
            ByteBuffer keyBuffer = ByteBuffer.allocate(BUFSIZE);
            selector.select(key -> {
                if (key.isReadable()) {
                    try {
                        keyBuffer.clear();
                        DatagramChannel keyChannel = (DatagramChannel) key.channel();
                        Attachment attachment = (Attachment) key.attachment();
                        doRead(keyBuffer, key, keyChannel, attachment);
                    } catch (IOException e) {
                        System.err.println("Error while processing data: " + e.getMessage());
                        key.interestOps(SelectionKey.OP_WRITE);
                    }
                }
                if (key.isWritable()) {
                    DatagramChannel keyChannel = (DatagramChannel) key.channel();
                    Attachment attachment = (Attachment) key.attachment();
                    doWrite(key, keyChannel, attachment);
                }
            }, TIMEOUT);
        }
    }

    private void doRead(ByteBuffer keyBuffer, SelectionKey key, DatagramChannel keyChannel, Attachment attachment) throws IOException {
        attachment.address = keyChannel.receive(keyBuffer);
        if (attachment.address != null) {
            byte[] res = new byte[keyBuffer.flip().remaining()];
            keyBuffer.get(res);
            attachment.addData("Hello, " + new String(res, StandardCharsets.UTF_8));
            key.interestOps(SelectionKey.OP_WRITE);
        }
        keyBuffer.clear();
    }

    private void doWrite(SelectionKey key, DatagramChannel keyChannel, Attachment attachment) {
        attachment.buffer.flip();
        try {
            keyChannel.send(attachment.buffer, attachment.address);
        } catch (IOException e) {
            System.err.println("Cannot send data: " + e.getMessage());
        }
        key.interestOps(SelectionKey.OP_READ);
    }

    /**
     * Stops server and deallocates all resources.
     */
    @Override
    public void close() {
        try {
            channel.close();
            selector.close();
            service.shutdown();
            try {
                service.awaitTermination(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                service.shutdownNow();
            }
        } catch (IOException e) {
            System.err.println("Error while closing server: " + e.getMessage());
        }
    }
}
