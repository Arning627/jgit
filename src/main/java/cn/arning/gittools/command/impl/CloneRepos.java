package cn.arning.gittools.command.impl;

import cn.arning.gittools.command.Clone;
import cn.arning.gittools.conf.GitAuthentication;
import cn.arning.gittools.shell.Method;
import cn.arning.gittools.utils.Result;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.concurrent.Future;

/**
 * @author arning
 */
@Component
public class CloneRepos implements Clone {


    @Override
    @Async
    public void clone(String url, String rootPath) {
        String substring = url.substring(url.lastIndexOf("/"));
        String projectPath = rootPath + substring;
        File file = new File(projectPath);
        try {
            Git.cloneRepository().setDirectory(file).setURI(url).setCredentialsProvider(GitAuthentication.authentication()).call();
//            return Future < Result.success() >;

        } catch (GitAPIException e) {
            System.out.println(e);
            Method.errorPath.add(url);
        }

        System.out.println(Thread.currentThread().getName());
        System.out.printf("%s完成...\n", substring);

//        return null;

    }
}
