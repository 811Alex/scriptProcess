package eu.gflash.scriptProcess;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class FindProcRun implements Runnable {
	private final String processName;
	private final Consumer<ProcessHandle> callback;

	public FindProcRun(String processName, Consumer<ProcessHandle> callback){
		this.processName = processName;
		this.callback = callback;
	}

	@Override
	public void run() {
		Optional<ProcessHandle> optProc = findProc(processName).findFirst();
		if(!optProc.isEmpty())
			callback.accept(optProc.get());
	}

	private static Stream<ProcessHandle> findProc(String processName){
		return ProcessHandle.allProcesses().filter(ph ->{
			if(ph.isAlive()){
				Optional<String> cmd = ph.info().command();
				if(cmd.isPresent()) return cmd.get().endsWith(processName);
			}
			return false;
		});
	}
}
