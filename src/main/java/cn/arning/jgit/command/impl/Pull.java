package cn.arning.jgit.command.impl;

import cn.arning.jgit.command.Execute;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.stereotype.Component;

/**
 * @author arning
 */
@Component
public class Pull implements Execute {


    @Override
    public String execute(Git git, String message, String version) throws GitAPIException {
        PullResult call = git.pull().setRemote(message).call();
        boolean successful = call.isSuccessful();
        System.out.println(successful);
        return null;
    }
}
