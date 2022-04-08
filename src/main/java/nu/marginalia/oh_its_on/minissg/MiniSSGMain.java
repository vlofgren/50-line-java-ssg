package nu.marginalia.oh_its_on.minissg;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.FileTemplateLoader;
import org.slf4j.*;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.nio.file.*;

public class MiniSSGMain {
    private final Object params;
    private final Handlebars bars;
    private static Path templateDir;
    private static Path settingsFile;
    private static Path outputDir;

    private static final Logger logger = LoggerFactory.getLogger(MiniSSGMain.class);

    public static void main(String... args) throws IOException {
        templateDir = Path.of(args[0]);
        outputDir = Path.of(args[1]);
        settingsFile = Path.of(args[2]);

        new MiniSSGMain();
    }

    public MiniSSGMain() throws IOException {
        params = new Yaml().load(Files.readString(settingsFile));
        bars = new Handlebars(new FileTemplateLoader(templateDir.toFile(), ""));

        try (var filesStream = Files.list(templateDir)) {
            filesStream
                    .map(p -> p.getFileName().toString())
                    .filter(p -> p.endsWith(".html"))
                    .forEach(this::compile);
        }
    }

    public void compile(String filename) {
        logger.info("Compiling {}", filename);
        try {
            var output = bars.compile(filename).apply(params);
            var dest = outputDir.resolve(filename);

            Files.writeString(dest, output);
        }
        catch (Exception ex) {logger.error("Could not render " + filename, ex);}
    }
}