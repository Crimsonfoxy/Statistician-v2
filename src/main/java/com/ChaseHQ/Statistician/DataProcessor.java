package com.ChaseHQ.Statistician;

import java.util.ArrayList;
import java.util.TimerTask;

import com.ChaseHQ.Statistician.Stats.IProcessable;

public class DataProcessor extends TimerTask {
	private ArrayList<IProcessable> processList = new ArrayList<IProcessable>();

	public synchronized void addProcessable(IProcessable proc) {
		this.processList.add(proc);
	}

	public synchronized void removeProcessable(IProcessable proc) {
		this.processList.remove(proc);
	}

	@Override
	public synchronized void run() {
		for (IProcessable proc : this.processList) {
			proc._processData();
		}
	}
}
