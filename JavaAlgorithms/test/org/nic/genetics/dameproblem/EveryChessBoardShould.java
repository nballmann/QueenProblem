package org.nic.genetics.dameproblem;

import static org.junit.Assert.*;
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
}
