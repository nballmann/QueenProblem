package org.nic.genetics.dameproblem;

import java.util.ArrayList;
import java.util.Random;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.nic.genetics.dameproblem.controller.ChessController;

public class ChessBoard
{
    private int fieldCount;

    private ObservableList<ObservableList<ChessField>> chessFields = FXCollections.observableArrayList();

    private Random random = new Random();

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

    public void setFieldCount(final int newFieldCount)
    {
	this.fieldCount = newFieldCount;
    }

    public ObservableList<ObservableList<ChessField>> getChessFields()
    {
	return chessFields;
    }

    public void setQueen(final int row, final int column, final boolean queenStatus)
    {
	chessFields.get(row).get(column).setQueen(queenStatus);
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
		    value -= (1-field.getQueenValue());
		}
	    }
	}
	return value;
    }

    /**
     * Changes the status of fields in a horizontal/vertical cross shape and a diagonal x-shape to false<br>
     * @param column the center row position
     * @param row the center column position
     */
    public void changeFieldStatus(final int row, final int column)
    {
	for (ObservableList<ChessField> list : chessFields)
	{
	    list.get(column).changeFreeStatus(false); // all vertical fields
	    if(!(list.get(column) == chessFields.get(row).get(column)))
	    {
		changeQueenValue(list.get(column));
	    }
	}

	int rowPlus = column, rowMinus = column;

	for(int i = row + 1; i < 6; i++)
	{
	    if(i < 6)
	    {
		if(++rowPlus < 6)
		{
		    chessFields.get(i).get(rowPlus).changeFreeStatus(false);
		    changeQueenValue(chessFields.get(i).get(rowPlus));
		}

		if(--rowMinus >= 0)
		{
		    chessFields.get(i).get(rowMinus).changeFreeStatus(false);
		    changeQueenValue(chessFields.get(i).get(rowMinus));
		}
	    }
	    else
	    {
		break;
	    }
	}

	rowPlus = column;
	rowMinus = column;

	for(int j = row - 1; j >= 0;j--)
	{
	    if(j >= 0)
	    {
		if(++rowPlus < 6)
		{
		    chessFields.get(j).get(rowPlus).changeFreeStatus(false);
		    changeQueenValue(chessFields.get(j).get(rowPlus));
		}

		if(--rowMinus >= 0)
		{
		    chessFields.get(j).get(rowMinus).changeFreeStatus(false);
		    changeQueenValue(chessFields.get(j).get(rowMinus));
		}
	    }
	    else
	    {
		break;
	    }

	    //		System.out.println("column: " + j + "rows: " + rowPlus + ":" + rowMinus);
	}


	for (ChessField f : chessFields.get(row))
	{
	    f.changeFreeStatus(false); // all horizontal fields
	    if(!(f == chessFields.get(row).get(column)))
	    {
		changeQueenValue(f);
	    }
	}
    }

    /**
     * Decrements the value of a queen-ChessField by 0.2 
     * 
     * @param field the ChessField to decrement 
     */
    private void changeQueenValue(final ChessField field)
    {
	field.decrementQueenValue();
    }

    /**
     * Creates a deep copy of this object
     * 
     * @return a deep copy of this ChessBoard instance
     */
    public ChessBoard deepCopy()
    {
	ChessBoard newBoard = new ChessBoard(chessFields.size());

	newBoard.chessFields = FXCollections.observableArrayList();

	for (int i = 0; i < this.chessFields.size(); i++)
	{
	    ObservableList<ChessField> newList = FXCollections.observableArrayList();

	    newBoard.chessFields.add(newList);

	    for (int j = 0; j < this.chessFields.get(0).size(); j++)
	    {
		newBoard.chessFields.get(i).add(this.chessFields.get(i).get(j).deepCopy());
	    }
	}

	return newBoard;
    }

    /**
     * Mutates the whole board by off-setting its queens by a specified amount<br>
     * (specified through a variant factor {@link ChessController.VARIANZ}
     */
    public void mutateBoard()
    {
	for (int i = 0; i < chessFields.size(); i++)
	{
	    for (int j = 0; j < chessFields.get(0).size(); j++)
	    {
		if (chessFields.get(j).get(i).isQueen())
		{
		    int[] mutation = mutateQueen(ChessController.VARIANZ);

		    if (    j+mutation[0] < 0 
			    || i+mutation[1] < 0 
			    || i+mutation[1] >= chessFields.get(0).size() 
			    || j+mutation[0] >= chessFields.size() 
			    || chessFields.get(j+mutation[0]).get(i+mutation[1]).isQueen() )
		    {
			j--;
		    }
		    else
		    {
			chessFields.get(j).get(i).setQueen(false);
			chessFields.get(j+mutation[0]).get(i+mutation[1]).setQueen(true);
		    }
		}
	    }
	}
    }

    /**
     * Randomly mutates a queen field
     * @param varianz the maximum amount by which a queen can be moved randomly
     * @return direction and amount of the queen-offset
     */
    private int[] mutateQueen(int varianz)
    {
	int rndX = random.nextInt(varianz*2)-varianz;
	int rndY = random.nextInt(varianz*2)-varianz;

	return new int[] {rndX, rndY};
    }

    /**
     * Recombines the whole board by mirroring its content on a vertically centered axis 
     */
    public void recombineBoard()
    {
	ArrayList<ChessField> fields = new ArrayList<>();

	for( int i = 0; i < chessFields.size(); i++)
	{
	    for( int j = 0; j < chessFields.get(0).size(); j++)
	    {
		if(chessFields.get(i).get(j).isQueen())
		{
		    fields.add(chessFields.get(i).get(5-j));

		    chessFields.get(i).get(j).setQueen(false);
		}
	    }
	}

	for(int i = 0; i < fields.size(); i++)
	{
	    fields.get(i).setQueen(true);
	}
    }

    /**
     * Generates a recombined child from two ChessBoard results
     * @param otherParent the other parent for recombination
     * @return a deep copy child of this instance and the ChessBoard object parameter
     */
    public ChessBoard generateChild(final ChessBoard otherParent)
    {	
	ChessBoard child = this.deepCopy();
	child.recombineBoard();

	int counter = 0;

	for(ObservableList<ChessField> row : child.getChessFields())
	{
	    for(ChessField field : row)
	    {
		if(field.isQueen())
		{
		    for(int i = 0; i < otherParent.getFieldCount(); i++)
		    {
			for(int j = 0; j < otherParent.getFieldCount(); j++)
			{
			    if(otherParent.getChessFields().get(i).get(j).isQueen())
			    {

				if(!child.getChessFields().get(i).get(5-j).isQueen())
				{
				    child.getChessFields().get(i).get(5-j).setQueen(true);
				    field.setQueen(false);

				    counter++;
				}

				if(counter == 3)
				{
				    return child;
				}
			    }

			}
		    }
		}
	    }
	}

	return child;
    }


}
