package element;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * The {@code GameLogger} class represents the log of this
 * project. All the methods about the log are implemented
 * in this class. 
 *
 * @author	 Zhengtian CHU - modified
 * @since		1.0
 */
public class GameLogger extends Logger {
    private static Logger m_Logger = Logger.getLogger("GameLogger");
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private Calendar calendar = Calendar.getInstance();

    /**
     * Constructs a logger named {@code "GameLogger"} 
     * and output the log into the file {@code directory}.
     *
     * @see	java.io.File#mkdirs()
     * @see 	java.util.logging.Logger
     * @see 	java.util.logging.SimpleFormatter#SimpleFormatter()
     * @see	java.util.logging.FileHandler#FileHandler(String)
     */
    public GameLogger() {
        super("GameLogger", null);
        File directory = new File(System.getProperty("user.dir") + "/" + "logs");
        directory.mkdirs();
        
        m_Logger.setUseParentHandlers(false);
        FileHandler fh = null;
		try {
			fh = new FileHandler(directory + "/" + GameEngine.GAME_NAME + ".log");
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
        m_Logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        assert fh != null;
        fh.setFormatter(formatter);
    }

    private String createFormattedMessage(String message) {
        return dateFormat.format(calendar.getTime()) + " -- " + message;
    }

    /**
     *  Logs a message with level {@code info}.
     *  
     *  @param message The string message
     *  @see 	java.util.logging.Logger#info(String)
     */
    public void Info(String message) { m_Logger.info(createFormattedMessage(message));
    }

    /**
     * Logs a message with level {@code warning}.
     * 
     * @param message The string message
     * @see 	java.util.logging.Logger#warning(String)
     */
    public void Warning(String message) {
        m_Logger.warning(createFormattedMessage(message));
    }

    /**
     * Logs a message with level {@code severe}.
     * 
     * @param message The string message
     * @see 	java.util.logging.Logger#severe(String)
     */
    public void Severe(String message) {
        m_Logger.severe(createFormattedMessage(message));
    }
}