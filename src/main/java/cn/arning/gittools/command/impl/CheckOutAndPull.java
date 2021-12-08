package cn.arning.gittools.command.impl;

import cn.arning.gittools.conf.GitAuthentication;
import cn.arning.gittools.command.Execute;
import cn.arning.gittools.shell.Method;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
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
        boolean flag = true;
        try {
            List<Ref> localBranchList = git.branchList().call();
            for (Ref ref : localBranchList) {
                if (ref.getName().endsWith(branch)) {
                    check(git, branch);
                    flag = false;
                    break;
                }
            }
            if (flag) {
                List<Ref> remoteBranchList = git.branchList().setListMode(ListBranchCommand.ListMode.REMOTE).call();
                for (Ref ref : remoteBranchList) {
                    if (ref.getName().endsWith(branch)) {
                        git.branchCreate().setStartPoint(ref.getName()).setName(branch).call();
                        check(git, branch);
                        break;
                    }
                }
            }
            System.out.printf("%s检出完成,当前分支为%s\n", projectName, repository.getBranch());
        } catch (GitAPIException | IOException firstException) {
            System.out.println(firstException);
            System.out.printf("***%s没有%s分支***\n", projectName, branch);
            Method.errorPath.add(projectName);
        }
    }

    private void check(Git git, String branch) throws GitAPIException {
        GitAuthentication authentication = GitAuthentication.authentication();
        git.checkout().setName(branch).call();
        git.pull().setCredentialsProvider(authentication).call();
    }
}
