package cn.arning.jgit.command.impl;

import cn.arning.jgit.command.Execute;
import cn.arning.jgit.conf.GitAuthentication;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class DeleteTags implements Execute {

    @Autowired
    private GitAuthentication gitAuthentication;

    @Override
    public String execute(Git git, String message, String version) throws GitAPIException, IOException {
        List<Ref> tagList = git.tagList().call();
        for (Ref ref : tagList) {
            String[] tag = ref.getName().split("/");
            if (version.equals(tag[tag.length - 1])) {
                RefUpdate refUpdate = git.getRepository().updateRef(ref.getName());
                refUpdate.setForceUpdate(true);
                refUpdate.delete();


//                List<String> call = git.tagDelete().setTags(ref.getName()).call();
//                for (String s : call) {
//                    System.out.println(s);
//                }
            }
        }
        git.push().setPushTags().setRemote("origin").setCredentialsProvider(gitAuthentication).call();
        return "success";

    }
}
