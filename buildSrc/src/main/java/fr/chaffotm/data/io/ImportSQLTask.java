package fr.chaffotm.data.io;

import fr.chaffotm.data.io.geo.WorldGenerator;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ImportSQLTask extends DefaultTask {

    @InputDirectory
    @Optional
    public
    String outputDir = "";

    @Input
    @Optional
    public
    String fileName = "import";

    @OutputFile
    public File getSQLFile() {
        final File buildDir = getProject().getBuildDir();
        return new File(String.format("%s/resources/main/"+ outputDir, buildDir), fileName + ".sql");
    }

    @TaskAction
    void run() throws IOException {
        final WorldGenerator generator = new WorldGenerator(getLogger());
        writeFile(getSQLFile(), generator.toSQL());
    }

    private void writeFile(final File destination, final String content) throws IOException {
        try (final FileWriter writer = new FileWriter(destination);
             final BufferedWriter output = new BufferedWriter(writer)) {
            output.write(content);
        }
    }

}
