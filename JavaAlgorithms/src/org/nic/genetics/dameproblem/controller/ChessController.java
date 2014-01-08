package org.nic.genetics.dameproblem.controller;

import java.math.BigDecimal;
import java.util.Random;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import org.nic.genetics.dameproblem.ChessBoard;

public class ChessController
{
    /*
     * VARIANZ: maximum amount to offset a queen in case of mutation
     */
    public static final int VARIANZ = 6;

    /*
     *  MUTATION_PROPABILITY: the chance of mutation (1.0 = 100%)
     */
    public static final double MUTATION_PROPABILITY = 0.2;

    public static final int POPULATION_SIZE = 8;

    /*
     * the probability for the fittest individual to be eliminated by natural selection 
     */
    private static final float ELEMINATION_PROPABILITY = 0.08f;

    public static int MUTATION_SIZE, RECOMBINE_SIZE;

    public static int ROW_LENGTH;

    public static int REBASE;

    @FXML
    private GridPane chessGrid;   

    private AnchorPane parent;

    public ChessBoard chessBoard;

    private Random random = new Random();

    private long startTime, endTime;

    private ObservableList<ChessBoard> resultPool = FXCollections.observableArrayList();
    private ObservableList<ChessBoard> completeResultPool = FXCollections.observableArrayList();

    public ObservableList<AnchorPane> paneList = FXCollections.observableArrayList();

    private long iterations = 0;

    public ChessController() { }

    public void init(final int fieldCount, final AnchorPane parent)
    {
	ROW_LENGTH = fieldCount;

	REBASE = fieldCount-1;

	MUTATION_SIZE = (int)Math.ceil(new Double(POPULATION_SIZE)/2);
	RECOMBINE_SIZE = (int)Math.floor(new Double(POPULATION_SIZE)/2);

	this.parent = parent;

	chessBoard = new ChessBoard(fieldCount);

	chessBoard.resetBoardValue();

	generateInitialResultSet();

	if(!chessGrid.getChildren().isEmpty())
	{
	    chessGrid.getChildren().clear();
	}

	if(!paneList.isEmpty())
	{
	    paneList.clear();
	}

	final int chessboardSize =  chessBoard.getChessFields().size();
	for (int i = 0; i < chessboardSize; i++)
	{
	    for ( int j = 0; j < chessboardSize; j++)
	    {
		AnchorPane pane = new AnchorPane();

		if(i%2==0)
		{
		    if(j%2!=0)
		    {
			pane.setStyle("-fx-background-color: black;");
		    }
		    else
		    {
			pane.setStyle("-fx-background-color: white;");
		    }
		}
		else
		{
		    if(j%2!=0)
		    {
			pane.setStyle("-fx-background-color: white;");
		    }
		    else
		    {
			pane.setStyle("-fx-background-color: black;");
		    }
		}

		pane.setPrefSize(35, 35);

		chessGrid.add(pane, j, i);

		paneList.add(pane); 

	    }
	}
	//	parent.getChildren().add(chessGrid);
    }

    private BigDecimal boardStatus()
    {
	BigDecimal actualBest = BigDecimal.ZERO;

	for(ChessBoard b : resultPool)
	{
	    if(b.getBoardValue().compareTo(actualBest) > 0)
	    {
		actualBest = b.getBoardValue();
		chessBoard = b;
		//		System.out.println(b.getBoardValue());
	    }
	}

	return actualBest;
    }

    /**
     * starts the genetic solving process:<br>
     * <ol>
     * <li>generate initial result set</li>
     * <li>mutate 3 random results</li>
     * <li>generate 3 children out of 3 random pairs with recombined parameters</li>
     * <li>reduce result pool to its original size of 6 (through selection)</li>
     * </ol>
     */
    public void startSolve()
    {
	BigDecimal toReach = new BigDecimal(ROW_LENGTH);

	startTime = System.currentTimeMillis();

	while(boardStatus().compareTo(toReach) != 0)
	{
	    // mutate
	    mutate();

	    // recombine
	    recombine();

	    //reset boardValues
	    //	    int c = 0;
	    for(ChessBoard board : completeResultPool)
	    {
		board.resetBoardValue();
		//		System.out.print((c<3?"m":"r") + board.getBoardValue() + "\t");
		//		c++;
	    }
	    for(ChessBoard board : resultPool)
	    {
		board.resetBoardValue();
		//		System.out.print(board.getBoardValue() + "\t");
	    }

	    // select
	    applySelection();

	    //	    if(iterations%100 == 0)
	    //	    {
	    //		System.out.println(chessBoard.getBoardValue());
	    //	    }

	    iterations++;

	    Platform.runLater(new Runnable() {

		@Override
		public void run()
		{
		    if(iterations%100 == 0)
		    {
			System.out.println(chessBoard.getBoardValue());
			redrawBoard();
			drawQueens();
		    }
		}

	    });
	}

	endTime = System.currentTimeMillis();
	System.out.println("FINAL: " + boardStatus() + "\nIterations: " + iterations);
	redrawBoard();
	showPerfectResult();

	Platform.runLater(new Runnable() {

	    @Override
	    public void run()
	    {
		Label l = new Label(String.valueOf((endTime - startTime)/1000f) + "s");
		l.setStyle("-fx-text-fill:red;");
		parent.getChildren().add(l);
	    }

	});
    }

    /**
     * Initial results based of first random ChessBoard<br>
     * fills the resultPool list with the specified number (POPULATION_SIZE)<br> of identical results
     */
    private void generateInitialResultSet()
    {
	for(int i = 0; i < POPULATION_SIZE; i++)
	    resultPool.add(chessBoard.deepCopy());
    }

