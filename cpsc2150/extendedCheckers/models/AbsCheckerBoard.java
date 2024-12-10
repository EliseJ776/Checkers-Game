package cpsc2150.extendedCheckers.models;
import static cpsc2150.extendedCheckers.views.CheckersFE.getBoardDimensions;

public abstract class AbsCheckerBoard implements ICheckerBoard
{
    private int dimensionCheck;

    /**
     * A method that returns a string that represents the checkerboard and players current position
     * @return String that represents the current state of the board
     * @pre none
     * @post toString = [string representing current state of the board] AND board = #board AND pieceCount = #pieceCount
     */
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("|  |");
        for (int col = 0; col < getColNum(); col++) {
            if(col < 10)
                sb.append(" ").append(col).append("|");
            else if (col == 10)
                sb.append(col).append("|");
            else
                sb.append(col).append("|");
        }
        sb.append("\n");

        for (int row = 0; row < getRowNum(); row++) {
            if (row < 10)
                sb.append("|").append(row).append(" ");
            else
                sb.append("|").append(row);
            for (int col = 0; col < getColNum(); col++) {
                BoardPosition sPos = new BoardPosition(row, col);
                sb.append("|");
                sb.append(whatsAtPos(sPos) == '*' ? "*" : whatsAtPos(sPos)).append(" ");
            }
            sb.append("|\n");
        }
        return sb.toString();
    }
}
