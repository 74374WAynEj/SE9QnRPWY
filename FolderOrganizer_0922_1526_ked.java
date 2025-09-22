// 代码生成时间: 2025-09-22 15:26:28
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * FolderOrganizer - A PlayFramework controller to organize files within a directory.
 */
public class FolderOrganizer extends Controller {

    private static final Logger.ALogger logger = Logger.of(FolderOrganizer.class);

    /**
     * Organize files within a given directory.
     *
     * @param directoryPath The path to the directory to be organized.
     * @return A Result object indicating the success or failure of the operation.
     */
    public Result organizeFiles(String directoryPath) {
        try {
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                return badRequest("Directory does not exist.");
            }
            if (!directory.isDirectory()) {
                return badRequest("Path is not a directory.");
            }

            // Get all files in the directory and sort them by name.
            File[] files = directory.listFiles();
            if (files != null) {
                Arrays.sort(files, Comparator.comparing(File::getName));

                // Create a new directory for organized files.
                File organizedDirectory = new File(directory, "organized");
                if (!organizedDirectory.exists()) {
                    organizedDirectory.mkdir();
                }

                // Move each file to the new organized directory.
                for (File file : files) {
                    Path sourcePath = Paths.get(file.getAbsolutePath());
                    Path targetPath = Paths.get(organizedDirectory.getAbsolutePath(), file.getName());
                    Files.move(sourcePath, targetPath);
                }
            }

            return ok("Files organized successfully.");
        } catch (IOException e) {
            logger.error("Error organizing files", e);
            return internalServerError("An error occurred while organizing files.");
        }
    }
}
