package cpsc2150.extendedCheckers.tests;

import static cpsc2150.extendedCheckers.util.DirectionEnum.*;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

import cpsc2150.extendedCheckers.models.CheckerBoard;
import cpsc2150.extendedCheckers.util.DirectionEnum;

import org.junit.Test;
import cpsc2150.extendedCheckers.models.*;

import java.util.ArrayList;
import java.util.HashMap;

public class TestCheckerBoard {
    private String arrToString(char[][] board){
        StringBuilder s = new StringBuilder("|  |");
        int numRows = board.length;
        int numCols = board[0].length;

        for(int i = 0; i < numCols; i++){
            if(i < 10){
                s.append(" ").append(i).append("|");
            }
            else {
                s.append(i).append("|");
            }
        }
        s.append("\n");

        for(int i = 0; i < numRows; i++) {
            if (i < 10) {
                s.append("|").append(i).append(" ");
            } else {
                s.append("|").append(i);
            }


            for (int j = 0; j < numCols; j++) {
                s.append("|");
                char pos = board[i][j];
                if (pos != '*') {
                    s.append(pos).append(" ");
                } else {
                    s.append("* ");
                }
            }
            s.append("|\n");
        }
        return s.toString();
    }

    /**
     * A method that calls the constructor and returns a CheckerBoard
     * @return A new checkers board to start a game
     * @pre none
     * @post  new CheckerBoard object
     */
    public static ICheckerBoard makeBoard(int aDimension) {
        return new CheckerBoard(aDimension);
    }

