package cn.arning.gittools.shell;


import cn.arning.gittools.command.Clone;
import cn.arning.gittools.command.Execute;
import cn.arning.gittools.conf.GitUserConfig;
import cn.arning.gittools.utils.Assert;
import cn.arning.gittools.utils.FileUtil;
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
    private Execute checkOutAndPull;

    @Autowired
    private Clone cloneRepos;


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
        System.out.printf("\033[31;1m当前目录为: %s\033[0m\n", currentDir);
        projectFile = new File(currentDir);
    }

    /**
     * 设置认证信息
     *
     * @param user
     * @param pwd
     * @return
     */
    @ShellMethod("\033[31;2m-u [用户名] -p [密码] \033[0m")
    public String auth(@ShellOption("-u") String user, @ShellOption("-p") String pwd) {
        GitUserConfig.IS_AUTHENTICATION = true;
        GitUserConfig.GIT_PASSWORD = pwd;
        GitUserConfig.GIT_USERNAME = user;
        return "setting...";
    }

    /**
     * 创建或删除tag
     *
     * @param version
     * @param message
     */
    @ShellMethod("\033[31;2m-v [版本] -m [描述信息]\033[0m")
    public void tag(@ShellOption("-v") String version, @ShellOption("-m") String message) {
        List<File> localGitRepository = FileUtil.findLocalGitRepository(projectFile, gits);
        System.out.printf("当前目录共 %d 个仓库", localGitRepository.size());
        try {
            for (File file : localGitRepository) {
                Git git = Git.open(file);
                createTags.execute(git, message, version);
                System.out.println("无操作");
            }
            System.out.printf("更新完成,成功 %d 条记录,失败 %d 条记录\n", (localGitRepository.size() - errorPath.size()), errorPath.size());
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
    @ShellMethod("\033[31;2m-b [分支]\033[0m")
    public void pull(@ShellOption("-b") String branch) {
        List<File> localGitRepository = FileUtil.findLocalGitRepository(projectFile, gits);
        System.out.println("当前目录共 " + localGitRepository.size() + " 个仓库");
        try {
            for (File file : localGitRepository) {
                Git git = Git.open(file);
                checkOutAndPull.execute(git, branch, "");
            }
            System.out.printf("更新完成,成功 %d 条记录,失败 %d 条记录\n", (localGitRepository.size() - errorPath.size()), errorPath.size());
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


    /**
     * 读取同目录下配置文件 clone工程
     *
     * @param filename
     * @throws IOException
     */
    @ShellMethod("\033[31;2m-f [配置文件名]\033[0m")
    public void gitClone(@ShellOption(value = "-f", defaultValue = "cloneUrl.txt") String filename) throws IOException {
        File file = new File(currentDir + "/" + filename);
        Assert.isNotNull(file, "文件不存在");
        List<String> cloneUrl = FileUtil.findCloneUrl(file);
        for (String url : cloneUrl) {
            cloneRepos.clone(url, currentDir);
        }

        System.out.printf("clone完成,失败%d条\n", errorPath.size());


        printErrorPath();
    }





}
