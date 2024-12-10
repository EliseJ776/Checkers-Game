package cpsc2150.extendedCheckers.models;

import cpsc2150.extendedCheckers.util.DirectionEnum;
import cpsc2150.extendedCheckers.views.CheckersFE;

import java.util.ArrayList;
import java.util.HashMap;

import static cpsc2150.extendedCheckers.views.CheckersFE.getBoardDimensions;

/**
 * Entity object which represents the checkerboard. Contains functions that create and update
 * the pieces on the board according to the actions of the player.
 * @invariant (pieceCount<[Character is x, X, y, or Y], [0 <= Integer <= 8]>)
 * @corresponds self = board
 */

public class CheckerBoard extends AbsCheckerBoard
{
    /**
     * A 2D array of characters used to represent our checkerboard.
     */
    private char[][] board;

    /**
     * A HashMap, with a Character key and an Integer value, that is used to map a player's char to the number of
     * tokens that player still has left on the board.
     */
    private HashMap<Character, Integer> pieceCount;

    /**
     * A HashMap, with a Character key and an ArrayList of DirectionEnums value, used to map a player (and its king
     * representation) to the directions that player can viably move in. A non-kinged (standard) piece can only move
     * in the diagonal directions away from its starting position. A kinged piece can move in the same directions the
     * standard piece can move in plus the opposite directions the standard piece can move in.
     */
    private HashMap<Character, ArrayList<DirectionEnum>> viableDirections;
    public static char PLAYER_ONE = CheckersFE.getPlayerOne();
    public static char PLAYER_TWO = CheckersFE.getPlayerTwo();
    public static final char EMPTY_POS = ' ';
    public static final char BLACK_TILE = '*';

    /**
    Standard Checkers starts with 8 rows and 8 columns. Both players begin with 12 pieces each.
     */
    private int ROW_NUM = getBoardDimensions();
    private int COL_NUM = getBoardDimensions();
    private int STARTING_COUNT = 12;

    /**
     * Constructor for a CheckerBoard object. The constructor should initialize the three instance variables to
     * a new data structure of their respective type. Furthermore, the constructor should use the pieceCount HashMap
     * to map the starting count of tokens to each player, and use the viableDirections HashMap to map the players to
     * their respective starting directions (SE and SW for player one, NE and NW for player two). Finally, the
     * constructor should also initialize all the indices within the checkerboard to either a player char, an asterisk
     * (a 'black, non-playable' position), or a space (the 'empty' position).
     *
     * @param aDimension The size of the board (number of rows and columns)
     * @pre none
     * @post [initializes the three instance variables to a new data structure of their respective type] AND
     * [uses the pieceCount HashMap to map the starting count of tokens to each player] AND [use the viableDirections
     * HashMap to map the players to their respective starting directions (SE and SW for player one, NE and NW for
     * player two)] AND [initialize all the indices within the checkerboard to either a player char, an asterisk]
     */

    public CheckerBoard(int aDimension)
    {

        //initializes board and puts correct pieces for starting board
        ROW_NUM = aDimension;
        COL_NUM = aDimension;
        STARTING_COUNT = ((aDimension/2)*((aDimension - 2) / 2));

        board = new char[ROW_NUM][COL_NUM];
        for (int row = 0; row < ROW_NUM; row++) {
            for (int col = 0; col < COL_NUM; col++) {
                if (row < ((aDimension - 2) / 2) && (row + col) % 2 == 0) {
                    board[row][col] = PLAYER_ONE;
                } else if (row > ((aDimension - 2) / 2 + 1) && (row + col) % 2 == 0) {
                    board[row][col] = PLAYER_TWO;
                } else if ((row + col) % 2 == 0) {
                    board[row][col] = EMPTY_POS;
                } else {
                    board[row][col] = BLACK_TILE;
                }
            }
        }
        //initializes pieceCounts for both players
        pieceCount = new HashMap<>();
        pieceCount.put(PLAYER_ONE, STARTING_COUNT);
        pieceCount.put(PLAYER_TWO, STARTING_COUNT);

        //initializes viableDirections
        viableDirections = new HashMap<>();

        //directions for player 1
        ArrayList<DirectionEnum> playerOneDirs = new ArrayList<>();
        playerOneDirs.add(DirectionEnum.SE);
        playerOneDirs.add(DirectionEnum.SW);
        viableDirections.put(PLAYER_ONE, playerOneDirs);
        //directions for player 2
        ArrayList<DirectionEnum> playerTwoDirs = new ArrayList<>();
        playerTwoDirs.add(DirectionEnum.NE);
        playerTwoDirs.add(DirectionEnum.NW);
        viableDirections.put(PLAYER_TWO, playerTwoDirs);

        //directions for the kinged pieces
        ArrayList<DirectionEnum> kingDirs = new ArrayList<>(playerOneDirs);
        kingDirs.addAll(playerTwoDirs);
        viableDirections.put(Character.toUpperCase(PLAYER_ONE), kingDirs);
        viableDirections.put(Character.toUpperCase(PLAYER_TWO), kingDirs);

    }

    /**
     * Retrieves the viable directions for each player
     * @return A HashMap where each key is a character which represents a player, the matching value being
     * a list of viable directions (NE, NW, SE, SW) for that player
     * @post Returns the viableDirections map, containing the directions for the player
     */
    public HashMap<Character, ArrayList<DirectionEnum>> getViableDirections() {
        return viableDirections;
    }

    /**
     * Retrieves the piece counts for both players
     * @return A HashMap where each key is a character which represents a player, the matching value being the
     * number of pieces that specific player has.
     * @post Returns the pieceCount map, containing the piece counts of both players
     */
    public HashMap<Character, Integer> getPieceCounts() {
        return pieceCount;
    }

    /**
     * Mutator method that places a player's piece on the board on the board 2D array. Sets a given 2D index within the
     * board 2D array, given by the row and col of the parameter BoardPosition, equal to the char given by player.
     * @param pos The position given, an object of class BoardPosition
     * @param player the symbol representing a player, a char
     * @pre The row and column for BoardPosition must be within bounds
     * @pre The position must not already contain a character
     * @post The board at the given position will be updated to contain the player's piece
     */
    public void placePiece(BoardPosition pos, char player) {

        int r;
        int c;

        r = pos.getRow();
        c = pos.getColumn();
        board[r][c] = player;
    }

    /**
     * Accessor method to get the character value of the specific position for the board 2D array
     * @param pos The position the function is checking, an object of class BoardPosition
     * @return The character at the given position on the board
     * @pre The row and column for BoardPosition must be within bounds
     * @post Returns the position given by the row and col of the BoardPosition parameter
     */

    public char whatsAtPos(BoardPosition pos) {
        return board[pos.getRow()][pos.getColumn()];
    }

    /**
     * Updates the number of pieces for the player by adding the pieces together
     * @param player The character that represents the specific player
     * @param numPieces The number of pieces to be added to the player's count
     * @pre player must be pre-existing
     * @pre numPieces must be non-negative integer
     * @post The piece count will be added to numPieces
     */
    public void setPieceCount(char player, int numPieces)
    {
        int pieces = getPieceCounts().get(player);
        pieces = pieces + numPieces;

        pieceCount.put(player, pieces);
    }

    public int getRowNum()
    {
        return ROW_NUM;
    }

    public int getColNum()
    {
        return COL_NUM;
    }



}

