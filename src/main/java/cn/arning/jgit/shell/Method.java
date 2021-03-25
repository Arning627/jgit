package cn.arning.jgit.shell;


import cn.arning.jgit.command.Execute;
import cn.arning.jgit.utils.FileUtil;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
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
    private Execute pull;

    @ShellMethod("create tags")
    public String tag(@ShellOption("-v") String version, @ShellOption("-m") String message) throws IOException, GitAPIException {
        List<File> gits = new ArrayList<>();
        String currentDir = System.getProperties().getProperty("user.dir");
        File projectFile = new File(currentDir);
        List<File> localGitRepository = FileUtil.findLocalGitRepository(projectFile, gits);
        String execute = "";
        for (File file1 : localGitRepository) {
            Git git = Git.open(file1);
            execute = createTags.execute(git, message, version);
        }
        return execute;
    }


}
