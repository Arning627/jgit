package cn.arning.jgit.command.impl;

import cn.arning.jgit.command.Execute;
import cn.arning.jgit.conf.GitAuthentication;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author arning
 */
@Component
public class CheckOutAndPull implements Execute {


    @Override
    public void execute(Git git, String message, String version) throws GitAPIException, IOException {
        git.checkout().setName(message).call();
        git.pull().call();
        String name = git.getRepository().getDirectory().getParentFile().getName();

    }
}
