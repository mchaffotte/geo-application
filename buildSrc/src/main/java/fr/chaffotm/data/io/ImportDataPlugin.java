package fr.chaffotm.data.io;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.TaskContainer;

public class ImportDataPlugin implements Plugin<Project> {

    private static final String GROUP = "import";

    @Override
    public void apply(final Project project) {
        final TaskContainer tasks = project.getTasks();
        final Copy importMedia = tasks.create("importMedia", Copy.class, copy -> {
            copy.from(project.zipTree(getClass().getProtectionDomain().getCodeSource().getLocation().toExternalForm()), copySpec -> {
                copySpec.include("/flags/*", "/silhouettes/*");
            });
            copy.into(String.format("%s/resources/main", project.getBuildDir()));
        });
        importMedia.setGroup(GROUP);
        importMedia.setDescription("Import images and sounds");

        final Task importSQL = tasks.create("importSQL", ImportSQLTask.class);
        importSQL.dependsOn(importMedia);
        importSQL.setGroup(GROUP);
        importSQL.setDescription("Import data");
    }

}
