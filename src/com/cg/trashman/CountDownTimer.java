package com.cg.trashman;

public class CountDownTimer {
	private long endTime;
	private boolean isStart = false;
	private long millisTime = 0;
	private long millisCountDown;

	public CountDownTimer(int minuteCountDown) {
		millisCountDown = (minuteCountDown * 1000) + 999;
	}

	public long getTime() {
		if (!isStart) {
			endTime = System.currentTimeMillis() + millisCountDown;
			isStart = true;
		}
		millisTime = (endTime - System.currentTimeMillis());
		return millisTime / 1000;
	}

	public float getPercent() {
		return 1f * millisTime / millisCountDown;
	}
}
