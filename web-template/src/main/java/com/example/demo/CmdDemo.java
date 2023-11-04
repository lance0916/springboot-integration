package com.example.demo;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * @author WuQinglong
 * @since 2023/2/15 07:40
 */
public class CmdDemo {

    public static void main(String[] args) throws Exception {
        File workDir = new File(System.getenv("HOME"), "/JoplinData");
        List<String> cmdList = Arrays.asList(
            "git add .",
            "git commit -m \"commit\"",
            "git push"
        );
        for (String cmd : cmdList) {
            int retCode = execCmd(cmd, workDir);
            if (retCode != 0) {
                System.out.println(cmd + "：退出码" + retCode);
                break;
            }
        }
    }

    private static int execCmd(String cmd, File file) throws Exception {
        Process process = Runtime.getRuntime().exec(cmd, null, file);
        return process.waitFor();
    }

}
