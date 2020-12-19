package element;

/**
 * This class store a element called {@code ScoreList}. It is used to record
 * the records for scores. Each list contains the {@code username}, {@code time}
 * and {@code movesCount}.
 *
 * @author Zhengtian CHU
 * @since 2.0
 */
public class ScoreList {
	private String username;
	private int time;
	private int movesCount;
	
	public String GetUsername() {
		return username;
	}

	public void SetUsername(String username) {
		this.username = username;
	}

	public int GetTime() {
		return time;
	}

	public void SetTime(int time) {
		this.time = time;
	}

	public int GetMovesCount() {
		return movesCount;
	}

	public void SetMovesCount(int movesCount) {
		this.movesCount = movesCount;
	}

	/**
	 * The constructor of {@code scoreList}.
	 * @param username the name of player
	 * @param time the playing time
	 * @param movesCount the moves count.
	 */
	public ScoreList(String username, int time, int movesCount) {
		this.username = username;
		this.time = time;
		this.movesCount = movesCount;
	}
}
