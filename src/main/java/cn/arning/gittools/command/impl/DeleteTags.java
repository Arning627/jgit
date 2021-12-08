package cn.arning.gittools.command.impl;

import cn.arning.gittools.command.Execute;
import org.eclipse.jgit.api.Git;
import org.springframework.stereotype.Component;

/**
 * @author arning
 */
@Component
public class DeleteTags implements Execute {


    @Override
    public void execute(Git git, String describe, String version) {

//        Map<String, Ref> tags = git.getRepository().getTags();
//
//        GitAuthentication authentication = GitAuthentication.authentication();
//
//
//        for (String s : tags.keySet()) {
//            System.out.println(s);
//            Ref ref = tags.get(s);
//        }
//
//        List<Ref> tagList = git.tagList().call();
//        for (Ref ref : tagList) {
//            String[] tag = ref.getName().split("/");
//            if (version.equals(tag[tag.length - 1])) {
//                RefUpdate refUpdate = git.getRepository().updateRef(ref.getName());
//                refUpdate.setForceUpdate(true);
//                refUpdate.delete();
//            }
//        }
//
//        git.push().setPushTags().setCredentialsProvider(authentication).call();
//

    }
}
