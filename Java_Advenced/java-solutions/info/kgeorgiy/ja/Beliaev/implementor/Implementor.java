package info.kgeorgiy.ja.Beliaev.implementor;

import info.kgeorgiy.java.advanced.implementor.ImplerException;
import info.kgeorgiy.java.advanced.implementor.JarImpler;
import info.kgeorgiy.java.advanced.implementor.Impler;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.jar.JarOutputStream;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;

/**
 * Class implementing {@link JarImpler}. Public methods create {@code .java}
 * or {@code .jar} files for classes implementing given interface.
 *
 * @author Beliaev Nikita
 */
public class Implementor implements JarImpler {

    /**
     * Creates a {@code .java} file with source code of a class implementing
     * interface {@code token} in location {@code root}.
     *
     * @param token type token to create implementation for.
     * @param root root directory.
     * @throws ImplerException error during creating {@code .java} file
     */
    @Override
    public void implement(Class<?> token, Path root) throws ImplerException {
        if (!token.isInterface()) {
            throw new ImplerException("Expected interface, incorrect class token");
        }
        if (Modifier.isPrivate(token.getModifiers())) {
            throw new ImplerException("Cannot implement private interface");
        }

        File classFile = new File(Paths.get(root.toString(),
                token.getPackageName().split("\\.")).resolve(token.getSimpleName() + "Impl.java").toString());
        // :NOTE: old IO
        if (!classFile.exists() && (!classFile.getParentFile().exists() && !classFile.getParentFile().mkdirs())) {
            throw new ImplerException("Failed to create path to output file");
        }

        //writing:
        try {
            //package
            BufferedWriter writer = new BufferedWriter(new FileWriter(classFile, StandardCharsets.UTF_8));
            // :NOTE: empty package
            writer.write("package " + token.getPackageName() + ";");
            writer.newLine();
            writer.newLine();

            //class head
            writer.write("public class " + token.getSimpleName() + "Impl implements " + token.getCanonicalName() + " {");
            writer.newLine();

            //methods
            for (Method method : token.getMethods()) {
                // :NOTE: package-private
                if (!Modifier.isPublic(method.getModifiers())) {
                    continue;
                }

                //method head
                writer.write("\tpublic " + method.getReturnType().getCanonicalName() + " " + method.getName()
                        + "(");
                Class<?>[] parameters = method.getParameterTypes();
                for (int i = 0; i < method.getParameterCount(); i++) {
                    if (i > 0) {
                        writer.write(", ");
                    }
                    writer.write(parameters[i].getCanonicalName() + " p" + i);
                }
                writer.write(") {");
                writer.newLine();

                //method body
                String returnValue;
                if (!method.getReturnType().isPrimitive()) {
                    returnValue = "null";
                } else if (method.getReturnType().equals(boolean.class)) {
                    returnValue = "false";
                } else if (method.getReturnType().equals(void.class)) {
                    returnValue = "";
                } else {
                    returnValue = "0";
                }
                writer.write("\t\treturn " + returnValue + ";");
                writer.newLine();

                //method tail
                writer.write("} ");
                writer.newLine();
                writer.newLine();
            }

            //class tail
            writer.write("}");
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            System.err.println("Cannot write to " + token.getSimpleName() + " file: " + e.getMessage());
        }
    }

    /**
     * Creates a {@code .jar} file for created {@code .java} file which contains
     * source code of a class implementing interface {@code token} in location {@code root}
     *
     * @param token type token to create implementation for.
     * @param jarFile target <var>.jar</var> file.
     * @throws ImplerException error during creating {@code .jar} file
     */
    @Override
    public void implementJar(Class<?> token, Path jarFile) throws ImplerException {
        try {
            implement(token, Paths.get(""));
        } catch (ImplerException e) {
            System.err.println("Error during creating class (.java) file: " + e.getMessage());
        }
        final String classFilename = Paths.get("",
                token.getPackageName().split("\\.")).resolve(token.getSimpleName() + "Impl").toString();
        compileFiles(token, classFilename);
        try {
            final JarOutputStream writer = new JarOutputStream(Files.newOutputStream(jarFile));
            writer.putNextEntry(new ZipEntry(classFilename.replace(File.separatorChar, '/') + ".class"));
            Files.copy(Paths.get(classFilename + ".class"), writer);
            writer.close();
        } catch (final IOException e) {
            throw new ImplerException("Error during writing to jar file: ", e);
        }
    }

    /**
     * Compile generated {@code .java} file and creates {@code .jar}
     *
     * @param token class for compilation
     * @param className class name for compilation
     * @throws ImplerException error during compilation
     */
    public void compileFiles(Class<?> token, String className) throws ImplerException {
        try {
            final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            String[] args = Stream.of("-classpath", getClassPath(token), className + ".java").toArray(String[]::new);
            if (compiler.run(null, null, null, args) != 0) {
                throw new ImplerException("Error during compilation of file");
            }
        } catch (final URISyntaxException e) {
            throw new ImplerException("URISyntaxException", e);
        }
    }

    /**
     * Returns full path of given file
     *
     * @param token class which path is needed to be returned
     * @return full path in {@link String}
     * @throws URISyntaxException error during getting full path
     */
    private static String getClassPath(Class<?> token) throws URISyntaxException {
        return Path.of(token.getProtectionDomain().getCodeSource().getLocation().toURI()).toString();
    }

    /**
     * Main function. Provides console interface for {@link Implementor} class.
     * Runs in two modes depending on count of arguments {@code args}:
     * <ol>
     * <li>2 arguments - {@code className outputPath} creates {@code .java} file by executing {@link Impler} method
     * {@link #implement(Class, Path)}</li>
     * <li>3 arguments - {@code -jar className jarOutputPath} creates {@code .jar} file by executing {@link JarImpler}
     * method {@link #implementJar(Class, Path)}</li>
     * </ol>
     *
     * If argument count isn't equal to 3 or 4 then method throws {@link ImplerException}
     *
     * @param args command line arguments.
     */
    public static void main(final String[] args) {
        if (args.length < 3 || args.length > 4) {
            System.err.println("Incorrect number of args");
            return;
        } else if (Arrays.stream(args).anyMatch(Objects::isNull)) {
            System.err.println("Some arguments is null");
            return;
        }

        try {
            Implementor implementor = new Implementor();
            if (args.length == 3) {
                implementor.implement(Class.forName(args[1]), Path.of(args[2]));
            } else {
                implementor.implementJar(Class.forName(args[2]), Path.of(args[3]));
            }
        } catch (ImplerException e) {
            System.err.println("Error during implementing interface: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Cannot create given class: " + e.getMessage());
        }
    }
}
