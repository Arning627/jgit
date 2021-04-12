package cn.arning.jgit.command.impl;

import cn.arning.jgit.command.Clone;
import cn.arning.jgit.conf.GitAuthentication;
import cn.arning.jgit.shell.Method;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author arning
 */
@Component
public class CloneRepos implements Clone {


    @Override
    public void clone(String url, File rootPath) {
        try {
            Git.cloneRepository().setDirectory(rootPath).setURI(url).setCredentialsProvider(GitAuthentication.authentication()).call();
        } catch (GitAPIException e) {
            System.out.println(e);
            Method.errorPath.add(url);
        }
    }
}
