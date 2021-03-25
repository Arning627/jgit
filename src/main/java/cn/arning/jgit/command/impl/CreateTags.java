package cn.arning.jgit.command.impl;

import cn.arning.jgit.conf.GitAuthentication;
import cn.arning.jgit.command.Execute;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.transport.PushResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author arning
 */
@Component
public class CreateTags implements Execute {

    @Autowired
    private GitAuthentication gitAuthentication;

    @Override
    public String execute(Git git, String message, String version) {
        String messages = "";
        try {
            List<Ref> localTags = git.tagList().call();
            for (Ref localTag : localTags) {
                String tagName = localTag.getName();
                String[] tag = tagName.split("/");
                if (version.equals(tag[tag.length - 1])) {
                    return "tag" + version + "已存在";
                }
            }
            git.tag().setMessage(message).setName(version).call();
            Iterable<PushResult> origin = git.push().setPushTags().setRemote("origin").setCredentialsProvider(gitAuthentication).call();
            messages = origin.iterator().next().getMessages();

        } catch (GitAPIException e) {
            messages = "创建tag异常";
            System.out.println(e);
        }
        return messages;
    }
}
