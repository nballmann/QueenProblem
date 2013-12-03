package org.nic.genetics.dameproblem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

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

	assertEquals(new BigDecimal("0.8"), cf.getQueenValue());
    }
}
