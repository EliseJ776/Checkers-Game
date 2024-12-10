package cpsc2150.extendedCheckers.models;

/**
 * Entity object which keeps track of the individual position of the checkerboard.
 * @invariant (0 < row < 8) and (0 < column < 8)
 */

public class BoardPosition
{
    /**
     * Row component of the BoardPosition
     */
    private int row;

    /**
     * Column component of the BoardPosition
     */
    private int column;

    /**
     * Constructor for the BoardPosition object.
     * @param aRow, the value to be set for row, an int
     * @param aCol, the value to be set for column, an int
     * @pre [0 <= aRow <= CheckerBoard.ROW_NUM] AND [0 <= aCol <= CheckerBoard.COL_NUM]
     * @post row = aRow AND column = aColumn
     */

    public BoardPosition(int aRow, int aCol)
    {
        row = aRow;
        column = aCol;
    }

    /**
     * Standard getter for the row
     * @return An integer representing where on the board the player wants to place their piece, horizontally
     * @pre none
     * @post getRow = row AND row = #row AND column = #column
     */

    public int getRow() {
        return row;
    }

    /**
     * Standard getter for the column
     * @return An integer representing where on the board the player wants to place their piece, vertically
     * @pre none
     * @post getColumn = column AND column = #column AND row = #row
     */

    public int getColumn() {
        return column;
    }

    /**
     * Compares BoardPosition to obj
     * @param obj is the object to be compared
     * @return equals = [true if obj equals BoardPosition, false otherwise]
     * @pre obj should not be null
     * @post equals = true IF [obj and BoardPosition object are the same and/or have the same instance variable values], False OW AND row = #row AND column = #column
     */

    public boolean equals(Object obj) {
        /*
        returns true if this BoardPosition is equal to the parameter object. Two BoardPositions are equal if their row
        and column values are the same.*/

        if (!((obj) instanceof BoardPosition)) {
            return false;
        }

        BoardPosition parameter = (BoardPosition) obj;
        return this.row == parameter.row && this.column == parameter.column;

        /* hint: it is intentional that this accepts a parameter of type Object. There is a way to check if that parameter
        Object just happens to be an instance of a BoardPosition.
        */
    }

    /**
     * A method that returns a String of the row and column of the BoardPosition
     * @return String that represents the current row and column
     * @post toString = [String of current row and column in format "row, column"] AND row = #row AND column =#column
     */
    @Override
    public String toString() {
        /*
        returns a String representation of the BoardPosition in the format of "row,column"
         */
        return row + "," + column;
    }

}
