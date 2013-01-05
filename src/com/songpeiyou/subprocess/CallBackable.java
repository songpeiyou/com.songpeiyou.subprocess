/**
 * 
 * @author Peiyou Song (SongPeiyou@gmail.com)
 */
package com.songpeiyou.subprocess;

import java.util.UUID;
import java.util.Vector;

public abstract class CallBackable {
	public UUID uuid = UUID.randomUUID();
	public Vector<SystemCommandExecutor> exeVector = new Vector<>();
	public abstract void callback (Object[] argv);
}
