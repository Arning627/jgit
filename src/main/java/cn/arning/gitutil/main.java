package cn.arning.gitutil;

import com.sun.org.apache.xpath.internal.operations.Gt;
import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.attributes.Attribute;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import javax.sound.midi.Soundbank;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class main {

    public static void main(String[] args) throws IOException, GitAPIException {
        UsernamePasswordCredentialsProvider up = new UsernamePasswordCredentialsProvider("", "#");
        File file = new File("/tmp/gitTest/");
        File localGitRepository = findLocalGitRepository(file);
        Git git = Git.open(localGitRepository);
//        createTags(git, "v2.0", "2.0 test");
//        git.push().setPushTags().setCredentialsProvider(up).call();

        push(git,up,"master","test again");

//        File ccc = new File("/Users/arning/Desktop/tmp/gitTest/ccc.txt");
//        FileWriter fileWriter = new FileWriter(ccc);
//        fileWriter.write("test");
//        fileWriter.close();
//        Git git = Git.open(file);
//        List<DiffEntry> call = git.diff().call();
//        System.out.println(call.size());
//        if (call.size() > 0) {
//            for (DiffEntry diffEntry : call) {
//                String newPath = diffEntry.getNewPath();
//                git.add().addFilepattern(newPath).call();
//                RevCommit test_commit = git.commit().setMessage("test commit").call();
//                Iterable<PushResult> call2 = git.push().setCredentialsProvider(up).setRemote("origin").add("master").call();
//                Iterator<PushResult> iterator = call2.iterator();
//                while (iterator.hasNext()) {
//                    PushResult next = iterator.next();
//                    String messages = next.getMessages();
//                    System.out.println(messages);
//                }
//
//            }
//        }

    }


    //遍历工程目录获取本地git仓库
    public static File findLocalGitRepository(File project) {
        File[] files = project.listFiles();
        if (files.length > 0) {
            for (File file : files) {
                if (".git".equals(file.getName())) {
                    return file;
                }
                if (file.isFile()) {
                    continue;
                } else {
                    findLocalGitRepository(file);
                }
            }
        }
        return null;
    }

    //拉取代码
    private static void pull(Git git, CredentialsProvider credentialsProvider) throws GitAPIException {
        git.pull().setCredentialsProvider(credentialsProvider).call();
    }

    //检出分支
    private static void switchBranch(Git git, String branch) throws GitAPIException {
        git.checkout().addPath(branch).call();
    }

    //创建tag
    private static void createTags(Git git, String tag, String message) throws GitAPIException {
        git.tag().setName(tag).setMessage(message).call();
    }

    private static void push(Git git, CredentialsProvider credentialsProvider, String branch, String message) throws GitAPIException {
        List<DiffEntry> call = git.diff().call();
        if (call.size() > 0) {
            for (DiffEntry diffEntry : call) {
                String newPath = diffEntry.getNewPath();
                git.add().addFilepattern(newPath).call();
            }
            git.commit().setMessage(message).call();
            git.push().setCredentialsProvider(credentialsProvider).setRemote("origin").add(branch).call();
        }

    }

}
