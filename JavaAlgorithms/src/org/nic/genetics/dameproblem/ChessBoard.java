package org.nic.genetics.dameproblem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ChessBoard
{
    private int fieldCount;

    private ObservableList<ObservableList<ChessField>> chessFields = FXCollections.observableArrayList();

    /**
     * 
     * @param fieldCount this value to the power of 2 equals the total field count
     */
    public ChessBoard(final int fieldCount)
    {
	this.fieldCount = (int) Math.pow(fieldCount, 2);

	for(int i = 0; i < fieldCount; i++)
	{
	    ObservableList<ChessField> column = FXCollections.observableArrayList();

	    for(int j = 0; j < fieldCount; j++)
	    {
		column.add(new ChessField());
	    }
	    
	    chessFields.add(column);
	}

	FXCollections.unmodifiableObservableList(chessFields);
    }

    public int getFieldCount()
    {
	return fieldCount;
    }

    public ObservableList<ObservableList<ChessField>> getChessFields()
    {
	return chessFields;
    }

    public void setQueen(final int rowPos, final int columnPos, final boolean queenStatus)
    {
	chessFields.get(rowPos).get(columnPos).setQueen(queenStatus);
    }

    public boolean isQueen(final int rowPos, final int columnPos)
    {
	return chessFields.get(rowPos).get(columnPos).isQueen();
    }
    
    public double getBoardValue()
    {
	double value = 6;
	
	for(ObservableList<ChessField> rows : chessFields)
	{
	    for(ChessField field : rows)
	    {
		if(field.isQueen())
		{
		    value -= field.getQueenValue();
		}
	    }
	}
	return value;
    }
    
    /**
     * Changes the status of fields in a horizontal/vertical cross shape and a diagonal x-shape to false<br>
     * @param rowPos the center row position
     * @param columnPos the center column position
     */
    public void changeFieldStatus(int columnPos, int rowPos)
    {
	for (ObservableList<ChessField> list : chessFields)
	{
	    list.get(rowPos).changeFreeStatus(false); // all vertical fields
	    changeQueenValue(list.get(rowPos));

	    int rowPlus = rowPos, rowMinus = rowPos;

	    for(int i = columnPos + 1; i < 6; i++)
	    {
		if(++rowPlus < 6)
		{
		    chessFields.get(i).get(rowPlus).changeFreeStatus(false);
		    changeQueenValue(chessFields.get(i).get(rowPlus));
		}

		if(--rowMinus >= 0)
		{
		    chessFields.get(i).get(rowMinus).changeFreeStatus(false);
		    changeQueenValue(chessFields.get(i).get(rowPlus));
		}
	    }

	    rowPlus = rowPos;
	    rowMinus = rowPos;

	    for(int j = columnPos - 1; j >= 0;j--)
	    {
		if(++rowPlus < 6)
		{
		    chessFields.get(j).get(rowPlus).changeFreeStatus(false);
		    changeQueenValue(chessFields.get(j).get(rowPlus));
		}

		if(--rowMinus >= 0)
		{
		    chessFields.get(j).get(rowMinus).changeFreeStatus(false);
		    changeQueenValue(chessFields.get(j).get(rowPlus));
		}
		
		System.out.println("column: " + j + "rows: " + rowPlus + ":" + rowMinus);
	    }
	}

	for (ChessField f : chessFields.get(columnPos))
	{
	    f.changeFreeStatus(false); // all horizontal fields
	    changeQueenValue(f);
	}
    }
    
    private void changeQueenValue(ChessField field)
    {
	field.decrementQueenValue();
    }
    
    public ChessBoard deepCopy()
    {
	ChessBoard newBoard = new ChessBoard(fieldCount);
	
	newBoard.chessFields = FXCollections.observableArrayList();
	
	
	
	
	return null;
    }
}
