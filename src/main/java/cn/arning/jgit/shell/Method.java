package cn.arning.jgit.shell;


import cn.arning.jgit.command.Execute;
import cn.arning.jgit.conf.GitUserConfig;
import cn.arning.jgit.utils.FileUtil;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.RemoteConfig;
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

    @ShellMethod("create tags")
    public String tag(@ShellOption("-v") String version, @ShellOption("-m") String message, @ShellOption("-f") String function) throws IOException, GitAPIException {
        List<File> gits = new ArrayList<>();
//        String currentDir = System.getProperties().getProperty("user.dir");
        String currentDir = "/Users/arning/Desktop/tmp";
        File projectFile = new File(currentDir);
        List<File> localGitRepository = FileUtil.findLocalGitRepository(projectFile, gits);
        System.out.println("当前目录共 " + localGitRepository.size() + " 个仓库");
        String execute = "";
        for (File file : localGitRepository) {
            Git git = Git.open(file);
            switch (function) {
                case "d":
                    execute = deleteTags.execute(git, message, version);
                    break;
                case "c":
                    execute = createTags.execute(git, message, version);
                    break;
                default:
                    System.out.println("无操作");
            }
            System.out.println(execute);
        }
        return "done...";
    }

    @ShellMethod("remote")
    public void getRemoteTag() throws IOException, GitAPIException {
        List<File> gits = new ArrayList<>();
        String currentDir = System.getProperties().getProperty("user.dir");
        File projectFile = new File(currentDir);
        List<File> localGitRepository = FileUtil.findLocalGitRepository(projectFile, gits);
        System.out.println("当前目录共 " + localGitRepository.size() + " 个仓库");
        for (File file : localGitRepository) {
            Git open = Git.open(file);
            List<RemoteConfig> call = open.remoteList().call();
            for (RemoteConfig remoteConfig : call) {
                String name = remoteConfig.getName();
                System.out.println(name);
            }

        }
    }
    @ShellMethod("pull code")
    public void pull(@ShellOption("-b")String branch) throws IOException, GitAPIException {
        List<File> gits = new ArrayList<>();
        String currentDir = System.getProperties().getProperty("user.dir");
        File projectFile = new File(currentDir);
        List<File> localGitRepository = FileUtil.findLocalGitRepository(projectFile, gits);
        System.out.println("当前目录共 " + localGitRepository.size() + " 个仓库");
        for (File file : localGitRepository) {
            Git git = Git.open(file);
            String execute = checkOutAndPull.execute(git, branch, "");
            System.out.println(execute);
        }
    }


}