    /**
     * Take half(round up) of the results from the result pool,<br>
     * copy them<br>
     * and randomly mutate the queen positions 
     */
    private void mutate()
    {
	//int[] mutators = threeOutOfSix();

	shuffleResultPool(resultPool);

	if(!completeResultPool.isEmpty())
	{
	    completeResultPool.clear();
	}

	for(int i = 0; i < MUTATION_SIZE; i++)
	{
	    completeResultPool.add(resultPool.get(i).deepCopy());
	    completeResultPool.get(i).mutateBoard();
	}
    }

    /**
     * generate random result pairs<br>
     */
    private void recombine()
    {
	//int[][] parents = generateRandomPairs();

	int otherParent = POPULATION_SIZE -1;

	for(int i = 0; i < RECOMBINE_SIZE; i++)
	{
	    completeResultPool.add(resultPool.get(i).generateChild(resultPool.get(otherParent--)));
	}
    }

    /**
     * generate pairs out of resultPool and discard the worst<br>
     * -> pool back to initial results of average better quality
     */
    private void applySelection()
    {
	// add resultPool to completeResultPool
	completeResultPool.addAll(resultPool);

	// random sort array
	shuffleResultPool(completeResultPool);

	// clear resultPool
	resultPool.clear();

	int x;

	for(int i = 0; i < POPULATION_SIZE; i++)
	{
	    // get random pair from completeResultPool
	    ChessBoard a, b;
	    x = random.nextInt(completeResultPool.size());

	    a = completeResultPool.get(x);
	    completeResultPool.remove(x);

	    x = random.nextInt(completeResultPool.size());

	    b = completeResultPool.get(x);
	    completeResultPool.remove(x);

	    // discard the one with worst value (or first if equal)
	    // add selected result to resultPool
	    if(a.getBoardValue().compareTo(b.getBoardValue()) > 0)
	    {
		if(random.nextFloat() < ELEMINATION_PROPABILITY)
		{
		    resultPool.add(b);
		}
		else 
		{
		    resultPool.add(a);
		}
		
	    }
	    else
	    {
		resultPool.add(b);
	    }
	}
	// after: resultPool.size == POPULATION_SIZE completeResultPool.size == 0
    }


    /**
     * Fischer-Yates Shuffle implementation for 
     * ObservableList Arrays<br>
     * sorts the resultPool randomly
     */   
    private void shuffleResultPool(final ObservableList<ChessBoard> pool) {

	for( int i = pool.size() ; --i > 0; ) 
	{
	    int index = random.nextInt(i);
	    ChessBoard tmp = pool.get(index);
	    pool.set(index, pool.get(i));
	    pool.set(i, tmp);
	}
    }

//    /**
//     * Returns three unique random numbers between 0 and 5 (included)
//     * @return
//     */
//    private int[] threeOutOfSix()
//    {
//	int a = random.nextInt(6), b = random.nextInt(6), c = random.nextInt(6);
//
//	if(a!=b && a!=c && b!=c)
//	{
//	    return new int[] {a,b,c};
//	}
//	else
//	{
//	    threeOutOfSix();
//	}
//
//	return new int[] {0,1,2};
//    }

//    /** 
//     * returns three pairs out of the 6 unmodified results
//     * @return 
//     */
//    private int[][] generateRandomPairs()
//    {
//	int[] lhs = threeOutOfSix();
//	int[] rhs = new int[] {-1,-1,-1};
//
//	for(int i = 0; i < 3; i++)
//	{
//	    int r = random.nextInt(6);
//
//	    for (int j = 0; j < 3; j++)
//	    {
//		if(lhs[j] == r || rhs[j] == r)
//		{
//		    i--;
//		    break;
//		} 
//		else if (j==2)
//		    rhs[i] = r;
//	    }
//	}
//
//	return new int[][] {{lhs[0],rhs[0]},{lhs[1],rhs[1]},{lhs[2],rhs[2]}};
//    }

    private void showPerfectResult()
    {
	for(int i = 0; i < ROW_LENGTH; i++)
	{
	    for (int j = 0; j < ROW_LENGTH; j++)
	    {
		if(chessBoard.getChessFields().get(i).get(j).isQueen())
		{
		    System.out.print("X ");
		    chessGrid.getChildren().get((i*ROW_LENGTH)+j).setStyle("-fx-background-color: yellow;");
		}
		else
		{
		    System.out.print("O ");
		}
	    }
	    System.out.println();
	}
    }

    private void drawQueens()
    {
	for(int i = 0; i < ROW_LENGTH; i++)
	{
	    for (int j = 0; j < ROW_LENGTH; j++)
	    {
		if(chessBoard.getChessFields().get(i).get(j).isQueen())
		{
		    chessGrid.getChildren().get((i*ROW_LENGTH)+j).setStyle("-fx-background-color: yellow;");
		}
	    }
	}
    }

    private void redrawBoard()
    {
	for(int i = 0; i < ROW_LENGTH; i++)
	{
	    for (int j = 0; j < ROW_LENGTH; j++)
	    {
		if(i%2==0)
		{
		    if(j%2!=0)
		    {
			chessGrid.getChildren().get((i*ROW_LENGTH)+j).setStyle("-fx-background-color: black;");
		    }
		    else
		    {
			chessGrid.getChildren().get((i*ROW_LENGTH)+j).setStyle("-fx-background-color: white;");
		    }
		}
		else
		{
		    if(j%2!=0)
		    {
			chessGrid.getChildren().get((i*ROW_LENGTH)+j).setStyle("-fx-background-color: white;");
		    }
		    else
		    {
			chessGrid.getChildren().get((i*ROW_LENGTH)+j).setStyle("-fx-background-color: black;");
		    }
		}
	    }
	}
    }


}
