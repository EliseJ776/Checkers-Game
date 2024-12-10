package cpsc2150.extendedCheckers.views;

import cpsc2150.extendedCheckers.models.*;
import cpsc2150.extendedCheckers.util.DirectionEnum;

import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class CheckersFE
{
    private static int BOARD_DIMENSIONS = 8;
    public static int MAX_POS = 7;
    public static int MIN_POS = 0;
    private static char playerOne = 'x';
    private static char playerTwo = 'o';

    public static void setPlayerOne(char player) {
        playerOne = player;
    }

    public static void setPlayerTwo(char player) {
        playerTwo = player;
    }

    public static void setMaxPos(int position)
    {
        MAX_POS = position;
    }

    public static char getPlayerOne()
    {
        return playerOne;
    }

    public static char getPlayerTwo()
    {
        return playerTwo;
    }

    public static int getBoardDimensions()
    {
        return BOARD_DIMENSIONS;
    }

    public static int getMaxPos()
    {
        return MAX_POS;
    }

    private static void setPlayerPieces()
    {
        String userIn;
        Scanner userRead = new Scanner(System.in);

        System.out.println("Player 1, enter your piece: ");
        userIn = userRead.nextLine();

        while(!validatePieces(userIn))
        {
            System.out.println("Please enter only a single lowercase letter character.");
            userIn = userRead.nextLine();
        }

        playerOne = userIn.charAt(0);

        System.out.println("Player 2, enter your piece: ");
        userIn = userRead.nextLine();

        while(!validatePieces(userIn))
        {
            System.out.println("Please enter only a single lowercase letter character.");
            userIn = userRead.nextLine();
        }

        playerTwo = userIn.charAt(0);

    }

    private static boolean validatePieces(String piece)
    {
        return((piece.length() == 1) && (Character.isLetter(piece.charAt(0))) && (piece.charAt(0) == Character.toLowerCase(piece.charAt(0))));
    }

    private static void setBoardDimensions()
    {
        String userIn;
        Scanner userRead = new Scanner(System.in);

        System.out.println("How big should the board be? It can be 8x8, 10x10, 12x12, 14x14, or 16x16. Enter one number: ");
        userIn = userRead.nextLine();
        while(!validateDimensions(userIn))
        {
            System.out.println("Please enter an even value between 8 and 16.");
            userIn = userRead.nextLine();
        }

        BOARD_DIMENSIONS = Integer.parseInt(userIn);
        setMaxPos(BOARD_DIMENSIONS - 1);
    }

    private static boolean setFastGameType()
    {
        String userIn;
        Scanner userRead = new Scanner(System.in);

        System.out.println("Do you want a fast game (F/f) or a memory efficient game (M/m)?");
        userIn = userRead.nextLine();

        return(Objects.equals(userIn, "f") || Objects.equals(userIn, "F"));
    }

    private static boolean validateDimensions(String size)
    {
        int dimensions = Integer.valueOf(size);
        return((dimensions >= 8) && (dimensions <= 16) && ((dimensions % 2) == 0));
    }

    /**
     *
     * @param player The player the function will act on, a char
     * @return new BoardPosition, a new board position created based on the user's requested position
     * @pre [player is = x or o]
     * @post askMovement = [a new Board]
     */
    private static BoardPosition askMovement(char player)
    {
        String userIn;
        Scanner userRead = new Scanner(System.in);
        int row;
        int col;
        int firstIn = 0;
        int secondIn = 2;

        System.out.println("player " + player + ", which piece do you wish to move? Enter" +
                " the row followed by a space followed by the column.");
        userIn = userRead.nextLine();
        char[] userMove = userIn.toCharArray();
        row = Character.getNumericValue(userMove[firstIn]);
        col = Character.getNumericValue(userMove[secondIn]);

        return new BoardPosition(row, col);
    }

    /**
     *
     * @param playerPos the position the player wants to move in, an object of BoardPosition
     * @param player the player who the function is to act on, a char
     * @param gameBoard the game board which is being played on, an objet of CheckerBoard
     * @return [a boolean], returns true if the position the player wants to move to is a possible move and false if it is not
     * @pre [an object of BoardPosition has been created] AND [player = x or o] AND [an object of CheckerBoard has been created]
     * @post isValid = [true or false]
     */
    private static Boolean isValid(BoardPosition playerPos, char player, AbsCheckerBoard gameBoard)
    {
        if(playerPos.getRow() < MIN_POS || playerPos.getRow() > MAX_POS || playerPos.getColumn() < MIN_POS || playerPos.getColumn() > MAX_POS)
        {
            System.out.println("player " + player + ", that isn't within the bounds of the board. Please choose a valid location");
            return false;
        }

        if(Character.toUpperCase(gameBoard.whatsAtPos(playerPos)) != Character.toUpperCase(player))
        {
            System.out.println("Player " + player + ", that isn't your piece. Pick one of your pieces");
            return false;
        }

        else
        {
            return true;
        }
    }

    /**
     *
     * @param player the player whose turn it is, a char
     * @param otherPlayer the player whose turn it is not, a char
     * @param playerPos the position the player wants to move to, an object of BoardPosition
     * @param gameBoard the game board that is being played on
     * @pre [player = x or o] AND [otherPlayer = o if player = x OR other player = x if player = o] AND [playerPos is a valid position]
     * AND [gameBoard has been created]
     * @post [the player's piece is moved to the position they chose and the opponent's piece is jumped and removed if applicable]
     */
    private static BoardPosition playerMove(char player, char otherPlayer, BoardPosition playerPos, AbsCheckerBoard gameBoard)
    {
        Scanner userRead = new Scanner(System.in);
        HashMap<DirectionEnum, Character> vDirections;
        HashMap<DirectionEnum, Character> blockedDirections;
        String userIn;

        System.out.println("In which direction do you wish to move the piece?" +
                " Enter one of these options:");
        vDirections = gameBoard.scanSurroundingPositions(playerPos);

        if(gameBoard.whatsAtPos(playerPos) == playerOne)
        {
            if ((vDirections.get(DirectionEnum.SE) == ' ' || vDirections.get(DirectionEnum.SE) == playerTwo || vDirections.get(DirectionEnum.SE) == Character.toUpperCase(playerTwo))
                    && playerPos.getRow() != MAX_POS && playerPos.getColumn() != MAX_POS)
            {
                blockedDirections = gameBoard.scanSurroundingPositions(new BoardPosition(playerPos.getRow() + 1, playerPos.getColumn() +1 ));
                if((blockedDirections.get(DirectionEnum.SE) == ' ' && (vDirections.get(DirectionEnum.SE) == otherPlayer ||
                        vDirections.get(DirectionEnum.SE) == Character.toUpperCase(otherPlayer))) || vDirections.get(DirectionEnum.SE) == ' ')
                    System.out.println("SE");
            }
            if ((vDirections.get(DirectionEnum.SW) == ' ' || vDirections.get(DirectionEnum.SW) == playerTwo || vDirections.get(DirectionEnum.SW) == Character.toUpperCase(playerTwo))
                    && playerPos.getRow() != MAX_POS && playerPos.getColumn() != MIN_POS)
            {
                blockedDirections = gameBoard.scanSurroundingPositions(new BoardPosition(playerPos.getRow() + 1, playerPos.getColumn() -1 ));
                if((blockedDirections.get(DirectionEnum.SW) == ' ' && (vDirections.get(DirectionEnum.SW) == otherPlayer ||
                        vDirections.get(DirectionEnum.NE) == Character.toUpperCase(otherPlayer))) || vDirections.get(DirectionEnum.SW) == ' ')
                    System.out.println("SW");
            }
        }

        if(gameBoard.whatsAtPos(playerPos) == playerTwo)
        {
            if ((vDirections.get(DirectionEnum.NE) == ' ' || vDirections.get(DirectionEnum.NE) == playerOne || vDirections.get(DirectionEnum.NE) == Character.toUpperCase(playerOne))
                    && playerPos.getRow() != MIN_POS && playerPos.getColumn() != MAX_POS)
            {
                blockedDirections = gameBoard.scanSurroundingPositions(new BoardPosition(playerPos.getRow() - 1, playerPos.getColumn() +1 ));
                if((blockedDirections.get(DirectionEnum.NE) == ' ' && (vDirections.get(DirectionEnum.NE) == otherPlayer ||
                        vDirections.get(DirectionEnum.NE) == Character.toUpperCase(otherPlayer))) || vDirections.get(DirectionEnum.NE) == ' ')
                    System.out.println("NE");
            }
            if ((vDirections.get(DirectionEnum.NW) == ' ' || vDirections.get(DirectionEnum.NW) == playerOne || vDirections.get(DirectionEnum.NW) == Character.toUpperCase(playerOne))
                    && playerPos.getRow() != MIN_POS && playerPos.getColumn() != MIN_POS)
            {
                blockedDirections = gameBoard.scanSurroundingPositions(new BoardPosition(playerPos.getRow() - 1, playerPos.getColumn() -1 ));
                if((blockedDirections.get(DirectionEnum.NW) == ' ' && (vDirections.get(DirectionEnum.NW) == otherPlayer ||
                        vDirections.get(DirectionEnum.NW) == Character.toUpperCase(otherPlayer)))  || vDirections.get(DirectionEnum.NW) == ' ')
                    System.out.println("NW");
            }
        }

        if(gameBoard.whatsAtPos(playerPos) == Character.toUpperCase(player))
        {
            if ((vDirections.get(DirectionEnum.SE) == ' ' || vDirections.get(DirectionEnum.SE) == otherPlayer || vDirections.get(DirectionEnum.SE) == Character.toUpperCase(otherPlayer))
                    && playerPos.getRow() != MAX_POS &&  playerPos.getColumn() != MAX_POS)
            {
                blockedDirections = gameBoard.scanSurroundingPositions(new BoardPosition(playerPos.getRow() + 1, playerPos.getColumn() + 1 ));
                if((blockedDirections.get(DirectionEnum.SE) == ' ' && ((vDirections.get(DirectionEnum.SE) == otherPlayer) ||
                        vDirections.get(DirectionEnum.SE) == Character.toUpperCase(otherPlayer))) || vDirections.get(DirectionEnum.SE) == ' ')
                    System.out.println("SE");
            }
            if ((vDirections.get(DirectionEnum.SW) == ' ' || vDirections.get(DirectionEnum.SW) == otherPlayer || vDirections.get(DirectionEnum.SW) == Character.toUpperCase(otherPlayer))
                    && playerPos.getRow() != MAX_POS && playerPos.getColumn() != MIN_POS)
            {
                blockedDirections = gameBoard.scanSurroundingPositions(new BoardPosition(playerPos.getRow() + 1, playerPos.getColumn() -1 ));
                if((blockedDirections.get(DirectionEnum.SW) == ' ' && ((vDirections.get(DirectionEnum.SW) == otherPlayer) ||
                        vDirections.get(DirectionEnum.SW) == Character.toUpperCase(otherPlayer))) || vDirections.get(DirectionEnum.SW) == ' ')
                    System.out.println("SW");
            }
            if ((vDirections.get(DirectionEnum.NW) == ' ' || vDirections.get(DirectionEnum.NW) == otherPlayer || vDirections.get(DirectionEnum.NW) == Character.toUpperCase(otherPlayer))
                    && playerPos.getRow() != MIN_POS && playerPos.getColumn() != MIN_POS)
            {
                blockedDirections = gameBoard.scanSurroundingPositions(new BoardPosition(playerPos.getRow() - 1, playerPos.getColumn() +1 ));
                if((blockedDirections.get(DirectionEnum.NE) == ' ' && ((vDirections.get(DirectionEnum.NE) == otherPlayer) ||
                        vDirections.get(DirectionEnum.NE) == Character.toUpperCase(otherPlayer))) || vDirections.get(DirectionEnum.NE) == ' ')
                    System.out.println("NE");
            }
            if ((vDirections.get(DirectionEnum.NE) == ' ' || vDirections.get(DirectionEnum.NE) == otherPlayer || vDirections.get(DirectionEnum.NE) == Character.toUpperCase(otherPlayer))
                    && playerPos.getRow() != MIN_POS && playerPos.getColumn() != MAX_POS)
            {
                blockedDirections = gameBoard.scanSurroundingPositions(new BoardPosition(playerPos.getRow() - 1, playerPos.getColumn() -1 ));
                if((blockedDirections.get(DirectionEnum.NW) == ' ' && ((vDirections.get(DirectionEnum.NW) == otherPlayer) ||
                        vDirections.get(DirectionEnum.NW) == Character.toUpperCase(otherPlayer))) || vDirections.get(DirectionEnum.NW) == ' ')
                    System.out.println("NW");
            }
        }
        userIn = userRead.nextLine();

        userIn = userIn.toUpperCase();

        DirectionEnum moveDir = DirectionEnum.valueOf(userIn);

        if(vDirections.get(moveDir) == otherPlayer || vDirections.get(moveDir) == Character.toUpperCase(otherPlayer))
        {
            playerPos = gameBoard.jumpPiece(playerPos, moveDir);
            gameBoard.setPieceCount(otherPlayer, -1);
            return playerPos;
        }

        else
        {
            gameBoard.movePiece(playerPos, moveDir);
            return playerPos;
        }

    }

    public static void main(String[] args)
    {
     String userIn = "Y";
     String userChar = " ";
     int turn = 0;
     boolean boardType = true;
     AbsCheckerBoard gameBoard;

     System.out.println("Welcome to Checkers!");

     while(userIn.equals("Y"))
     {
         setPlayerPieces();
         setBoardDimensions();
         boardType = setFastGameType();

         if(boardType)
         {
             gameBoard = new CheckerBoard(BOARD_DIMENSIONS);
         }
         else
         {
             gameBoard = new CheckerBoardMem(BOARD_DIMENSIONS);
         }

         Scanner userRead = new Scanner(System.in);
         turn = 0;

         while (!gameBoard.checkPlayerWin(playerOne) && !gameBoard.checkPlayerWin(playerTwo))
         {
             System.out.println(gameBoard.toString() + "\n");

             if (turn % 2 == 0) {
                 BoardPosition playerPos = askMovement(playerOne);
                 while (!isValid(playerPos, playerOne, gameBoard)) {
                     playerPos = askMovement(playerOne);
                 }

                 playerPos = playerMove(playerOne, playerTwo, playerPos, gameBoard);

                 if (playerPos.getRow() == MAX_POS) {
                     gameBoard.crownPiece(playerPos);
                 }
             }

             if (turn % 2 != 0) {
                 BoardPosition playerPos = askMovement(playerTwo);
                 while (!isValid(playerPos, playerTwo, gameBoard)) {
                     playerPos = askMovement(playerTwo);
                 }

                 playerMove(playerTwo, playerOne, playerPos, gameBoard);

                 if (playerPos.getRow() == MIN_POS) {
                     gameBoard.crownPiece(playerPos);
                 }
             }
             turn++;
         }

         if (gameBoard.checkPlayerWin(playerOne)) {
             System.out.println("Player " + playerOne + " has won!");
         }

         if (gameBoard.checkPlayerWin(playerTwo)) {
             System.out.println("Player " + playerTwo + " has won!");
         }

         System.out.println("Would you like to play again? Enter 'Y' or 'N' ");
         userIn = userRead.nextLine();
     }
    }
}
