package info.kgeorgiy.ja.Beliaev.crawler;

import info.kgeorgiy.java.advanced.crawler.*;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class WebCrawler implements Crawler {

    private final Downloader downloader;
    private final ExecutorService downloadService;
    private final ExecutorService extractService;
    private final int perHost;

    public WebCrawler(Downloader downloader, int downloaders, int extractors, int perHost) {
        this.downloader = downloader;
        this.downloadService = Executors.newFixedThreadPool(downloaders);
        this.extractService = Executors.newFixedThreadPool(extractors);
        this.perHost = perHost;
    }

    /**
     * Downloads web site up to specified depth.
     *
     * @param url   start <a href="http://tools.ietf.org/html/rfc3986">URL</a>.
     * @param depth download depth.
     * @return download result.
     */
    @Override
    public Result download(String url, int depth) {
        Map<String, IOException> errors = new ConcurrentHashMap<>();
        Set<String> urls = ConcurrentHashMap.newKeySet();
        Set<String> layerUrls = ConcurrentHashMap.newKeySet();
        layerUrls.add(url);

        downloadLayer(depth, layerUrls, urls, errors);

        urls.removeAll(errors.keySet());
        return new Result(urls.stream().toList(), errors);
    }

    private void downloadLayer(int depth, Set<String> layerUrls, Set<String> urls, Map<String, IOException> errors) {
        if (depth == 0) {
            return;
        }

        layerUrls.removeAll(urls);
        urls.addAll(layerUrls);

        Set<String> newLayerUrls = ConcurrentHashMap.newKeySet();
        Phaser phaser = new Phaser(1);
        for (String u : layerUrls) {
            downloadWeb(u, newLayerUrls, errors, phaser);
        }
        phaser.arriveAndAwaitAdvance();
        layerUrls = newLayerUrls;
        downloadLayer(depth - 1, layerUrls, urls, errors);
    }

    private void downloadWeb(String url, Set<String> layerUrls, Map<String, IOException> errors, Phaser phaser) {
        phaser.register();
        downloadService.submit(() -> {
            try {
                Document document = downloader.download(url);
                phaser.register();
                extractService.submit(() -> {
                    try {
                        layerUrls.addAll(document.extractLinks());
                    } catch (IOException e) {
                        errors.putIfAbsent(url, e);
                    } finally {
                        phaser.arrive();
                    }
                });
            } catch (IOException e) {
                errors.putIfAbsent(url, e);
            } finally {
                phaser.arrive();
            }
        });
    }

    /**
     * Closes this web-crawler, relinquishing any allocated resources.
     */
    @Override
    public void close() {
        try {
            serviceShutdown(downloadService);
        } catch (InterruptedException e) {
            System.err.println("Error during downloadService shutdown: " + e.getMessage());
            downloadService.shutdownNow();
        }

        try {
            serviceShutdown(extractService);
        } catch (InterruptedException e) {
            System.err.println("Error during extractorService shutdown: " + e.getMessage());
            extractService.shutdownNow();
        }
    }

    private void serviceShutdown(ExecutorService downloadService) throws InterruptedException {
        downloadService.shutdown();
        if (!downloadService.awaitTermination(999, TimeUnit.MILLISECONDS)) {
            downloadService.shutdownNow();
        }
    }

    public static void main(String[] args) {
        Objects.requireNonNull(args);

        if (args.length < 1 || args.length > 5) {
            System.out.println("Correct usage: ");
            System.out.println("\tWebCrawler url [depth [downloads [extractors [perHost]]]]");
            return;
        }

        String url = args[0];
        int[] params = new int[4];
        IntStream
                .range(1, 5)
                .forEach((i) -> params[i] = args.length >= (i + 1) ? Integer.parseInt(args[i]) : 1);
        try {
            WebCrawler crawler = new WebCrawler(new CachingDownloader(), params[1],  params[2],  params[3]);
            crawler.download(url, params[0]);
        } catch (IOException e) {
            System.err.println("Error in downloader constructor: " + e.getMessage());
        }
    }
}
