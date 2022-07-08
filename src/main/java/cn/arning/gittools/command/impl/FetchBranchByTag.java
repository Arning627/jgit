package cn.arning.gittools.command.impl;

import cn.arning.gittools.command.Execute;
import cn.arning.gittools.shell.Method;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xnguo
 * @date 2022/7/8 下午4:33
 */
@Component
public class FetchBranchByTag implements Execute {

    @Override
    public void execute(Git git, String describe, String version) {
        Repository repository = git.getRepository();
        String projectName = repository.getDirectory().getParentFile().getName();
        try {
            List<Ref> tags = git.tagList().call();
            for (Ref tag : tags) {
                if (tag.getName().endsWith(version)) {
                    git.branchCreate()
                            .setName(describe)
                            .setStartPoint(tag.getName())
                            .call();

                    git.checkout()
                            .setName(describe)
                            .call();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Method.errorPath.add(projectName);
        }

    }
}
