/*
    Maria Yampolsky
    Ms. Krasteva
    December 20th, 2018
    AlphaAttack - DropLetter class
    This class is the graphics portion of the AlphaAttack gameplay. It picks a random letter,
    a random position horizontally on screen and moves it down the screen. This runs as a Thread,
    as it has to run simultaneously with the input.

    Name            Type            Purpose
    c               Console         This stores the output Console
    a               AlphaAttack     This stores the instance of the main class
*/
import java.awt.*;
import hsa.Console;
import java.lang.*;

public class DropLetter extends Thread
{
    private Console c;           // The output console
    AlphaAttack a;

    /*
    This method is the class constructor, and it is where the instance of the output Console
    and AlphaAttack class we are using are passed.
    */
    public DropLetter (Console con, AlphaAttack alpha)
    {
	c = con;
	a = alpha;
    }


    /*
    This method is the method where the animation is done and the letters are moved down screen.
    
    Name                Type                Purpose
    i                   int                 This is the for loop variable used for the animation
    
    If Statements
    1) This if statement checks if there are any letters meant to be on screen, if there are not, the loop exits
    2) This if statement checks if the letter has reached the bottom, making the score lose 20 points
    3) This if statement checks if there are any letters meant to be on screen, and if so, it prints the new score 
    
    For Loops
    1) This for loop increases the y position by one for the letter and moves it down the screen
    
    Try-Catch Statements
    1) This try-catch statement is used for the delay in the animation, so it looks like the letters are moving down screen
    
    */
    public void animation (char letter, int xLoc)
    {
	String output = " ";
	a.letterNum++;
	output = Character.toString (letter);
	a.onScreen [((int) output.charAt (0)) - 97] = true;
	a.xLocation [((int) output.charAt (0)) - 97] = xLoc;
	for (int i = 0 ; i < 520 ; i++) //for loop 1
	{
	    synchronized (c)
	    {
		if (a.onScreen [((int) output.charAt (0)) - 97] == false) //if statement 1
		{
		    break;
		}

		c.setColor (new Color (168, 209, 247));
		c.fillRect (xLoc - 1, i - 30, 30, 30);
		c.setColor (Color.black);
		c.setFont (new Font ("Franklin Gothic Medium Cond", Font.PLAIN, 30));
		c.drawString (output, xLoc, i);
		a.yLocation [((int) output.charAt (0)) - 97] = i;
	    }
	    try //try-catch statement 1
	    {
		Thread.sleep ((4 - a.difficulty) * 5 - a.letterNum / 11);
	    }
	    catch (InterruptedException e)
	    {
	    }
	    if (i == 519) //if statement 2
		a.score -= 20;

	}
	if (a.containsTrue (a.onScreen) == true) //if statement 3
	{
	    synchronized (c)
	    {
		c.setColor (Color.white);
		c.fillRect (580, 480, 60, 20);
		c.setColor (Color.red);
		c.setFont (new Font ("Franklin Gothic Medium Cond", Font.PLAIN, 20));
		c.drawString ("" + a.score, 600, 498);
	    }
	}
	a.onScreen [((int) output.charAt (0)) - 97] = false;
	a.yLocation [((int) output.charAt (0)) - 97] = -1;
    }

    //This method returns a random x location for the letter to pop up at
    private int makeXLoc ()
    {
	return ((int) (Math.random () * 550));
    }

    /*
    This method returns a random letter to pop up on screen.
    
    Name            Type            Purpose
    letter          char            This variable stores the letter that will be returned.
    
    If statements
    1) This if statement checks if the letter is already on screen, and if so, it picks a new one
    
    */
    private char makeLetter ()
    {
	char letter;
	letter = (char) ((int) (Math.random () * 26 + 97));
	//if statement 1
	if (a.onScreen [letter - 97] == true || a.yLocation [letter - 97] > 0)
	    return makeLetter ();
	return letter;
    }

    //This method runs the Thread for this class. It is called in the AlphaAttack class.
    public void run ()
    {
	animation (makeLetter (), makeXLoc ());
    }
} // DropLetter class


