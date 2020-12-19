package display;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import element.GameEngine;
import element.ScoreList;

/**
 * This class stores the methods for save and load score board to
 * the text in resources.
 * @author Zhengtian CHU
 * @version 2.0
 */
public class FileHandler {
	public static List<ScoreList> scorelist = new ArrayList<>();

	/**
	 * This function is called when a player has finished the level or finished
	 * the whole game.
	 *
	 * Write the player's name, their playing time, moves count and the index
	 * of level(if whole game is finished, the {@code levelIndex} equals to -1)
	 * to the specific file{@code f}.
 	 * @param username the name of the player
	 * @param time the time cost of the level
	 * @param movesCount the movesCount of the level
	 * @param levelIndex the index of the level
	 * @see FileWriter
	 * @see PrintWriter
	 */
	public static void WriteFile(String username, int time, int movesCount, int levelIndex) {
		String path = null;
		if (levelIndex == -1) {
			path = "src/main/resources/scoreboard/FinalHighScoreBoard.txt";
		} else {
			path = "src/main/resources/scoreboard/Level" + levelIndex + "HighScoreBoard.txt";
		}
		FileWriter fw = null;
		try {
			File f = new File(path);
			fw = new FileWriter(f, true);
		}catch(IOException e) {
			e.printStackTrace();
		}
		assert fw != null;
		PrintWriter pw = new PrintWriter(fw);
		pw.println(username + " " + time + " " + movesCount);
		pw.flush();
		try {
			fw.flush();
			pw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is called after the score list has been write to the text file
	 * and it will load the text file and put the high scores in to a {@code scorelist}.
	 * Then, using a {@code Comparator} to sort the scores by time.
	 *
	 * @param levelIndex the index of the level(if whole game is finished, equals to -1)
	 * @see BufferedReader
	 * @see FileReader
	 * @see ScoreList
	 * @see Comparator
	 */
	public static void ReadFile(int levelIndex) {
		FileHandler.scorelist = new ArrayList<>();
		String path = null;
		if (levelIndex == -1) {
			path = "src/main/resources/scoreboard/FinalHighScoreBoard.txt";
		} else {
			path = "src/main/resources/scoreboard/Level" + levelIndex + "HighScoreBoard.txt";
		}
		try (FileReader reader = new FileReader(path);
			 BufferedReader br = new BufferedReader(reader)
		) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] score = line.split(" ");
				String name = score[0];
				int time = Integer.parseInt(score[1]);
				int moves = Integer.parseInt(score[2]);
				ScoreList temp = new ScoreList(name, time, moves);
				scorelist.add(temp);
			}
			br.close();
			scorelist.sort((s1, s2) -> {
				if (s1.GetTime() > s2.GetTime()) {
					return 1;
				}else if(s1.GetTime() < s2.GetTime()) {
					return -1;
				}else if(s1.GetMovesCount() >= s2.GetMovesCount()) {
					return 1;
				}else {
					return -1;
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * To judge whether this score should be added to the top10 list.
	 *
	 * @param index in level index
	 * @return whether it should be added
	 */
	public static boolean JudgeRanked(int index) {
		int SIZE = 10;
		ReadFile(index);
		if (scorelist.size() < SIZE){
			return true;
		}
		return (scorelist.get(SIZE - 1).GetTime() > GameEngine.GetLevelTime())
				|| (scorelist.get(SIZE - 1).GetTime() == GameEngine.GetLevelTime()
				&& scorelist.get(SIZE - 1).GetMovesCount() > GameEngine.GetCurrentMovesCount());
	}
}



