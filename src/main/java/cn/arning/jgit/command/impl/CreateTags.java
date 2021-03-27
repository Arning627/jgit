package cn.arning.jgit.command.impl;

import cn.arning.jgit.conf.GitAuthentication;
import cn.arning.jgit.command.Execute;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.PushResult;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author arning
 */
@Component
public class CreateTags implements Execute {


    @Override
    public String execute(Git git, String message, String version) {
        Repository repository = git.getRepository();
        String projectFileName = repository.getDirectory().getParentFile().getName();
        String result = "";
        try {
            Ref ref = repository.getTags().get(version);
            if (null == ref) {
                git.tag().setMessage(message).setName(version).call();
                System.out.println(projectFileName + " local tag " + version + " already exists...");
            }
            GitAuthentication gitAuthentication = GitAuthentication.isAuthentication();
            git.push().setPushTags().setRemote("origin").setCredentialsProvider(gitAuthentication).call();
            result = projectFileName + "====> tag " + version + " pushed...";
        } catch (GitAPIException e) {
            result = projectFileName + "====> create tag error...Deleting local tag ===>" + version;
            try {
                git.tagDelete().setTags(version).call();
            } catch (GitAPIException e1) {
                result = projectFileName + "Local label deletion failed...";
                System.out.println(e1);
            }
            System.out.println(e);
        }
        return result;
    }
}
