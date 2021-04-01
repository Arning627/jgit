package cn.arning.jgit.command.impl;

import cn.arning.jgit.command.Execute;
import cn.arning.jgit.conf.GitAuthentication;
import cn.arning.jgit.shell.Method;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author arning
 */
@Component
public class CheckOutAndPull implements Execute {


    @Override
    public void execute(Git git, String branch, String version) {
        Repository repository = git.getRepository();
        String projectName = repository.getDirectory().getParentFile().getName();
        try {
            git.checkout().setName(branch).call();
            git.pull().setCredentialsProvider(GitAuthentication.authentication()).call();
            System.out.println(projectName + "检出完成,当前分支为 " + repository.getBranch());
        } catch (GitAPIException | IOException firstException) {
            System.out.println(firstException);
            Method.errorPath.add(projectName);
        }
    }
}
