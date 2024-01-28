package com.myy;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;

public class LocalFileUtils {

    public static final int BUFFER_SIZE = 8 * 1024;

    public static final String CHUNK_NAME_PREFIX = "chunk_";

    // 输入流存入本地文件
    // 判断文件大小, 超过
    // 本地文件切片
    // 本地切片文件批量传输至远程
    // 传输完成后通知合并远程切片文件
    // 删除本地文件和本地切片文件

    public static final char separator = File.separatorChar;

    public static File saveTempFile(String fileName, InputStream in) {
        File file = new File(System.getProperty("java.io.tmpdir"));
        return null;
    }

    /**
     * 创建文件
     *
     * @param fileFullName 文件全名称
     * @return file
     */
    public File createFile(String fileFullName) {
        String[] directoryAndFile = splitDirectoryAndFile(fileFullName);
        if (!new File(directoryAndFile[0]).mkdirs()) {
            throw new RuntimeException("");
        }
        File file = new File(directoryAndFile[1]);
        if (file.exists() && file.isDirectory()) {
            throw new RuntimeException("");
        }
        return file;
    }


    public String[] splitDirectoryAndFile(String fileFullName) {
        char[] chars = fileFullName.toCharArray();
        int begin = 0;
        for (int i = chars.length - 1; i >= 0; i--) {
            if (chars[i] == separator) {
                begin = i + 1;
                break;
            }
        }
        return new String[]{fileFullName.substring(0, begin - 1), fileFullName.substring(begin)};
    }


    public static File saveTempFile(MultipartFile multipartFile) {
        return null;
    }

    /**
     * 分割文件
     *
     * @param filePath  分割文件的路径
     * @param chunkSize 切片大小
     * @return 返回存储切片数据的文件夹路径
     */
    public static File[] splitFile(String filePath, String chunkDirPath, int chunkSize) {
        File file = new File(filePath);
        File dir = new File(chunkDirPath);
        if (!(file.isFile() && dir.mkdir() && ArrayUtils.isEmpty(dir.list()))) {
            throw new RuntimeException("filePath must be file & chunkDirPath must be empty directory");
        }
        try (FileInputStream in = new FileInputStream(file)) {
            byte[] buffer = new byte[BUFFER_SIZE];
            boolean hasNext = true;
            int index = 0;
            while (hasNext) {
                hasNext = generateChunk(in, new File(dir, generateChunkName(index++)), chunkSize, buffer);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dir.listFiles();
    }

    private static String generateChunkName(int index) {
        return CHUNK_NAME_PREFIX + index;
    }

    private static boolean generateChunk(FileInputStream in, File chunkFile, int chunkSize, byte[] buffer) throws IOException {
        try (FileOutputStream out = new FileOutputStream(chunkFile)) {
            int remain = chunkSize;
            int read = 0;
            while ((read = in.read(buffer, 0, Math.min(remain, buffer.length))) != -1) {
                out.write(buffer, 0, read);
                if ((remain = remain - read) <= 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 合并文件
     *
     * @param chunkDirPath 存储切片数据的文件夹路径
     * @param newFilePath  指定合并新文件的路径
     */
    public static void mergeFile(String chunkDirPath, String newFilePath) {
        File dir = new File(chunkDirPath);
        File[] chunks = dir.listFiles();
        File file = new File(newFilePath);
        if (!(dir.isDirectory() && chunks != null && chunks.length > 0 && file.isFile())) {
            throw new RuntimeException("chunkDirPath must not be empty directory && file must be file");
        }
        try (FileOutputStream out = new FileOutputStream(file)) {
            byte[] buffer = new byte[BUFFER_SIZE];
            // 按照切片文件索引排序
            chunkIndexSorted(chunks);
            for (File chunk : chunks) {
                mergeChunk(out, chunk, buffer);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void chunkIndexSorted(File[] files) {
        Arrays.sort(files, Comparator.comparingInt(f -> Integer.parseInt(f.getName().replace(CHUNK_NAME_PREFIX, ""))));
    }

    private static void mergeChunk(FileOutputStream out, File chunk, byte[] buffer) throws IOException {
        try (FileInputStream in = new FileInputStream(chunk)) {
            int length = 0;
            while ((length = in.read(buffer)) != -1) {
                out.write(buffer, 0, length);
            }
        }
    }


}
