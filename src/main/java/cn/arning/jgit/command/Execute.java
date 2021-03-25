package cn.arning.jgit.command;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

/**
 * @author arning
 */
public interface Execute {

    String execute(Git git,String message,String version) throws GitAPIException;

}
