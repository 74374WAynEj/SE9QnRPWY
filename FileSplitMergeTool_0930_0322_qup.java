// 代码生成时间: 2025-09-30 03:22:28
package com.example.tools;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import play.Logger;

public class FileSplitMergeTool {
    
    private static final Logger.ALogger logger = Logger.of(FileSplitMergeTool.class);
    
    /**
     * Splits a file into smaller parts.
     *
     * @param filePath The path to the file to split.
     * @param splitSize The size of each split part in bytes.
     * @throws IOException If an I/O error occurs.
     */
    public void splitFile(String filePath, long splitSize) throws IOException {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            logger.error("File does not exist: " + filePath);
            throw new FileNotFoundException("File not found: " + filePath);
        }
        
        int fileCount = 1;
        InputStream in = new FileInputStream(filePath);
        try {
            while (in.available() > 0) {
                Path partPath = Paths.get(filePath + "_part_" + fileCount + ".tmp");
                OutputStream out = new FileOutputStream(partPath.toString());
                try {
                    byte[] buffer = new byte[(int) splitSize];
                    int bytesRead = in.read(buffer);
                    if (bytesRead > 0) {
                        out.write(buffer, 0, bytesRead);
                    }
                } finally {
                    out.close();
                }
                fileCount++;
            }
        } finally {
            in.close();
        }
    }
    
    /**
     * Merges split files back into a single file.
     *
     * @param filePath The path to the original file.
     * @param partFiles The list of split part files to merge.
     * @throws IOException If an I/O error occurs.
     */
    public void mergeFiles(String filePath, List<String> partFiles) throws IOException {
        OutputStream out = new FileOutputStream(filePath);
        try {
            for (String partFile : partFiles) {
                InputStream in = new FileInputStream(partFile);
                try {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = in.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }
                } finally {
                    in.close();
                }
                Files.delete(Paths.get(partFile)); // Delete the part file after merging
            }
        } finally {
            out.close();
        }
    }
    
    /**
     * Main method to run the tool.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Usage: java FileSplitMergeTool <split|merge> <file_path> <split_size> <part_files...>");
            return;
        }
        
        String command = args[0];
        String filePath = args[1];
        long splitSize = Long.parseLong(args[2]);
        List<String> partFiles = new ArrayList<>();
        for (int i = 3; i < args.length; i++) {
            partFiles.add(args[i]);
        }
        
        FileSplitMergeTool tool = new FileSplitMergeTool();
        try {
            if ("split".equalsIgnoreCase(command)) {
                tool.splitFile(filePath, splitSize);
                System.out.println("File split successfully.");
            } else if ("merge".equalsIgnoreCase(command)) {
                tool.mergeFiles(filePath, partFiles);
                System.out.println("Files merged successfully.");
            } else {
                System.out.println("Invalid command. Use 'split' or 'merge'.");
            }
        } catch (Exception e) {
            logger.error("Error processing file: ", e);
            System.out.println("Error: " + e.getMessage());
        }
    }
}