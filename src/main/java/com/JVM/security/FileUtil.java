package com.JVM.security;

import java.io.File;
import java.io.IOException;
import java.security.AccessControlException;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 *  AccessController的最核心方法是它的静态方法checkPermission()，这个方法决定一个特定的操作能否被允许。
 *
 * FilePermission perm = new FilePermission("/temp/testFile", "read");
 *   AccessController.checkPermission(perm);
 * 在这个例子中, checkPermission将决定是否授予"读"权限给文件testFile
 *
 *
 *
 */
public class FileUtil {
    // 工程 A 执行文件的路径
    private final static String FOLDER_PATH = "C:\\Users\\dushangkui\\workspace\\projectX\\bin";

    public static void makeFile(String fileName) {
        try {
            // 尝试在工程 A 执行文件的路径中创建一个新文件
            File fs = new File(FOLDER_PATH + "\\" + fileName);
            fs.createNewFile();
        } catch (AccessControlException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void doPrivilegedAction(final String fileName) {
        // 用特权访问方式创建文件
        AccessController.doPrivileged(new PrivilegedAction<String>() {
            @Override
            public String run() {
                makeFile(fileName);
                return null;
            }
        });
    }
}