package element;

/**
 * The enumerate class {@code GameObject} represents 
 * objects in Sokoban. All GameObject in this projects, 
 * such as {@code WALL} , are implement as an instance of
 * this class.
 * 
 * @author Zhengtian CHU - modified
 * @since	1.0
 */
public enum GameObject {
    WALL('W'),
    FLOOR(' '), 
    CRATE('C'),
    DIAMOND('D'), 
    KEEPER('S'), 
    CRATE_ON_DIAMOND('O'), 
    DEBUG_OBJECT('='),
    STAR('X'),
    PORTALUP('P'),
    PORTALDOWN('Q'),
    BOMB('B');

    private final char symbol;
    
	/**
	 * Create an instance of {@code GameObject} whose symbol
	 * is the same as specified symbol
	 * @param symbol the specified symbol
	 */
    GameObject(final char symbol) {this.symbol = symbol;}

    /**
     * Returns an object in the game.  
     * @param c the char to be compared with symbols
     * @return {@code t} if the upper-case of {@code c} is
     * 				 included in the symbol; {@code WALL} otherwise.
     */
    public static GameObject FromChar(char c) {
        for (GameObject t : GameObject.values()) {
            if (Character.toUpperCase(c) == t.symbol) {
                return t;
            }
        }
        return WALL;
    }
    
    /**
     * Returns the String representation of  {@code symbol}
     * @return a string of length {@code 1} containing
     *          as its single character the argument {@code symbol}
     */
    public String GetStringSymbol() {return String.valueOf(symbol);}

    /**
     * Returns the {@code symbol}
     * @return char {@code symbol}
     */
    public char GetCharSymbol() {return symbol;}
}