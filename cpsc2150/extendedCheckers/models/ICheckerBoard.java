package cpsc2150.extendedCheckers.models;

import cpsc2150.extendedCheckers.util.DirectionEnum;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * An interface that stores and defines methods to interact with the checkerboard, including those that
 * retrieve the game state, manipulate board pieces, check conditions and change data within the board.
 *
 * @defines board: a data structure that stores piece positions
 * @defines pieceCount: tracks the number of pieces each player has on the board
 * @defines viableDirections: stores the possible directions a player's pieces may move in
 *
 * @initialization_ensures a board with the starting amount of pieces, with each player having a
 *      starting set of instructions
 *
 * @constraint pieces cannot be placed on or moved to black tiles
 * @constraint pieces cannot move outside the board's boundaries
 * @constraint a player's piece count must be equal or greater than zero
 * @constraint a player's piece may not surpass the starting count
 * @constraint when a piece moves, its previous position is opened
 * @constraint jumping over an opponent's piece removes the piece from the board
 * @constraint a piece is only crowned if it reaches the opposite side of the board
 */
public interface ICheckerBoard {

    public static final int BOARD_DIMENSIONS = 8;

    /**
     * Standard getter for viableDirections
     * @return viableDirections, stores directions a piece may move, a HashMap
     * @pre none
     * @post getViableDirections = viableDirections AND viableDirections = #viableDirections
     * AND board = #board AND pieceCount = #pieceCount
     */
    public HashMap<Character, ArrayList<DirectionEnum>> getViableDirections();

    /**
     * Standard getter for pieceCount
     * @return pieceCounts, tracks the number of pieces a player has, a HashMap
     * @pre none
     * @post getPieceCounts = pieceCount AND pieceCount = #pieceCount AND viableDirections = #viableDirections AND board = #board
     */
    public HashMap<Character, Integer> getPieceCounts();

    /**
     * A mutator for the board data structure. Used to place a character piece at a specific position on the board.
     * @param pos The position given, an object of class BoardPosition
     * @param player the symbol representing a player, a char
     * @pre [pos is within the boundaries of the board]
     * @post board = [at the given pos, the char given by the player is placed] AND viableDirections = #viableDirections AND pieceCount = #pieceCount
     */
    public void placePiece(BoardPosition pos, char player);

    /**
     * An accessor for the board data structure. Given a row and column from the BoardPosition parameter,
     * it returns the position accordingly.
     * @param pos The position the function is checking, an object of class BoardPosition
     * @return atPos, what is at the position given by the row and col of the BoardPosition
     *         parameter, a char
     * @pre [pos is within the valid bounds of the board] AND [board is not null]
     * @post whatsAtPos = board[pos.row][pos.column] AND viableDirections = #viableDirections
     * AND board = #board AND pieceCount = #pieceCount
     */
    public char whatsAtPos(BoardPosition pos);

    /**
     * A method that returns true or false, if a player has won the game or not. This is determined by whether all
     * the remaining pieces on the board belong to the player.
     * @param player A player in checkers, an object of class Character
     * @return true if there is only pieces of one player on the board, false otherwise
     * @pre [player needs to be one of the current player pieces]
     * @post checkPlayerWin = true IFF [there is only pieces of one player on the board]
     * ELSE [there are pieces of 2 players on the board] AND viableDirections = #viableDirections
     * AND board = #board AND pieceCount = #pieceCount
     */
    default public boolean checkPlayerWin(Character player)
    {
        int pieces = 0;

        if(player == 'x')
        {
            pieces = getPieceCounts().get('o');
        }

        if(player == 'o')
        {
            pieces = getPieceCounts().get('x');
        }

        return (pieces < 1);
    }

    /**
     * A method that "crowns" a piece by converting the char at the position on the board, given by the posOfPlayer
     * parameter, to an uppercase equivalent of the char
     * @param posOfPlayer The position of a piece of a player, an object of class BoardPosition
     * @pre [posOfPlayer needs to be at the opposite end of the board from where the piece started] AND [pos is within the boundaries of the board]
     * @post board = [the piece at the position designated by posOfPlayer is replaced by its uppercase equivalent]
     * AND pieceCount = #pieceCount AND viableDirections = #viableDirections
     */
    default public void crownPiece(BoardPosition posOfPlayer)
    {
        char crown;
        crown = Character.toUpperCase(whatsAtPos(posOfPlayer));
        placePiece(posOfPlayer, crown);
    }

    /**
     * A method which moves a piece on the board, located at the startingPos index, in the direction
     * designated by the DirectionEnum dir
     * @param startingPos The starting position of a piece, an object of class BoardPosition
     * @param dir The direction a piece should be moved, an object of class DirectionEnum
     * @return piecePos, an object of class BoardPosition
     * @pre [pos is within the valid bounds of the board] AND [board is not null] AND [dir is not null]
     * @post movePiece = [the new position of the piece on the board] AND viableDirections = #viableDirections
     * board = [at the original starting position, the board should mark the position as empty]
     * AND pieceCount = #pieceCount
     */
    default public BoardPosition movePiece(BoardPosition startingPos, DirectionEnum dir)
    {
        char piece;
        int row;
        int column;
        BoardPosition dirPos;

        piece = whatsAtPos(startingPos);
        placePiece(startingPos, ' ');

        dirPos = getDirection(dir);
        row = startingPos.getRow() + dirPos.getRow();
        column = startingPos.getColumn() + dirPos.getColumn();
        BoardPosition newPos = new BoardPosition(row, column);

        placePiece(newPos, piece);

        return newPos;
    }

