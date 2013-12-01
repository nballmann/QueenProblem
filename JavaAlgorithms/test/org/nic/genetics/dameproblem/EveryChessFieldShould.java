package org.nic.genetics.dameproblem;

import org.junit.Test;
import static org.junit.Assert.*;

public class EveryChessFieldShould
{
	@Test
	public void KnowIfItsFree()
	{
		ChessField cf = new ChessField();

		assertTrue(cf.isFree());
	}

	@Test
	public void BeAbleToHaveAQueenOnIt()
	{
		ChessField cf = new ChessField();

		cf.setQueen(true);

		assertTrue(cf.isQueen());
	}

	@Test
	public void makeADeepCopyOfItself()
	{
		ChessField original = new ChessField();
		
		ChessField copy = original.deepCopy();
		
		assertTrue(original != copy);
	}
	
	@Test
	public void BeAbleToDecrementItsValue()
	{
		ChessField cf = new ChessField();
		
		cf.decrementQueenValue();
		
		assertEquals(0.8, cf.getQueenValue(), 0.01);
	}
}
