package cn.arning.gittools.command;

import org.eclipse.jgit.api.Git;

/**
 * @author arning
 */
public interface Execute {

    void execute(Git git, String describe, String version);

}
