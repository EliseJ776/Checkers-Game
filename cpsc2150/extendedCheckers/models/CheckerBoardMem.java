
package cpsc2150.extendedCheckers.models;

import cpsc2150.extendedCheckers.util.DirectionEnum;
import cpsc2150.extendedCheckers.views.CheckersFE;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import static cpsc2150.extendedCheckers.views.CheckersFE.*;

public class CheckerBoardMem extends AbsCheckerBoard{

    private Map<Character, List<BoardPosition>> boardMap;
    private HashMap<Character, Integer> pieceCount;
    private HashMap<Character, ArrayList<DirectionEnum>> viableDirections;


    public static char PLAYER_ONE = CheckersFE.getPlayerOne();
    public static char PLAYER_TWO = getPlayerTwo();
    public static final char EMPTY_POS = ' ';
    public static final char BLACK_TILE = '*';

    private int ROW_NUM = getBoardDimensions();
    private int COL_NUM = getBoardDimensions();
    private int STARTING_COUNT = 12;

    /**
     * Constructor for the memory-conscious checkerboard. Initializes the board, sets up the players'
     * pieces and viable directions to move in.
     *
     * @param aDimension The size of the board (number of rows and columns)
     * @pre none
     * @post [initializes boardMap, pieceCount, and viableDirections to  their respective data structure type] AND
     *      [use the viableDirections HashMap to map the players to their respective starting directions
     *      (SE and SW for player one, NE and NW for player two)] AND [sets non-playable tiles as BLACK_TILE and
     *      empty positions as EMPTY_POS] AND [maps starting piece counts to both players] AND [maps valid directions
     *      for both players and their kinged pieces]
     */
    public CheckerBoardMem(int aDimension) {
        ROW_NUM = aDimension;
        COL_NUM = aDimension;
        STARTING_COUNT = ((aDimension/2)*((aDimension/2)-1));
        char upperP1 = Character.toUpperCase(PLAYER_ONE);
        char upperP2 = Character.toUpperCase(PLAYER_TWO);

        // Initialize the board and set up boardMap and pieceCounts for both players
        boardMap = new HashMap<>();
        boardMap.put(PLAYER_ONE, new ArrayList<>());
        boardMap.put(PLAYER_TWO, new ArrayList<>());
        boardMap.put(upperP1, new ArrayList<>());
        boardMap.put(upperP2, new ArrayList<>());

        pieceCount = new HashMap<>();
        pieceCount.put(PLAYER_ONE, STARTING_COUNT);
        pieceCount.put(PLAYER_TWO, STARTING_COUNT);

        viableDirections = new HashMap<>();

        // Directions for player 1
        ArrayList<DirectionEnum> playerOneDirs = new ArrayList<>();
        playerOneDirs.add(DirectionEnum.SE);
        playerOneDirs.add(DirectionEnum.SW);
        viableDirections.put(PLAYER_ONE, playerOneDirs);

        // Directions for player 2
        ArrayList<DirectionEnum> playerTwoDirs = new ArrayList<>();
        playerTwoDirs.add(DirectionEnum.NE);
        playerTwoDirs.add(DirectionEnum.NW);
        viableDirections.put(PLAYER_TWO, playerTwoDirs);

        // Directions for the kinged pieces
        ArrayList<DirectionEnum> kingDirs = new ArrayList<>(playerOneDirs);
        kingDirs.addAll(playerTwoDirs);
        viableDirections.put(Character.toUpperCase(PLAYER_ONE), kingDirs);
        viableDirections.put(Character.toUpperCase(PLAYER_TWO), kingDirs);

        // Loop through the board and place pieces or black tiles
        for (int row = 0; row < ROW_NUM; row++) {
            for (int col = 0; col < COL_NUM; col++) {
                BoardPosition pos = new BoardPosition(row, col);

                // Check if it's a black tile (non-playable position)
                if ((col % 2 == 0 && row % 2 == 1) || (col % 2 == 1 && row % 2 == 0))
                {

                }
                else
                {
                    // Assign pieces for player 1 and player 2 at appropriate rows
                    if (row < ((aDimension - 2) / 2)) {
                        // Player 1's pieces
                        boardMap.get(PLAYER_ONE).add(pos);
                    } else if (row > ((aDimension - 2) / 2) + 1) {
                        // Player 2's pieces
                        boardMap.get(PLAYER_TWO).add(pos);
                    }
                }
            }
        }
    }

    /**
     * Retrieves the viable directions for each player
     * @return A HashMap where each key is a character which represents a player, the matching value being
     * a list of viable directions (NE, NW, SE, SW) for that player
     * @post Returns the viableDirections map, containing the directions for the player
     */
    @Override
    public HashMap<Character, ArrayList<DirectionEnum>> getViableDirections() {
        return viableDirections;
    }

    /**
     * Retrieves the piece counts for both players
     * @return A HashMap where each key is a character which represents a player, the matching value being the
     * number of pieces that specific player has.
     * @post Returns the pieceCount map, containing the piece counts of both players
     */
    @Override
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
    @Override
    public void placePiece(BoardPosition pos, char player)
    {
        if (pos.getRow() < 0 || pos.getRow() >= ROW_NUM || pos.getColumn() < 0 || pos.getColumn() >= COL_NUM) {
            return;
        }

        char currentPiece = whatsAtPos(pos);

        if (currentPiece != EMPTY_POS && currentPiece != BLACK_TILE) {
            boardMap.get(currentPiece).remove(pos);
        }

        if (player != EMPTY_POS && player != BLACK_TILE) {
            boardMap.get(player).add(pos);
        }

    }

    /**
     * Accessor method to get the character value of the specific position for the board 2D array
     * @param pos The position the function is checking, an object of class BoardPosition
     * @return The character at the given position on the board
     * @pre The row and column for BoardPosition must be within bounds
     * @post Returns the position given by the row and col of the BoardPosition parameter
     */
    @Override
    public char whatsAtPos(BoardPosition pos) {
        for (Map.Entry<Character, List<BoardPosition>> entry : boardMap.entrySet()) {
            if (entry.getValue().contains(pos)) {
                return entry.getKey();
            }
        }
        if((pos.getColumn() % 2 == 0 && pos.getRow() % 2 == 1) || (pos.getColumn() % 2 == 1 && pos.getRow() % 2 == 0))
        {
            return BLACK_TILE;
        }
        return EMPTY_POS;
    }

    /**
     * Updates the number of pieces for the player by adding the pieces together
     * @param player The character that represents the specific player
     * @param numPieces The number of pieces to be added to the player's count
     * @pre player must be pre-existing
     * @pre numPieces must be non-negative integer
     * @post The piece count will be added to numPieces
     */
    @Override
    public void setPieceCount(char player, int numPieces) {
        int pieces = pieceCount.get(player);
        pieces += numPieces;
        pieceCount.put(player, pieces);
    }

    /**
     * Retrieves the number of rows on the board.
     * @return The number of rows.
     */
    public int getRowNum() {
        return ROW_NUM;
    }

    /**
     * Retrieves the number of columns on the board.
     * @return The number of columns.
     */
    public int getColNum() {
        return COL_NUM;
    }



}
