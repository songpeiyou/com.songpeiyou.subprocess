/**
 * @author Peiyou Song (SongPeiyou@gmail.com)
 */
package com.songpeiyou.subprocess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

class ThreadedStreamHandler extends Thread {
	InputStream inputStream;
	OutputStream outputStream;
	PrintWriter printWriter;
	StringBuilder outputBuffer = new StringBuilder();
	boolean printoutput = false;
	boolean printerr = false;
	

	ThreadedStreamHandler(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	ThreadedStreamHandler(InputStream inputStream, OutputStream outputStream, boolean printoutput) {
		this.inputStream = inputStream;
		this.outputStream = outputStream;
		this.printWriter = new PrintWriter(outputStream);
		this.printoutput = printoutput;
	}

	public void run() {

		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				outputBuffer.append(line + "\n");
				if (printoutput){
					this.printWriter.println(line);
//					System.out.println(line);
				}
				if (printerr){
					System.err.println(line);
				}
			}
		} catch (IOException ioe) {
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			try {
				bufferedReader.close();
			} catch (IOException e) {
			}
		}
	}

	public StringBuilder getOutputBuffer() {
		return outputBuffer;
	}

}
