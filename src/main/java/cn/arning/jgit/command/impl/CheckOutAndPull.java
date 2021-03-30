package cn.arning.jgit.command.impl;

import cn.arning.jgit.command.Execute;
import cn.arning.jgit.conf.GitAuthentication;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.springframework.stereotype.Component;

/**
 * @author arning
 */
@Component
public class CheckOutAndPull implements Execute {


    @Override
    public String execute(Git git, String message, String version) throws GitAPIException {
        GitAuthentication authentication = GitAuthentication.authentication();
        git.checkout().setName(message).call();
        git.pull().call();
        return null;
    }
}
