package generate.utils;

import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * @author fei
 * @title: FileUtils
 * @projectName codegenerate
 * @description: 文件处理工具类
 * @date 2020/2/16 10:49
 */
public class FileUtils {

    /**
     * 得到相对路径
     *
     * @param baseDir 文件基础路径
     * @param file    文件对象
     * @return
     */
    public static String getRelativePath(File baseDir, File file) {
        if (baseDir.equals(file)) {
            return "";
        }
        if (baseDir.getParentFile() == null) {
            return file.getAbsolutePath().substring(baseDir.getAbsolutePath().length());
        }
        return file.getAbsolutePath().substring(baseDir.getAbsolutePath().length() + 1);
    }

    /**
     * 查询某个目录下的所有文件
     *
     * @param dir 文件目录
     * @return
     * @throws IOException
     */
    public static List<File> searchAllFile(File dir) throws IOException {
        ArrayList arrayList = new ArrayList();
        searchFiles(dir, arrayList);
        return arrayList;
    }

    /**
     * 递归获取某个目录下的所有文件
     *
     * @param dir 文件目录
     * @param collector 所有文件集合
     * @throws IOException
     */
    public static void searchFiles(File dir, List<File> collector) throws IOException {
        if (dir.isDirectory()) {
            File[] subFiles = dir.listFiles();
            for (int i = 0; i < subFiles.length; i++) {
                searchFiles(subFiles[i], collector);
            }
        } else {
            collector.add(dir);
        }
    }

    /**
     * 创建文件
     * @param dir
     * @param file
     * @return
     */
    public static File mkdir(String dir, String file) {
        if (dir == null) {
            throw new IllegalArgumentException("dir must be not null");
        }
        File result = new File(dir, file);
        if (result.getParentFile() != null) {
            result.getParentFile().mkdirs();
        }
        return result;
    }
}
