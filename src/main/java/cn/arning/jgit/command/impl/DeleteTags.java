package cn.arning.jgit.command.impl;

import cn.arning.jgit.command.Execute;
import cn.arning.jgit.conf.GitAuthentication;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author arning
 */
@Component
public class DeleteTags implements Execute {




    @Override
    public String execute(Git git, String message, String version) throws GitAPIException, IOException {

        Map<String, Ref> tags = git.getRepository().getTags();

        GitAuthentication authentication = GitAuthentication.authentication();


        for (String s : tags.keySet()) {
            System.out.println(s);
            Ref ref = tags.get(s);
        }

        List<Ref> tagList = git.tagList().call();
        for (Ref ref : tagList) {
            String[] tag = ref.getName().split("/");
            if (version.equals(tag[tag.length - 1])) {
                RefUpdate refUpdate = git.getRepository().updateRef(ref.getName());
                refUpdate.setForceUpdate(true);
                refUpdate.delete();
            }
        }

        git.push().setPushTags().setRemote("origin").setCredentialsProvider(authentication).call();
        return "success";

    }
}
