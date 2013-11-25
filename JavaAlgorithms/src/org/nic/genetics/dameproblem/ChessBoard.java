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
}
