package cn.arning.gittools.conf;

import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

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