    @Test
    public void test_placePiece_pos07_x() {
        BoardPosition inputPos = new BoardPosition(0, 7);
        ICheckerBoard testBoard = makeBoard(8);
        char inputPlayer = 'x';

        testBoard.placePiece(inputPos, inputPlayer);
        String observed = testBoard.toString();

        char[][] expectedBoard = {
            {'x', '*', 'x', '*', 'x', '*', 'x', 'x'},
            {'*', 'x', '*', 'x', '*', 'x', '*', 'x'},
            {'x', '*', 'x', '*', 'x', '*', 'x', '*'},
            {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
            {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
            {'*', 'o', '*', 'o', '*', 'o', '*', 'o'},
            {'o', '*', 'o', '*', 'o', '*', 'o', '*'},
            {'*', 'o', '*', 'o', '*', 'o', '*', 'o'},

        };

        String expected = arrToString(expectedBoard);
        assertEquals(expected, observed);
    }

    @Test
    public void test_placePiece_pos00_o() {
        BoardPosition inputPos = new BoardPosition(0,0);
        ICheckerBoard testBoard = makeBoard(8);
        char inputPlayer = 'o';

        testBoard.placePiece(inputPos, inputPlayer);
        String observed = testBoard.toString();

        char[][] expectedBoard = {
                {'o', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', 'x', '*', 'x', '*', 'x', '*', 'x'},
                {'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o'},
                {'o', '*', 'o', '*', 'o', '*', 'o', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o'},

        };

        String expected = arrToString(expectedBoard);
        assertEquals(expected, observed);
    }

    @Test
    public void test_placePiece_pos150_x() {
        BoardPosition inputPos = new BoardPosition(15,0);
        ICheckerBoard testBoard = makeBoard(16);
        char inputPlayer = 'x';

        testBoard.placePiece(inputPos, inputPlayer);
        String observed = testBoard.toString();

        char[][] expectedBoard = {
                {'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x'},
                {'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x'},
                {'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x'},
                {'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o'},
                {'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o'},
                {'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o'},
                {'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*'},
                {'x', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o'},

        };

        String expected = arrToString(expectedBoard);
        assertEquals(expected, observed);
    }

    @Test
    public void test_placePiece_pos11_11_x() {
        BoardPosition inputPos = new BoardPosition(11,11);
        ICheckerBoard testBoard = makeBoard(12);
        char inputPlayer = 'x';

        testBoard.placePiece(inputPos, inputPlayer);
        String observed = testBoard.toString();

        char[][] expectedBoard = {
                {'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x'},
                {'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x'},
                {'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o'},
                {'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o'},
                {'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'x'},

        };

        String expected = arrToString(expectedBoard);
        assertEquals(expected, observed);
    }

    @Test
    public void test_placePiece_pos44_o() {
        BoardPosition inputPos = new BoardPosition(4,4);
        ICheckerBoard testBoard = makeBoard(8);
        char inputPlayer = 'o';

        testBoard.placePiece(inputPos, inputPlayer);
        String observed = testBoard.toString();

        char[][] expectedBoard = {
                {'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', 'x', '*', 'x', '*', 'x', '*', 'x'},
                {'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', 'o', '*', ' ', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o'},
                {'o', '*', 'o', '*', 'o', '*', 'o', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o'},

        };

        String expected = arrToString(expectedBoard);
        assertEquals(expected, observed);
    }

    @Test
    public void testWhatsAtPos_MaxRow_MinCol_BlackTile() {
        BoardPosition inputPos = new BoardPosition(7,0);
        ICheckerBoard testBoard = makeBoard(8);
        char expected = '*';

        char actualOutput = testBoard.whatsAtPos(inputPos);

        assertEquals(expected, actualOutput);

    }

    @Test
    public void testWhatsAtPos_MinRow_MinCol_WhiteTile() {
        BoardPosition inputPos = new BoardPosition(0,0);
        ICheckerBoard testBoard = makeBoard(8);
        char expected = 'x';

        char actualOutput = testBoard.whatsAtPos(inputPos);

        assertEquals(expected, actualOutput);
    }

    @Test
    public void testWhatsAtPos_MaxRow_MaxCol_BlackTile() {
        BoardPosition inputPos = new BoardPosition(7,7);
        ICheckerBoard testBoard = makeBoard(8);
        char expected = 'o';

        char actualOutput = testBoard.whatsAtPos(inputPos);

        assertEquals(expected, actualOutput);
    }

    @Test
    public void testWhatsAtPos_MinRow_MaxCol_WhiteTile() {
        BoardPosition inputPos = new BoardPosition(0,7);
        ICheckerBoard testBoard = makeBoard(8);
        char expected = '*';

        char actualOutput = testBoard.whatsAtPos(inputPos);

        assertEquals(expected, actualOutput);
    }

    @Test
    public void testWhatsAtPos_MidRow_MidCol_WhiteTile() {
        BoardPosition inputPos = new BoardPosition(3,3);
        ICheckerBoard testBoard = makeBoard(8);
        char expected = ' ';

        char actualOutput = testBoard.whatsAtPos(inputPos);

        assertEquals(expected, actualOutput);
    }

    @Test
    public void test_movePiece_pos00() {
        BoardPosition startPos = new BoardPosition(0,0);
        ICheckerBoard testBoard = makeBoard(8);
        testBoard.placePiece(new BoardPosition(1,1), ' ');

        BoardPosition newPos = testBoard.movePiece(startPos, SE);

        assertEquals(new BoardPosition(1, 1), newPos);
    }

    @Test
    public void test_movePiece_pos77() {
        BoardPosition startPos = new BoardPosition(7,7);
        ICheckerBoard testBoard = makeBoard(8);
        testBoard.placePiece(new BoardPosition(6,6), ' ');

        BoardPosition newPos = testBoard.movePiece(startPos, NW);

        assertEquals(new BoardPosition(6, 6), newPos);
    }

    @Test
    public void test_movePiece_pos53() {
        BoardPosition startPos = new BoardPosition(5,3);
        ICheckerBoard testBoard = makeBoard(8);

        BoardPosition newPos = testBoard.movePiece(startPos, NE);

        assertEquals(new BoardPosition(4, 4), newPos);
    }

    @Test
    public void test_scanSurroundingPositions_MidRow_MidCol_WhiteTile() {
        BoardPosition startingPos = new BoardPosition(3,3);
        ICheckerBoard testBoard = makeBoard(8);
        
        HashMap<DirectionEnum, Character> expectedOutput = new HashMap<>();
        expectedOutput.put(SE,' ');
        expectedOutput.put(NE, 'x');
        expectedOutput.put(SW, ' ');
        expectedOutput.put(NW, 'x');

        String expected = expectedOutput.toString();

        HashMap<DirectionEnum, Character> actualOutput = testBoard.scanSurroundingPositions(startingPos);

        String observed = actualOutput.toString();
        
        assertEquals(expected, observed);
    }


    @Test
    public void test_scanSurroundingPositions_minRow_MaxCol_BlackTile() {
        BoardPosition startingPos = new BoardPosition(0,7);
        ICheckerBoard testBoard = makeBoard(8);

        HashMap<DirectionEnum, Character> expectedOutput = new HashMap<>();
        expectedOutput.put(SE,' ');
        expectedOutput.put(NE, ' ');
        expectedOutput.put(SW, '*');
        expectedOutput.put(NW, ' ');

        String expected = expectedOutput.toString();

        HashMap<DirectionEnum, Character> actualOutput = testBoard.scanSurroundingPositions(startingPos);

        String observed = actualOutput.toString();

        assertEquals(expected, observed);
    }

    @Test
    public void test_scanSurroundingPositions_MaxRow_SecondCol_WhiteTile() {
        BoardPosition startingPos = new BoardPosition(7,1);
        ICheckerBoard testBoard = makeBoard(8);

        HashMap<DirectionEnum, Character> expectedOutput = new HashMap<>();
        expectedOutput.put(SE,' ');
        expectedOutput.put(NE, 'o');
        expectedOutput.put(SW, ' ');
        expectedOutput.put(NW, 'o');

        String expected = expectedOutput.toString();

        HashMap<DirectionEnum, Character> actualOutput = testBoard.scanSurroundingPositions(startingPos);

        String observed = actualOutput.toString();

        assertEquals(expected, observed);
    }

    @Test
    public void test_Constructor(){
        ICheckerBoard testBoard = makeBoard(8);
        String observed = testBoard.toString();
        char[][] expectedBoard ={
                {'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', 'x', '*', 'x', '*', 'x', '*', 'x'},
                {'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o'},
                {'o', '*', 'o', '*', 'o', '*', 'o', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o'},
        };

        String expected = arrToString(expectedBoard);
        assertEquals(expected, observed);

    }
    @Test
    public void test_getPieceCounts_56(){
        ICheckerBoard checkerBoard = makeBoard(16);
        HashMap<Character, Integer> actualCount = checkerBoard.getPieceCounts();
        HashMap<Character, Integer> expectedCount = new HashMap<>();

        expectedCount.put('x', 56);
        expectedCount.put('o', 56);

        String expected = expectedCount.toString();
        String observed = actualCount.toString();

        assertEquals(expected, observed);

    }
    @Test
    public void test_getViableDirections_start(){
        ICheckerBoard checkerBoard = makeBoard(8);
        HashMap<Character, ArrayList<DirectionEnum>> actualDirections = checkerBoard.getViableDirections();
        HashMap<Character, ArrayList<DirectionEnum>> expectedDirections = new HashMap<>();
        ArrayList<DirectionEnum> directionx = new ArrayList<>();
        ArrayList<DirectionEnum> directiono = new ArrayList<>();
        ArrayList<DirectionEnum> directionX = new ArrayList<>();
        ArrayList<DirectionEnum> directionO = new ArrayList<>();

        directionx.add(SE);
        directionx.add(SW);
        directiono.add(NE);
        directiono.add(NW);

        directionX.add(SE);
        directionX.add(SW);
        directionX.add(NE);
        directionX.add(NW);

        directionO.add(SE);
        directionO.add(SW);
        directionO.add(NE);
        directionO.add(NW);

        expectedDirections.put('x', directionx);
        expectedDirections.put('o', directiono);
        expectedDirections.put('X', directionX);
        expectedDirections.put('O', directionO);

        String expected = expectedDirections.toString();
        String observed = actualDirections.toString();

        assertEquals(expected, observed);
    }
    @Test
    public void test_jumpPieceFrom_13SW(){
        BoardPosition startPos = new BoardPosition(1,3);
        ICheckerBoard testBoard = makeBoard(8);

        BoardPosition newPos = testBoard.jumpPiece(startPos, SW);
        BoardPosition expectedPos = new BoardPosition(3,1);

        assertEquals(expectedPos, newPos);
    }
    @Test
    public void test_jumpPieceFrom_13SE(){
        BoardPosition startPos = new BoardPosition(1,3);
        ICheckerBoard testBoard = makeBoard(8);

        BoardPosition newPos = testBoard.jumpPiece(startPos, SE);
        BoardPosition expectedPos = new BoardPosition(3,5);

        assertEquals(expectedPos, newPos);
    }
    @Test
    public void test_jumpPieceFrom_60NE(){
        BoardPosition startPos = new BoardPosition(6,0);
        ICheckerBoard testBoard = makeBoard(8);

        BoardPosition newPos = testBoard.jumpPiece(startPos, NE);
        BoardPosition expectedPos = new BoardPosition(4,2);

        assertEquals(expectedPos, newPos);
    }

    @Test
    public void test_checkPlayerWin_playerXWin() {
        ICheckerBoard board = makeBoard(8);
        board.setPieceCount('o', -12);

        String expected = "true";
        String actualOutput = String.valueOf(board.checkPlayerWin('x'));

        assertEquals(expected, actualOutput);
    }

    @Test
    public void test_checkPlayerWin_playerXLosing() {
        ICheckerBoard board = makeBoard(8);

        String expected = "false";
        String actualOutput = String.valueOf(board.checkPlayerWin('x'));

        assertEquals(expected, actualOutput);
    }

    @Test
    public void test_crownPiece_playerCrown() {
        BoardPosition pos = new BoardPosition(7, 1);
        ICheckerBoard board = makeBoard(8);
        board.placePiece(pos, 'x');
        board.crownPiece(pos);

        String expected = "X";
        String actualOutput = String.valueOf(board.whatsAtPos(pos));

        assertEquals(expected, actualOutput);
    }

    @Test
    public void test_crownPiece_noCrown() {
        BoardPosition pos = new BoardPosition(1, 2);
        ICheckerBoard board = makeBoard(8);
        board.placePiece(pos, ' ');
        board.crownPiece(pos);

        String expected = " ";
        String actualOutput = String.valueOf(board.whatsAtPos(pos));

        assertEquals(expected, actualOutput);
    }

    @Test
    public void test_crownPiece_edge() {
        BoardPosition pos = new BoardPosition(0, 0);
        ICheckerBoard board = makeBoard(8);
        board.placePiece(pos, 'o');
        board.crownPiece(pos);

        String expected = "O";
        String actualOutput = String.valueOf(board.whatsAtPos(pos));

        assertEquals(expected, actualOutput);
    }

    @Test
    public void test_crownPiece_crownTwice() {
        BoardPosition pos = new BoardPosition(0, 0);
        ICheckerBoard board = makeBoard(8);
        board.placePiece(pos, 'o');
        board.crownPiece(pos);

        String expected = "O";
        String actualOutput = String.valueOf(board.whatsAtPos(pos));

        assertEquals(expected, actualOutput);
    }

    @Test
    public void test_getDirection_southeastSE() {
        int row;
        int col;

        BoardPosition newPos = ICheckerBoard.getDirection(SE);
        row = newPos.getRow();
        col = newPos.getColumn();
        row = row + 3;
        col = col + 3;

        BoardPosition pos = new BoardPosition(row, col);

        String expected = "4,4";
        String actualOutput = pos.toString();

        assertEquals(expected, actualOutput);
    }

}

