package cn.arning.jgit.command;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

public interface Execute {

    public String execute(Git git,String message,String version) throws GitAPIException;

}
