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
  StringBuffer outputBuffer = new StringBuffer();
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

  @Override
  public void run() {

    try (InputStreamReader isr = new InputStreamReader(this.inputStream);
        BufferedReader bufferedReader = new BufferedReader(isr)) {
      String line = null;
      while (this.inputStream != null && (line = bufferedReader.readLine()) != null) {
        this.outputBuffer.append(line + "\n");
        if (this.printoutput) {
          this.printWriter.println(line);
          // System.out.println(line);
        }
        if (this.printerr) {
          System.err.println(line);
        }
      }
    } catch (IOException e) {
      if (!"Stream closed".equals(e.getMessage())) e.printStackTrace();
    }
  }

  public StringBuffer getOutputBuffer() {
    return this.outputBuffer;
  }

}
