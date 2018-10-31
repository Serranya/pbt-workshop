package pbt.exercise3;

import java.time.*;

public class GameTime implements Comparable<GameTime> {

	private final int quarter;
	private final int minute;
	private final int second;

	public static GameTime at(int quarter, int minute, int second) {
		if (quarter < 1 || quarter > 4) {
			throw new IllegalArgumentException("Quarter must be between 1 and 4");
		}
		if (minute < 0 || minute > 9) {
			throw new IllegalArgumentException("Minute must be between 0 and 9");
		}
		if (second < 0 || second > 59) {
			throw new IllegalArgumentException("Second must be between 0 and 59");
		}
		return new GameTime(quarter, minute, second);
	}

	private GameTime(int quarter, int minute, int second) {
		this.quarter = quarter;
		this.minute = minute;
		this.second = second;
	}

	@Override
	public String toString() {
		return String.format("Q%s %02d:%02d", quarter, minute, second);
	}

	public int quarter() {
		return quarter;
	}

	public int minute() {
		return minute;
	}

	public int second() {
		return second;
	}

	public Duration minuteInGame() {
		return Duration.ofMinutes(minute() + (quarter() - 1) * 10).plusSeconds(second());
	}

	@Override
	public int compareTo(GameTime other) {
		return minuteInGame().compareTo(other.minuteInGame());
	}

}