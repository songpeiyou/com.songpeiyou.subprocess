/**
 * @author Peiyou Song (SongPeiyou@gmail.com)
 */

package com.songpeiyou.subprocess.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.songpeiyou.subprocess.CallBackable;
import com.songpeiyou.subprocess.SystemCommandExecutor;


public class CallbackExample extends CallBackable {

	@Override
	public void callback(Object[] argv) {
		SystemCommandExecutor exe = (SystemCommandExecutor) argv[0];
		StringBuilder out = exe.getStandardOutputFromCommand();
		StringBuilder err = exe.getStandardErrorFromCommand();

		FileWriter fstream;
		try {
			fstream = new FileWriter("output", true);
			BufferedWriter outfile = new BufferedWriter(fstream);

			outfile.write("aString = " + exe.getString("aString") + "\n");
			outfile.write("anInt = " + (Integer) exe.getInfo("anInt") + "\n");
			outfile.write("The err of the command:\n");
			outfile.write(err.toString());
			outfile.write("The output of the command:\n");
			outfile.write(out.toString());
			outfile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
