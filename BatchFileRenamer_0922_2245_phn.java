// 代码生成时间: 2025-09-22 22:45:55
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import play.Logger;

/**
 * BatchFileRenamer is a utility class for renaming files in a directory based on a pattern.
 */
public class BatchFileRenamer {

    private static final Logger.ALogger logger = Logger.of(BatchFileRenamer.class);

    /**
     * Renames files in the specified directory according to the given pattern.
     *
     * @param directoryPath The path to the directory containing the files.
     * @param pattern       The regular expression pattern to match file names.
     * @param replacement  The replacement string for the matched pattern.
     * @throws IOException If an I/O error occurs.
     */
    public void renameFiles(String directoryPath, String pattern, String replacement) throws IOException {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        if (files == null) {
            logger.error("Directory does not exist or is not accessible: " + directoryPath);
            return;
        }

        for (File file : files) {
            if (file.isFile()) {
                String fileName = file.getName();
                Pattern p = Pattern.compile(pattern);
                Matcher m = p.matcher(fileName);

                if (m.find()) {
                    String newFileName = m.replaceFirst(replacement);
                    Path oldPath = file.toPath();
                    Path newPath = oldPath.resolveSibling(newFileName);

                    Files.move(oldPath, newPath, StandardCopyOption.REPLACE_EXISTING);
                    logger.info("Renamed file from " + fileName + " to " + newFileName);
                } else {
                    logger.warn("No match found for file: " + fileName);
                }
            }
        }
    }

    /**
     * Main method to test the BatchFileRenamer.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        if (args.length < 3) {
            logger.error("Usage: BatchFileRenamer <directoryPath> <pattern> <replacement>");
            return;
        }

        String directoryPath = args[0];
        String pattern = args[1];
        String replacement = args[2];

        try {
            BatchFileRenamer renamer = new BatchFileRenamer();
            renamer.renameFiles(directoryPath, pattern, replacement);
        } catch (IOException e) {
            logger.error("Error renaming files: " + e.getMessage());
        }
    }
}
