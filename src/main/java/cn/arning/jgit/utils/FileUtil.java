package cn.arning.jgit.utils;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author arning
 */
public class FileUtil {

    private static String GIT_PATH = ".git";

    private static String XLSX_SUFFIX = ".xlsx";

    private static String TXT_SUFFIX = ".txt";


    public static List<File> findLocalGitRepository(File project, List<File> localGitRepositories) {
        if (project.isFile()) {
            return localGitRepositories;
        }
        File[] projectSubPaths = project.listFiles();
        if (projectSubPaths.length == 0) {
            return localGitRepositories;
        }
        for (File projectSubPath : projectSubPaths) {
            if (projectSubPath.isFile()) {
                continue;
            }
            if (GIT_PATH.equals(projectSubPath.getName())) {
                localGitRepositories.add(projectSubPath);
                continue;
            }
            findLocalGitRepository(projectSubPath, localGitRepositories);
        }
        return localGitRepositories;
    }

    public static List<String> findCloneUrl(String path) {
        File file = new File(path);
        String suffix = path.substring(path.lastIndexOf("."));
        List<String> urls = new ArrayList<>();
        if (TXT_SUFFIX.equals(suffix)) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(file));
                String tmpUrl;
                while ((tmpUrl = reader.readLine()) != null) {
                    urls.add(tmpUrl);
                }
            } catch (IOException e) {
                System.out.println(e);
            } finally {
                if (null != reader) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                }
            }
        }
        if (XLSX_SUFFIX.equals(suffix)) {
            //TODO 解析excel获取地址
        }
        return urls;
    }
}
