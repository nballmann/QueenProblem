package org.nic.genetics.dameproblem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import org.junit.Test;
import org.nic.genetics.dameproblem.controller.ChessController;

public class EveryChessControllerShould
{
    @Test
    public void initializeCorrectly()
    {
	ChessController cc = new ChessController();
	AnchorPane parent = new AnchorPane();
	
	Field pane;
	try
	{
	    pane = cc.getClass().getDeclaredField("chessGrid");
	    pane.setAccessible(true);
	    pane.set(cc, new GridPane());
	    
	} catch (NoSuchFieldException | SecurityException e)
	{
	    e.printStackTrace();
	} catch (IllegalArgumentException e)
	{
	    e.printStackTrace();
	} catch (IllegalAccessException e)
	{
	    e.printStackTrace();
	}
	
	cc.init(6, parent);
	
	assertEquals(36, cc.paneList.size());
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void generateAInitialResultSetOfCorrectSize()
    {
	ChessController cc = new ChessController();
	AnchorPane parent = new AnchorPane();
	
	Field pane;
	Field resultPool;
	int size = 0;
	try
	{
	    resultPool = cc.getClass().getDeclaredField("resultPool");
	    resultPool.setAccessible(true);
	    pane = cc.getClass().getDeclaredField("chessGrid");
	    pane.setAccessible(true);
	    pane.set(cc, new GridPane());
	    
	    cc.init(6, parent);
	    
	    size = ((ObservableList<ChessBoard>)(resultPool.get(cc))).size();
	    
	} catch (NoSuchFieldException | SecurityException e)
	{
	    e.printStackTrace();
	} catch (IllegalArgumentException e)
	{
	    e.printStackTrace();
	} catch (IllegalAccessException e)
	{
	    e.printStackTrace();
	}
	
	assertEquals(6, size);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void generateCorrectlyMutatedChildren()
    {
	ChessController cc = new ChessController();
	AnchorPane parent = new AnchorPane();
	
	Field pane;
	Field list;
	Method m;
	int size = 0;
	
	try
	{
	    pane = cc.getClass().getDeclaredField("chessGrid");
	    pane.setAccessible(true);
	    pane.set(cc, new GridPane());
	    
	    list = cc.getClass().getDeclaredField("completeResultPool");
	    list.setAccessible(true);
	    
	    cc.init(6, parent);
	    
	    m = cc.getClass().getDeclaredMethod("mutate", new Class<?>[] {});
	    m.setAccessible(true);
	    m.invoke(cc, new Object[] {});
	    
	    size = ((ObservableList<ChessBoard>)(list.get(cc))).size();
	    
	} catch (NoSuchFieldException | SecurityException e)
	{
	    e.printStackTrace();
	} catch (IllegalArgumentException e)
	{
	    e.printStackTrace();
	} catch (IllegalAccessException e)
	{
	    e.printStackTrace();
	} catch (NoSuchMethodException e)
	{
	    e.printStackTrace();
	} catch (InvocationTargetException e)
	{
	    e.printStackTrace();
	}

	assertEquals(3, size);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void generateCorrectlyRecombinedChildren()
    {
	ChessController cc = new ChessController();
	AnchorPane parent = new AnchorPane();
	
	Field pane;
	Field list;
	Method m;
	int size = 0;
	
	try
	{
	    pane = cc.getClass().getDeclaredField("chessGrid");
	    pane.setAccessible(true);
	    pane.set(cc, new GridPane());
	    
	    list = cc.getClass().getDeclaredField("completeResultPool");
	    list.setAccessible(true);
	    
	    cc.init(6, parent);
	    
	    m = cc.getClass().getDeclaredMethod("recombine", new Class<?>[] {});
	    m.setAccessible(true);
	    m.invoke(cc, new Object[] {});
	    
	    size = ((ObservableList<ChessBoard>)(list.get(cc))).size();
	    
	} catch (NoSuchFieldException | SecurityException e)
	{
	    e.printStackTrace();
	} catch (IllegalArgumentException e)
	{
	    e.printStackTrace();
	} catch (IllegalAccessException e)
	{
	    e.printStackTrace();
	} catch (NoSuchMethodException e)
	{
	    e.printStackTrace();
	} catch (InvocationTargetException e)
	{
	    e.printStackTrace();
	}

	assertEquals(3, size);
    }
    
    @Test
    public void generateThreeUniqueRandomNumbers()
    {
	ChessController cc = new ChessController();
	
	Method m;
	int[] values = null;
	
	try
	{
	    m = cc.getClass().getDeclaredMethod("threeOutOfSix", new Class<?>[] {});
	    m.setAccessible(true);
	    
	    values = (int[])m.invoke(cc, new Object[] {});
	    
	} catch (NoSuchMethodException | SecurityException e)
	{
	    e.printStackTrace();
	} catch (IllegalAccessException e)
	{
	    e.printStackTrace();
	} catch (IllegalArgumentException e)
	{
	    e.printStackTrace();
	} catch (InvocationTargetException e)
	{
	    e.printStackTrace();
	}
	
	assertTrue(values.length == 3 && values[0] != values[1] && values[1] != values[2]);
    }
    
    @Test
    public void generateCorrectRandomPairs()
    {
	ChessController cc = new ChessController();
	
	Method m;
	int[][] pairs = null;
	
	try
	{
	    m = cc.getClass().getDeclaredMethod("generateRandomPairs", new Class<?>[] {});
	    m.setAccessible(true);
	    
	    pairs = (int[][])m.invoke(cc, new Object[] {});
	    
	} catch (NoSuchMethodException | SecurityException e)
	{
	    e.printStackTrace();
	} catch (IllegalAccessException e)
	{
	    e.printStackTrace();
	} catch (IllegalArgumentException e)
	{
	    e.printStackTrace();
	} catch (InvocationTargetException e)
	{
	    e.printStackTrace();
	}
	
	assertTrue(pairs[0][0] != pairs[0][1]);
	assertTrue(pairs[1][0] != pairs[1][1]);
	assertTrue(pairs[2][0] != pairs[2][1]);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void haveSixChildrenBeforeSelection()
    {
	ChessController cc = new ChessController();
	AnchorPane parent = new AnchorPane();
	
	Field pane;
	Field list;
	Method m, n;
	int size = 0;
	
	try
	{
	    pane = cc.getClass().getDeclaredField("chessGrid");
	    pane.setAccessible(true);
	    pane.set(cc, new GridPane());
	    
	    list = cc.getClass().getDeclaredField("completeResultPool");
	    list.setAccessible(true);
	    
	    cc.init(6, parent);
	    
	    m = cc.getClass().getDeclaredMethod("mutate", new Class<?>[] {});
	    m.setAccessible(true);
	    m.invoke(cc, new Object[] {});
	    
	    
	    n = cc.getClass().getDeclaredMethod("recombine", new Class<?>[] {});
	    n.setAccessible(true);
	    n.invoke(cc, new Object[] {});
	    
	    size = ((ObservableList<ChessBoard>)(list.get(cc))).size();
	    
	    
	} catch (NoSuchFieldException | SecurityException e)
	{
	    e.printStackTrace();
	} catch (IllegalArgumentException e)
	{
	    e.printStackTrace();
	} catch (IllegalAccessException e)
	{
	    e.printStackTrace();
	} catch (NoSuchMethodException e)
	{
	    e.printStackTrace();
	} catch (InvocationTargetException e)
	{
	    e.printStackTrace();
	}
	
	assertEquals(6, size);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void applyTheSelectionCorrectly()
    {
	ChessController cc = new ChessController();
	AnchorPane parent = new AnchorPane();
	
	Field pane = null;
	Field list = null;
	Field pool = null;
	Method m, n, o;
	
	try
	{
	    pane = cc.getClass().getDeclaredField("chessGrid");
	    pane.setAccessible(true);
	    pane.set(cc, new GridPane());
	    
	    list = cc.getClass().getDeclaredField("completeResultPool");
	    list.setAccessible(true);
	    
	    pool = cc.getClass().getDeclaredField("resultPool");
	    pool.setAccessible(true);
	    
	    cc.init(6, parent);
	    
	    m = cc.getClass().getDeclaredMethod("mutate", new Class<?>[] {});
	    m.setAccessible(true);
	    m.invoke(cc, new Object[] {});
	    
	    n = cc.getClass().getDeclaredMethod("recombine", new Class<?>[] {});
	    n.setAccessible(true);
	    n.invoke(cc, new Object[] {});
	    
	    o = cc.getClass().getDeclaredMethod("applySelection", new Class<?>[] {});
	    o.setAccessible(true);
	    o.invoke(cc, new Object[] {});
	    
	} catch (NoSuchMethodException | SecurityException e)
	{
	    e.printStackTrace();
	} catch (IllegalAccessException e)
	{
	    e.printStackTrace();
	} catch (IllegalArgumentException e)
	{
	    e.printStackTrace();
	} catch (InvocationTargetException e)
	{
	    e.printStackTrace();
	} catch (NoSuchFieldException e)
	{
	    e.printStackTrace();
	}
	
	try
	{
	    assertEquals(6, ((ObservableList<ChessBoard>)(pool.get(cc))).size());
	    assertEquals(0, ((ObservableList<ChessBoard>)(list.get(cc))).size());
	} catch (IllegalArgumentException | IllegalAccessException e)
	{
	    e.printStackTrace();
	}
	
    }
    
    
}