    /**
     * A method that moves a piece by "jumping" the player's opponent's piece. When a player "jumps"
     * an opponent, that player should move two positions in the direction passed in by dir parameter.
     * The opponent's jumped piece will be removed from play.
     * @param startingPos The starting position of a piece, an object of class BoardPosition
     * @param dir The direction a piece should be moved, an object of class DirectionEnum
     * @return piecePos, an object of class BoardPosition
     * @pre [there must not be a piece behind the diagonal position from the piece the player wishes to jump]
     * AND [pos is within the valid bounds of the board] AND[board is not null] AND [dir is not null]
     * @post jumpPiece = [the new position of the piece on the board] AND viableDirections = #viableDirections
     * board = [at the original starting position, the board should mark the position as empty and, the position
     * where the piece was jumped should be marked as empty] AND pieceCount = [1 is subtracted from the opponent's
     * pieceCount value]
     */
    default public BoardPosition jumpPiece(BoardPosition startingPos, DirectionEnum dir)
    {
        //gets piece to jump from
        char piece = whatsAtPos(startingPos);

        //get position of jumped piece
        BoardPosition dirPos = getDirection(dir);
        int jumpedRow = startingPos.getRow() + getDirection(dir).getRow();
        int jumpedCol = startingPos.getColumn() + getDirection(dir).getColumn();
        BoardPosition jumpedPos = new BoardPosition(jumpedRow, jumpedCol);

        //get position after jumping
        int newRow = jumpedRow + dirPos.getRow();
        int newCol = jumpedCol + dirPos.getColumn();
        BoardPosition newPos = new BoardPosition(newRow, newCol);

        //checks if there is a piece at the jumped position
        char jumpedPiece = whatsAtPos(jumpedPos);
        if (jumpedPiece != ' ' && jumpedPiece != piece) {
            placePiece(newPos, piece);
            placePiece(startingPos, ' ');
            placePiece(jumpedPos, ' ');

        }
        return newPos;

    }

    /**
     * A method which "scans" the indices surrounding the index given by the startingPos parameter
     * @param startingPos The position from which the function scans, an object of class BoardPosition
     * @return surroundingPos, maps the four DirectionEnums to the char located at that position in the respective
     *         direction, a HashMap
     * @pre [pos is within the valid bounds of the board] AND [board is not null]
     * @post scanSurroundingPositions = [the status of the DirectionEnum positions NE, SE, NW, and SW of the starting
     * point represented as characters] AND piecePos = #piecePos AND viableDirections = #viableDirections AND
     * board = #board AND pieceCount = #pieceCount
     */
    default public HashMap<DirectionEnum, Character> scanSurroundingPositions(BoardPosition startingPos)
    {

        HashMap<DirectionEnum, Character> viable_dir = new HashMap<>();
        int row = startingPos.getRow();
        int col = startingPos.getColumn();

        if(row < 7 && col < 7)
            viable_dir.put(DirectionEnum.SE,whatsAtPos(new BoardPosition(row + 1, col + 1)));
        else
            viable_dir.put(DirectionEnum.SE,' ');

        if(row > 0 && col < 7)
            viable_dir.put(DirectionEnum.NE, whatsAtPos(new BoardPosition(row - 1, col +1)));
        else
            viable_dir.put(DirectionEnum.NE,' ');

        if(row < 7 && col > 0)
            viable_dir.put(DirectionEnum.SW, whatsAtPos(new BoardPosition(row + 1, col - 1)));
        else
            viable_dir.put(DirectionEnum.SW,' ');

        if(row > 0 && col > 0)
            viable_dir.put(DirectionEnum.NW, whatsAtPos(new BoardPosition(row - 1, col -1)));
        else
            viable_dir.put(DirectionEnum.NW,' ');


        return viable_dir;
        
    }

    /**
     * A method that takes the given direction and returns a new position
     * @param dir The direction of where the BoardPosition should be
     * @return The new BoardPosition that represents movement in the given direction
     * @pre [dir is within the boundaries of the board]
     * @post getDirection = [moved position on the board using dir parameter]
     */
    public static BoardPosition getDirection(DirectionEnum dir)
    {
        int row = 0;
        int column = 0;

        if(dir == DirectionEnum.NE)
        {
            row = row - 1;
            column = column + 1;
        }

        if(dir == DirectionEnum.SE)
        {
            row = row + 1;
            column = column + 1;
        }

        if(dir == DirectionEnum.SW)
        {
            row = row + 1;
            column = column - 1;
        }

        if(dir == DirectionEnum.NW)
        {
            row = row - 1;
            column = column - 1;
        }

        BoardPosition dirPos = new BoardPosition(row, column);
        return dirPos;
    }

    /**
     *  A mutator for the player's piece counts. Updates the piece counts of a player
     *  according to the number of pieces they have on a board
     * @param player, the character representing the player's pieces, a char
     * @param numPieces, the number of pieces the player's piece count changes by, an int
     * @pre [player is an "x" or "o"] AND [numPieces is a whole number]
     * @post pieceCount = [updated piece count] AND viableDirections = #viableDirections
     * board = #board
     */
    public void setPieceCount(char player, int numPieces);

    public int getRowNum();

    public int getColNum();

}
