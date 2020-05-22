package eu.gflash.scriptProcess;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final int PROC_SEARCH_INTERVAL = 500;
    private static final int PROC_CHECK_ALIVE_INTERVAL = 2000;

    public static void main(String[] args) {
        ScheduledExecutorService findService = Executors.newSingleThreadScheduledExecutor();
        ScheduledExecutorService waitService = Executors.newSingleThreadScheduledExecutor();

        System.out.println("Started! Parsing arguments.");
        ArgParser argP = new ArgParser(args);

        System.out.println("Executing start command.");
        exec(argP.getStartCommand());
        System.out.println("Looking for specified process...");
        findService.scheduleAtFixedRate(new FindProcRun(argP.getProcessName(), proc->{
            System.out.println("Process found!\nWaiting for termination...");
            waitService.scheduleAtFixedRate(new WaitProcTerm(proc, ()->{
                System.out.println("Process terminated!\nExecuting end command.");
                exec(argP.getEndCommand());
            }), 0, PROC_CHECK_ALIVE_INTERVAL, TimeUnit.MILLISECONDS);
            findService.shutdown();
        }), 0, PROC_SEARCH_INTERVAL, TimeUnit.MILLISECONDS);
    }

    private static void exec(String[] command){
		try {
			new ProcessBuilder(command).start();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Couldn't execute command: " + String.join(" ", command) + "\nCheck if it exists and we have the right permissions.");
			System.exit(1);
		}
	}
}
