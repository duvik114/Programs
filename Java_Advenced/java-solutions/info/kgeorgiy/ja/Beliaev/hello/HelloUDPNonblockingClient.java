package info.kgeorgiy.ja.Beliaev.hello;

import info.kgeorgiy.java.advanced.hello.HelloClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketOptions;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.stream.IntStream;

public class HelloUDPNonblockingClient implements HelloClient {

    private final int TIMEOUT = 100;

    private static class Attachment {
        ByteBuffer buffer;
        int threadNum, requestNum;

        public Attachment(int threadNum) {
            this.buffer = ByteBuffer.allocate(Math.max(SocketOptions.SO_SNDBUF, SocketOptions.SO_RCVBUF));
            this.threadNum = threadNum;
            this.requestNum = 0;
        }
    }

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
        Selector selector;
        try {
            selector = init(host, port, threads);
        } catch (IOException e) {
            System.err.println("Error during init: " + e.getMessage());
            return;
        }
        while (threads > 0) {

            try {
                selector.select(TIMEOUT);
            } catch (IOException e) {
                System.err.println("Too long wait. " + e.getMessage());
            }

            Set<SelectionKey> keys;
            final int[] finalThreads = {threads};
            if ((keys = selector.selectedKeys()).isEmpty()) {
                selector.keys().forEach(k -> {
                    DatagramChannel keyChannel = (DatagramChannel) k.channel();
                    Attachment attachment = (Attachment) k.attachment();
                    doRequest(prefix, k, keyChannel, attachment);
                });
            } else {
                keys.forEach(k -> {
                    DatagramChannel keyChannel = (DatagramChannel) k.channel();
                    Attachment attachment = (Attachment) k.attachment();
                    if (doResponse(prefix, requests, finalThreads, k, keyChannel, attachment)) {
                        return;
                    }
                    doRequest(prefix, k, keyChannel, attachment);
                });
                selector.selectedKeys().clear();
                threads = finalThreads[0];
            }
        }
    }

    private boolean doResponse(String prefix, int requests, int[] finalThreads, SelectionKey k, DatagramChannel keyChannel, Attachment attachment) {
        if (k.isReadable()) {
            attachment.buffer.clear();
            try {
                keyChannel.receive(attachment.buffer);
            } catch (IOException e) {
                System.err.println("Cannot receive data: " + e.getMessage());
                return true;
            }
            String response = StandardCharsets.UTF_8.decode(attachment.buffer.flip()).toString();
            if (response.contains(prefix + attachment.threadNum + "_" + attachment.requestNum)) {
                attachment.requestNum++;
            } else {
                System.err.println("Error, wrong response: " + response);
            }
            if (attachment.requestNum == requests) {
                try {
                    keyChannel.close();
                    k.cancel(); //
                } catch (IOException e) {
                    System.err.println("Cannot close channel: " + e.getMessage());
                }
                finalThreads[0]--;
                return true;
            }
            k.interestOps(SelectionKey.OP_WRITE);
        }
        return false;
    }

    private Selector init(String host, int port, int threads) throws IOException {
        Selector selector;
        InetSocketAddress address = new InetSocketAddress(InetAddress.getByName(host), port);
        selector = Selector.open();

        IntStream.range(0, threads).forEach(i -> {
            try {
                DatagramChannel channel = DatagramChannel.open();
                channel.setOption(StandardSocketOptions.SO_REUSEADDR, true)
                        .connect(address)
                        .configureBlocking(false)
                        .register(selector, SelectionKey.OP_WRITE, new Attachment(i));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return selector;
    }

    private void doRequest(String prefix, SelectionKey k, DatagramChannel keyChannel, Attachment attachment) {
        if (k.isWritable()) {
            String request = (prefix + attachment.threadNum + "_" + attachment.requestNum);
            attachment.buffer
                    .clear()
                    .put(request.getBytes(StandardCharsets.UTF_8))
                    .flip();

            try {
                keyChannel.write(attachment.buffer);
                k.interestOps(SelectionKey.OP_READ);
            } catch (IOException e) {
                System.err.println("Cannot send data: " + e.getMessage());
            }
        }
    }
}
