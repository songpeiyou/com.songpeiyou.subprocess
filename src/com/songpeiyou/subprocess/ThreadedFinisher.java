/**
 * @author Peiyou Song (SongPeiyou@gmail.com)
 */
package com.songpeiyou.subprocess;


class ThreadedFinisher extends Thread {

  SystemCommandExecutor exe;

  ThreadedFinisher(SystemCommandExecutor exe) {
    this.exe = exe;
  }

  @Override
  public void run() {
    long startMillis = System.nanoTime() / 1000000;
    while (true) {
      if (this.exe.getTimeoutSec() > 0
          && (System.nanoTime() / 1000000 - startMillis) > this.exe.getTimeoutSec() * 1000) {
        try {
          this.exe.kill();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }

      if (this.exe.exitValue() != SystemCommandExecutor.NotFinish) {
        if (this.exe.getCallback() != null) {
          this.exe.getCallback().callback(new Object[] {this.exe});
          // System.out.println(exe.getStandardOutputFromCommand().toString());
        }
        break;
      }

      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
        break;
      }
    }
  }
}
