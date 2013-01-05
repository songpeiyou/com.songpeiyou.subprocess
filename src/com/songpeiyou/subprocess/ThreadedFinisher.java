/**
 * @author Peiyou Song (SongPeiyou@gmail.com)
 */
package com.songpeiyou.subprocess;

import java.io.IOException;

class ThreadedFinisher extends Thread {

	SystemCommandExecutor exe;

	ThreadedFinisher(SystemCommandExecutor exe) {
		this.exe = exe;
	}

	public void run() {
		long startMillis = System.nanoTime()/1000000;
		while(true){
			if (exe.getTimeoutSec()>0 && (System.nanoTime()/1000000 - startMillis) > exe.getTimeoutSec()*1000){
				try {
					exe.kill();
				} catch (InterruptedException | IOException e) {
					e.printStackTrace();
				}
			}
			
			if (exe.exitValue()!=SystemCommandExecutor.NotFinish) {
				if (exe.getCallback()!= null){
					exe.getCallback().callback(new Object[]{exe});
//					System.out.println(exe.getStandardOutputFromCommand().toString());
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
