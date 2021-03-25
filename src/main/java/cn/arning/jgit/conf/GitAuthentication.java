package cn.arning.jgit.conf;

import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.stereotype.Component;

/**
 * @author arning
 */
@Component
public class GitAuthentication extends UsernamePasswordCredentialsProvider {

    static String git_username = System.getenv("GIT_USERNAME");
    static String git_password = System.getenv("GIT_PASSWORD");

    public GitAuthentication(String username, String password) {
        super(username, password);
    }

    public GitAuthentication(String username, char[] password) {
        super(username, password);
    }

    public GitAuthentication() {
        super(git_username, git_password);
    }
}
