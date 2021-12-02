package cn.arning.jgit.command;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.IOException;

/**
 * @author arning
 */
public interface Execute {

    void execute(Git git, String describe, String version);

}
