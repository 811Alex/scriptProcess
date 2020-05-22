package eu.gflash.scriptProcess;

import java.util.ArrayList;
import java.util.List;

public class ArgParser {
	private String[] startCommand, endCommand;
	private String processName;

	public ArgParser(String[] args){
		List<String> argList = new ArrayList<>(List.of(args));
		if(argList.isEmpty() || argList.get(0).equals("--help") || argList.get(0).equals("-h")){
			System.out.println("This program executes a command, then checks a certain process and executes another command when the process terminates.");
			System.out.println("Usage: scriptProcess.jar --startCmd <command & arguments> --procName <process name> --endCmd <command & arguments>");
			System.exit(0);
		}
		String type = argList.remove(0);
		if(isProgArg(type)){
			do{
				String arg = null;
				ArrayList<String> tempArgList = new ArrayList<>();
				while(!argList.isEmpty() && !isProgArg(arg = argList.remove(0))){
					tempArgList.add(arg);
				}
				switch(type){
					case "--startCmd":
						startCommand = tempArgList.toArray(new String[0]);
						break;
					case "--procName":
						processName = tempArgList.get(0);
						break;
					case "--endCmd":
						endCommand = tempArgList.toArray(new String[0]);
						break;
				}
				if(!argList.isEmpty())
					type = arg;
			}while(!argList.isEmpty());
		}
		else{
			System.out.println("Unknown argument \"" + type + "\"\nFor usage, enter scriptProcess.jar --help");
			System.exit(0);
		}
	}

	private boolean isProgArg(String arg){
		switch(arg){
			case "--startCmd":
			case "--procName":
			case "--endCmd":
				return true;
		}
		return false;
	}

	public String[] getStartCommand(){return startCommand;}
	public String[] getEndCommand(){return endCommand;}
	public String getProcessName(){return processName;}
}
