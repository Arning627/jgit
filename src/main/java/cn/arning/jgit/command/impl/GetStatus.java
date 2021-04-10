package cn.arning.jgit.command.impl;

import cn.arning.jgit.command.Execute;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.util.Set;

/**
 * @author arning
 */
public class GetStatus implements Execute {
    @Override
    public void execute(Git git, String describe, String version) {
        try {
            Status call = git.status().call();
            Set<String> modified = call.getModified();
            Set<String> added = call.getAdded();
            Set<String> changed = call.getChanged();
            for (String s : modified) {
                System.err.println("modified===>"+ s);
            }
//            for (String s : added) {
//                System.out.println("added===>"+s);
//            }
//            for (String s : changed) {
//                System.out.println("changed===>"+s);
//            }
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }
}
