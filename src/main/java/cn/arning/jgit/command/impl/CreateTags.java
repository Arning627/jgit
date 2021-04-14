package cn.arning.jgit.command.impl;

import cn.arning.jgit.conf.GitAuthentication;
import cn.arning.jgit.command.Execute;
import cn.arning.jgit.shell.Method;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author arning
 */
@Component
public class CreateTags implements Execute {


    @Override
    public void execute(Git git, String describe, String version) {
        Repository repository = git.getRepository();
        String projectFileName = repository.getDirectory().getParentFile().getName();
        GitAuthentication gitAuthentication = GitAuthentication.authentication();
        try {
            Ref ref = repository.getTags().get(version);
            if (null == ref) {
                git.tag().setMessage(describe).setName(version).call();
                System.out.printf("%s creating tag %s ...\n", projectFileName, version);
            }
            git.push().setPushTags().setRemote("origin").setCredentialsProvider(gitAuthentication).call();
            System.out.printf("%s====> tag %s pushed...\n", projectFileName, version);
        } catch (GitAPIException firstPushError) {
            try {
                git.push().setPushTags().setRemote("origin").setCredentialsProvider(gitAuthentication).call();
            } catch (GitAPIException secondPushError) {
                Method.errorPath.add(projectFileName);
                System.out.printf("%s====> create tag error...Deleting local tag ===>%s\n", projectFileName, version);
                try {
                    git.tagDelete().setTags(version).call();
                } catch (GitAPIException deleteError) {
                    System.out.printf("%s Local label deletion failed...\n", projectFileName);
                    System.out.println(deleteError);
                }
            }
            System.out.println(firstPushError);
        }

    }
}
