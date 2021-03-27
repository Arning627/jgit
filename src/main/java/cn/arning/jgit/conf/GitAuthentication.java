package cn.arning.jgit.conf;

import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.stereotype.Component;

/**
 * @author arning
 */
public class GitAuthentication extends UsernamePasswordCredentialsProvider {


    public GitAuthentication(String username, String password) {
        super(username, password);
    }

    public GitAuthentication(String username, char[] password) {
        super(username, password);
    }

    public GitAuthentication() {
        super(GitUserConfig.GIT_USERNAME, GitUserConfig.GIT_PASSWORD);
    }

    public static GitAuthentication authentication() {
        return new GitAuthentication();
    }
}
