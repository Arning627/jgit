package cn.arning.jgit.shell;


import cn.arning.jgit.command.Execute;
import cn.arning.jgit.conf.GitUserConfig;
import cn.arning.jgit.utils.FileUtil;
import org.eclipse.jgit.api.Git;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author arning
 */
@ShellComponent
public class Method {


    @Autowired
    private Execute createTags;

    @Autowired
    private Execute deleteTags;

    @Autowired
    private Execute checkOutAndPull;


    /**
     * 异常目录集合
     */
    public static List<String> errorPath = new ArrayList<>();

    /**
     * 当前目录
     */
    static String currentDir;

    static File projectFile;

    List<File> gits = new ArrayList<>();

    static {
        currentDir = System.getProperties().getProperty("user.dir");
        System.out.println("当前目录为: " + currentDir);
        projectFile = new File(currentDir);
    }

    /**
     * 设置认证信息
     *
     * @param user
     * @param pwd
     * @return
     */
    @ShellMethod("insert user")
    public String auth(@ShellOption("-u") String user, @ShellOption("-p") String pwd) {
        GitUserConfig.IS_AUTHENTICATION = true;
        GitUserConfig.GIT_PASSWORD = pwd;
        GitUserConfig.GIT_USERNAME = user;
        return "setting...";
    }

    /**
     * 创建或删除tag
     * @param version
     * @param message
     * @param function
     */
    @ShellMethod("create tags")
    public void tag(@ShellOption("-v") String version, @ShellOption("-m") String message, @ShellOption("-f") String function) {
        List<File> localGitRepository = FileUtil.findLocalGitRepository(projectFile, gits);
        System.out.println("当前目录共 " + localGitRepository.size() + " 个仓库");
        try {
            for (File file : localGitRepository) {
                Git git = Git.open(file);
                switch (function) {
                    case "d":
                        deleteTags.execute(git, message, version);
                        break;
                    case "c":
                        createTags.execute(git, message, version);
                        break;
                    default:
                        System.out.println("无操作");
                }
            }
            System.out.println("更新完成,成功 " + (localGitRepository.size() - errorPath.size()) + " 条记录,失败 " + errorPath.size() + " 条记录");
            printErrorPath();
            gits.clear();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * 切换分支 检出
     *
     * @param branch
     */
    @ShellMethod("pull code")
    public void pull(@ShellOption("-b") String branch) {
        List<File> localGitRepository = FileUtil.findLocalGitRepository(projectFile, gits);
        System.out.println("当前目录共 " + localGitRepository.size() + " 个仓库");
        try {
            for (File file : localGitRepository) {
                Git git = Git.open(file);
                checkOutAndPull.execute(git, branch, "");
            }
            System.out.println("更新完成,成功 " + (localGitRepository.size() - errorPath.size()) + " 条记录,失败 " + errorPath.size() + " 条记录");
            printErrorPath();
            gits.clear();
        } catch (IOException e) {
            System.out.println(e);
        }
    }


    private void printErrorPath() {
        if (errorPath.size() > 0) {
            System.out.println("失败目录如下: ");
            for (String path : errorPath) {
                System.out.println(path);
            }
            errorPath.clear();
        }
    }


}
