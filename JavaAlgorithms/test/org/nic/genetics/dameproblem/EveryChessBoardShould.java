package org.nic.genetics.dameproblem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javafx.collections.ObservableList;

import org.junit.Test;

public class EveryChessBoardShould
{
    @Test
    public void HaveAFieldCountEqualToItsConstructorArgumentToThePowerOf2()
    {
	ChessBoard cb = new ChessBoard(6);

	assertEquals(36, cb.getFieldCount());	
    }

    @Test
    public void HaveAListOfAllItsFields()
    {
	ChessBoard cb = new ChessBoard(6);

	int size = 0;
	for(ObservableList<ChessField> l : cb.getChessFields())
	{
	    size += l.size();
	}

	assertEquals(36, size);
    }

    @Test
    public void BeAbleToPutAQueenOnOneOfItsFields()
    {
	ChessBoard cb = new ChessBoard(6);

	cb.setQueen(3, 2, true);

	assertTrue(cb.isQueen(3, 2));
    }

    @Test
    public void makeADeepCopyOfItself()
    {
	ChessBoard original = new ChessBoard(6);

	ChessBoard copy = original.deepCopy();

	assertTrue(original != copy);
    }

    @Test
    public void HaveAHorizontalAndVerticalSideLengthOfItsConstructorParameter()
    {
	ChessBoard cb = new ChessBoard(6);

	int rowSize = cb.getChessFields().get(0).size();
	int columnSize = cb.getChessFields().size();

	assertEquals(6, ((rowSize+columnSize)/2)); 
    }

    @Test
    public void BeAbleToChangeItsFieldStati()
    {
	ChessBoard cb = new ChessBoard(6);

	cb.changeFieldStatus(0, 0);

	int setFields = 0;

	for(ObservableList<ChessField> row : cb.getChessFields())
	{
	    for(ChessField field : row)
	    {
		if(!field.isFree())
		{
		    setFields++;
		}
	    }
	}

	assertEquals(16, setFields);
    }

    @Test
    public void BeAbleToReturnItsBoardValue()
    {
	ChessBoard cb = new ChessBoard(6);

	assertEquals(6, cb.getBoardValue(), 0.01);
    }

    @Test
    public void BeAbleToDecrementItsQueenValues()
    {
	ChessBoard cb = new ChessBoard(6);

	cb.setQueen(0, 0, true);

	try
	{
	    Method m = cb.getClass().getDeclaredMethod("changeQueenValue", ChessField.class);
	    m.setAccessible(true);
	    m.invoke(cb, cb.getChessFields().get(0).get(0));

	} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
	{
	    e.printStackTrace();
	}

	assertEquals(5.8, cb.getBoardValue(), 0.05);

    }

    @Test
    public void BeAbleToMutateWithinItsBoundaries()
    {
	ChessBoard cb = new ChessBoard(6);

	cb.setQueen(0, 0, true);
	cb.setQueen(5, 5, true);
	cb.setQueen(0, 5, true);
	cb.setQueen(5, 0, true);

	int cycleCount = 0;

	for(int x = 0; x < 5; x++)
	{
	    cb.mutateBoard();

	    int count = 0;

	    for(int i = 0; i < 6; i++)
	    {
		for (int j = 0; j < 6; j++)
		{
		    if(cb.getChessFields().get(i).get(j).isQueen())
		    {
			count++;
		    }
		}
	    }

	    if(count == 4)
	    {
		cycleCount++;
	    }
	}

	assertEquals(5, cycleCount);
    }

    @Test
    public void MirrorItsContentCorrectlyIfRecombined()
    {
	ChessBoard cb = new ChessBoard(6);

	cb.setQueen(1, 0, true);
	cb.setQueen(4, 1, true);
	cb.setQueen(5, 0, true);
	cb.setQueen(5, 5, true);
	cb.setQueen(1, 5, true);
	cb.setQueen(3, 3, true);

	cb.recombineBoard();

	int[] rowPositions = new int[6];
	int count = 0;
	
	for(int i = 0; i < 6; i++)
	{
	    for (int j = 0; j < 6; j++)
	    {
		if(cb.getChessFields().get(i).get(j).isQueen())
		{
		    rowPositions[count++] = j;
		}
	    }
	}
	
	assertEquals(rowPositions[0], 0);
	assertEquals(rowPositions[1], 5);
	assertEquals(rowPositions[2], 2);
	assertEquals(rowPositions[3], 4);
	assertEquals(rowPositions[4], 0);
	assertEquals(rowPositions[5], 5);
    }
    
    @Test
    public void HaveACorrectBoardValueAfterFieldStatusChange()
    {
	ChessBoard cb = new ChessBoard(6);
	
	cb.setQueen(0, 0, true);
	cb.setQueen(0, 1, true);
//	cb.setQueen(5, 0, true);
//	cb.setQueen(5, 5, true);
//	cb.setQueen(1, 5, true);
//	cb.setQueen(3, 3, true);
	
	cb.changeFieldStatus(0, 0);
	cb.changeFieldStatus(0, 1);
//	cb.changeFieldStatus(4, 1);
//	cb.changeFieldStatus(5, 0);
//	cb.changeFieldStatus(5, 5);
//	cb.changeFieldStatus(1, 5);
//	cb.changeFieldStatus(3, 3);
	
	for(ObservableList<ChessField> fields : cb.getChessFields())
	{
	    for(ChessField field : fields)
	    {
		System.out.print(field.getQueenValue() + " ");
	    }
	    System.out.println();
	}
	
//	System.out.println(cb.getBoardValue());
	
    }


}
