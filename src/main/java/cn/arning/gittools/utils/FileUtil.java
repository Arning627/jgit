package cn.arning.gittools.utils;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static List<String> findCloneUrl(File readFile) {
        String name = readFile.getName();
        String suffix = name.substring(name.lastIndexOf("."));
        List<String> urls = new ArrayList<>();
        Assert.isNotNull(readFile, "文件不存在");
        if (TXT_SUFFIX.equals(suffix)) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(readFile));
                String tmpUrl;
                while ((tmpUrl = reader.readLine()) != null) {
                    urls.add(tmpUrl);
                }
            } catch (IOException e) {
                System.err.println(e);
            } finally {
                if (null != reader) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        System.err.println(e);
                    }
                }
            }
        }
        if (XLSX_SUFFIX.equals(suffix)) {
            ExcelReader excelReader = null;
            try {
                List<ExcelData> excelData = EasyExcel.read(name).head(ExcelData.class).sheet().doReadSync();
                for (ExcelData excelDatum : excelData) {
                    String url = excelDatum.getUrl();
                    urls.add(url);
                }
            } catch (Exception e) {
                System.err.println(e);
            } finally {
                if (null != excelReader) {
                    excelReader.finish();
                }
            }
        }
        return urls;
    }

    public static Map<String, String> readProjectVersion(File file) {
        Assert.isNotNull(file, "文件不存在");
        try {
            HashMap<String, String> projectAndVersion = new HashMap<>();
            Path path = file.toPath();
            List<String> projectVersions = Files.readAllLines(path);
            for (String projectVersion : projectVersions) {
                String[] split = projectVersion.split(":");
                projectAndVersion.put(split[0], split[1]);
            }
            return projectAndVersion;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, File> findLocalGitRepository(File project, Map<String, File> localGitRepositories) {
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
                String name = projectSubPath.getParentFile().getName();
                localGitRepositories.put(name,projectSubPath);
                continue;
            }
            findLocalGitRepository(projectSubPath, localGitRepositories);
        }

        return localGitRepositories;
    }

}
