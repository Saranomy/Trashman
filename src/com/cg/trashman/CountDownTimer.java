package com.cg.trashman;

public class CountDownTimer {
	private long endTime;
	private boolean isStart = false;

	public CountDownTimer() {
	}

	public long getTime() {
		if (!isStart) {
			endTime = System.currentTimeMillis() + (12L * 1000L);
			isStart = true;
		}
		long time = (endTime - System.currentTimeMillis()) / 1000;
		return time;
	}
}
