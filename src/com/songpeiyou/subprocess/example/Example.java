package com.songpeiyou.subprocess.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.songpeiyou.subprocess.SystemCommandExecutor;


public class Example {

  /**
   * @param args
   * @throws InterruptedException
   * @throws IOException
   */
  public static void main(String[] args) throws IOException, InterruptedException {
    String url = String.format("http://www.google.com");
    String[] cmdArray =
        new String[] {"curl", "-s", "-I", "--user-agent",
            "\"Mozilla/5.0 (X11; Linux x86_64; rv:7.0.1) Gecko/20100101 Firefox/7.0.1\"", url};

    // String[] cmdArray = new String[] {"ls", "-altr"};
    List<String> cmd = new ArrayList<>();
    Collections.addAll(cmd, cmdArray);
    SystemCommandExecutor cmdExe = new SystemCommandExecutor(cmd);
    cmdExe.putInfo("aString", "stringvalue");
    cmdExe.putInfo("anInt", 100);
    cmdExe.setCallback(new CallbackExample());
    cmdExe.setTimeoutSec(90);

    // Run the command in foreground
    cmdExe.executeCommand(false);

    // You can access the results or errors from
    // getStandardErrorFromCommand().toString()
    // and getStandardOutputFromCommand().toString()
    // In this case you do not have to set he callback class
    System.out.println("err");
    System.out.println(cmdExe.getStandardErrorFromCommand().toString());
    System.out.println("out");
    System.out.println(cmdExe.getStandardOutputFromCommand().toString());

    // You could also run it in background and this thread can go ahead to do some other tasks.
    // In this case you would better set a call backcalss to deal with the return results.
    cmdExe.executeCommand(true);

    // you can do other tasks
    System.out.println("I am doint other tasks.. bla.bla.bla...");

    // But do not finish this main thread before all your sub process finished,
    // otherwise the results will be lost.
    // Here I only emit one sub process, so I just wait for that to finish.
    while (cmdExe.exitValue() == SystemCommandExecutor.NotFinish) {
      Thread.sleep(1000);
    }

    // Plese wait a little bit more for the callback function to finish.
    // To check whether the callback is finished is a TODO.
    Thread.sleep(1000);
    System.out
        .println("Check whether there is a file named \"output\" generated in your current directory.");

  }

}
