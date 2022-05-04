package info.kgeorgiy.ja.Beliaev.walk;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Walk {
    private static final String EXCEPTIONAL_RESULT = "0".repeat(40);
    private static final String HASH_ALGO = "SHA-1";
    private static final int BUFFER_SIZE = 8282;

    private List<String> getFileNames(String inputFileNames) throws IOException, InvalidPathException {
        List<String> resultList = new ArrayList<>();
        Files.lines(Paths.get(inputFileNames)).forEach(resultList::add);
        return resultList;
    }

    private String createSha1AndAddPath(String fileName) {
        Path filePath;
        try {
            filePath = Paths.get(fileName);
        } catch (InvalidPathException e) {
            System.err.println("Invalid path of file '" + fileName + "', cannot open: " + e.getMessage());
            return EXCEPTIONAL_RESULT + " " + fileName;
        }

        MessageDigest digest;
        try {
            // :NOTE: move to consts // fixed
            digest = MessageDigest.getInstance(HASH_ALGO);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error while initializing hash algorithm: " + e.getMessage());
            return null;
        }
        try(InputStream fis = Files.newInputStream(filePath)) {
            int readSize;
            // :NOTE: move to a const // fixed
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((readSize = fis.read(buffer)) != -1) {
                if (readSize > 0) {
                    digest.update(buffer, 0, readSize);
                }
            }
            // :NOTE: RuntimeException // fixed ?
        } catch (AccessDeniedException e) {
            System.err.println("Cannot open file '" + fileName + "' access denied: " + e.getMessage());
            return EXCEPTIONAL_RESULT + " " + fileName;
        }/* catch (SecurityException e) {
            System.err.println("Security exception during reading input file: " + e.getMessage());
            // :NOTE: move to a const // fixed
            return EXCEPTIONAL_RESULT + " " + fileName;
        }*/ catch (FileNotFoundException e) {
            System.err.println("Cannot find input file: " + e.getMessage());
            return EXCEPTIONAL_RESULT + " " + fileName;
        } catch (IllegalArgumentException e) {
            System.err.println("Illegal argument during reading input file: " + e.getMessage());
            return EXCEPTIONAL_RESULT + " " + fileName;
        } catch (IOException e) {
            System.err.println("Error during reading input file: " + e.getMessage());
            return EXCEPTIONAL_RESULT + " " + fileName;
        }

        StringBuilder result = new StringBuilder();
        for (byte b : digest.digest()) {
            result.append(String.format("%02x", b));
        }
        return result + " " + fileName;
    }

    public static void main(String[] args) {
        if (args == null || args.length != 2 || args[0] == null || args[1] == null) {
            System.err.println("Wrong number of arguments, expected: 'inputFileName' and 'outputFileName' file names");
            System.err.println("Please rerun the program with correct arguments");
            return;
        }

        Walk walk = new Walk();
        String inputFileNamesFile = args[0];
        String outputFileNamesFile = args[1];
        List<String> files;
        try {
            files = walk.getFileNames(inputFileNamesFile);
        } catch (InvalidPathException e) {
            System.err.println("Input file name is incorrect: " + e.getMessage());
            return;
        } catch (IOException e) {
            System.err.println("Cannot open input file: " + e.getMessage());
            return;
        }

        // :NOTE: велосипеды bufferedReader // fixed
        List<String> resultFileHashesList = new ArrayList<>();
        files.forEach(fileName -> resultFileHashesList.add(walk.createSha1AndAddPath(fileName)));

        Path outputFilePath;
        try {
            outputFilePath = Paths.get(outputFileNamesFile);
        } catch (InvalidPathException e) {
            System.err.println("Invalid path of output file '" + outputFileNamesFile + "', cannot open: " + e.getMessage());
            return;
        }

        try (BufferedWriter writer = Files.newBufferedWriter(outputFilePath, StandardCharsets.UTF_8)) {
            resultFileHashesList.forEach(str -> {
                try {
                    writer.write(str);
                    writer.newLine();
                } catch (IOException e) {
                    System.err.println("Error during writing to output: " + e.getMessage());
                }
            });
        } catch (NoSuchFileException e) {
            System.err.println("No such file '" + outputFileNamesFile + "': " + e.getMessage());
        } catch (AccessDeniedException e) {
            System.err.println("Access to the output file '" + outputFileNamesFile + "' denied: " + e.getMessage());
        } catch (InvalidPathException e) {
            System.err.println("Output file name is incorrect: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Cannot open output file: " + e.getMessage());
        }
    }
}
