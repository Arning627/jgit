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
    public void execute(Git git, String message, String version) {
        Repository repository = git.getRepository();
        String projectName = repository.getDirectory().getParentFile().getName();
        try {
            git.checkout().setName(message).call();
            git.pull().call();
            String branchName = git.getRepository().getDirectory().getParentFile().getName();
            System.out.println(branchName + "检出完成,当前分支为 " + message);
        } catch (GitAPIException firstException) {
            System.out.println(firstException);
            Method.errorPath.add(projectName);
        }
    }
}
