package com.cg.trashman;

public class Score {
	private static Score score = null;
	private int point;

	public static Score getInstance() {
		if (score == null) {
			score = new Score();
		}
		return score;
	}

	private Score() {
		point = 0;
	}

	public void add(int p) {
		point += p;
	}

	public int getScore() {
		return point;
	}

	public void reset() {
		point = 0;
	}
}
