package eu.gflash.scriptProcess;

public class WaitProcTerm implements Runnable {
	private final ProcessHandle proc;
	private final Runnable callback;

	public WaitProcTerm(ProcessHandle proc, Runnable callback){
		this.proc = proc;
		this.callback = callback;
	}

	@Override
	public void run() {
		if(!proc.isAlive())
			callback.run();
	}
}
