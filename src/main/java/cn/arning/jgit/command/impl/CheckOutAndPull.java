package cn.arning.jgit.command.impl;

import cn.arning.jgit.command.Execute;
import cn.arning.jgit.conf.GitAuthentication;
import cn.arning.jgit.shell.Method;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

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
            List<Ref> branchList = git.branchList().setListMode(ListBranchCommand.ListMode.ALL).call();
            for (Ref ref : branchList) {
                if (ref.getName().endsWith(branch)) {
                    git.branchCreate().setStartPoint(ref.getName()).setName(branch).call();
                    git.checkout().setName(branch).call();
                    git.pull().setCredentialsProvider(GitAuthentication.authentication()).call();
                    break;
                }
            }
            System.out.println(projectName + "检出完成,当前分支为 " + repository.getBranch());
        } catch (GitAPIException | IOException firstException) {
            System.out.println(firstException);
            Method.errorPath.add(projectName);
        }
    }
}
