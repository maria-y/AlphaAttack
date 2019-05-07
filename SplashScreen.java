/*
    Maria Yampolsky
    Ms. Krasteva
    December 20th, 2018
    AlphaAttack - SplashScreen class
    This is the introductory animation to the program of AlphaAttack. It shows a
    plane flying with a banner, and a fire ball dropping on the city, relating to
    the purpose of the game itself - defending your city from attack.

    Name            Type            Purpose
    c               Console         This stores the output Console

*/
import java.awt.*;
import hsa.Console;
import java.lang.*;

public class SplashScreen extends Thread
{
    private Console c;           // The output console

    /*
    This is the class constructor for the SplashScreen class.
    */
    public SplashScreen (Console con)
    {
	c = con;
    }


    /*
    This method has the animation for the program introduction, and draws the city, plane and fire ball.

    Name                Type            Purpose
    i                   int             This is used as a for loop counter variable
    y                   int             This is used as a for loop counter variable
    firex               int[]           This stores the x-coordinates of the triangular flame portion
    firex1              int[]           This stores the x-coordinates of the triangular flame portion
    firex2              int[]           This stores the x-coordinates of the triangular flame portion
    firey               int[]           This stores the y-coordinates of the triangular flame portion
    firey1              int[]           This stores the y-coordinates of the triangular flame portion
    firey2              int[]           This stores the y-coordinates of the triangular flame portion


    For Loops
    1) This loop draws the windows on the first building
    2) This loop draws the windows on the first building
    3) This loop draws the windows on the second building
    4) This loop draws the windows on the second building
    5) This loop draws the windows on the third building
    6) This loop draws the windows on the third building
    7) This loop draws the windows on the fifth building
    8) This loop draws the windows on the fifth building
    9) This loop draws the airplane and banner as they move
    10) This loop draws the flame falling above the building
    11) This loop draws the flame falling below the building

    */
    public void intro ()
    {

	c.setColor (new Color (168, 209, 247));
	c.fillRect (0, 0, 640, 500);

	//building 1
	c.setColour (new Color (168, 175, 175));
	c.fillRect (0, 200, 175, 300);
	c.setColour (new Color (116, 119, 122));
	for (int i = 0 ; i < 6 ; i++) //for loop 1
	{
	    for (int y = 0 ; y < 10 ; y++) //for loop 2
		c.fillRect (4 + i * 30, 210 + y * 30, 15, 15);
	}
	c.setColor (new Color (194, 210, 214));
	c.fillRect (64, 480, 45, 20);
	c.setColor (new Color (97, 103, 104));
	c.drawLine (64, 480, 64, 500);
	c.drawLine (109, 480, 109, 500);
	c.drawLine (86, 480, 86, 500);
	c.drawLine (64, 480, 109, 480);

	//building 2
	c.setColor (new Color (204, 206, 206));
	c.fillRect (175, 100, 125, 400);
	c.setColour (new Color (188, 215, 244));
	for (int i = 0 ; i < 4 ; i++) //for loop 3
	{
	    for (int y = 0 ; y < 13 ; y++)
		c.fillRect (185 + i * 30, 110 + y * 30, 15, 15); //for loop 4
	}
	c.setColour (new Color (220, 243, 247));
	c.fillRect (215, 470, 45, 30);
	c.setColor (new Color (179, 206, 211));
	c.drawLine (215, 470, 215, 500);
	c.drawLine (260, 470, 260, 500);
	c.drawLine (215, 470, 260, 470);
	c.drawLine (238, 470, 238, 500);

	//building 3
	c.setColor (new Color (84, 89, 89));
	c.fillRect (300, 300, 200, 200);
	c.setColor (new Color (208, 224, 226));
	for (int i = 0 ; i < 6 ; i++)
	{
	    for (int y = 0 ; y < 6 ; y++) //for loop 5
		c.fillRect (318 + i * 30, 318 + y * 30, 15, 15); //for loop 6
	}
	c.setColor (new Color (214, 214, 214));
	c.fillRect (378, 468, 45, 32);
	c.setColor (new Color (175, 175, 175));
	c.drawLine (378, 468, 378, 500);
	c.drawLine (423, 468, 423, 500);
	c.drawLine (378, 468, 423, 468);
	c.drawLine (401, 468, 401, 500);

	//building 4
	c.setColor (new Color (63, 62, 57));
	c.fillRect (500, 150, 140, 350);
	c.setColor (new Color (216, 216, 216));
	for (int i = 0 ; i < 4 ; i++) //for loop 7
	{
	    for (int y = 0 ; y < 10 ; y++)
		c.fillRect (510 + i * 35, 160 + y * 35, 15, 15); //for loop 8
	}
	c.setColor (new Color (156, 181, 193));
	c.fillRect (545, 475, 50, 25);
	c.setColor (new Color (189, 190, 191));
	c.drawLine (545, 475, 545, 500);
	c.drawLine (595, 475, 595, 500);
	c.drawLine (545, 475, 595, 475);
	c.drawLine (570, 475, 570, 500);

	//sun
	c.setColor (new Color (249, 245, 107));
	c.fillOval (490, 20, 80, 80);

	//clouds
	c.setColor (new Color (239, 246, 252));
	c.fillOval (450, 40, 80, 80);
	c.fillOval (420, 50, 65, 65);
	c.fillOval (500, 60, 60, 60);

	//airplane & banner
	for (int i = 0 ; i < 400 ; i++) //for loop 9
	{
	    synchronized (c)
	    {
		c.setColor (new Color (168, 209, 247));
		c.fillRect (701 - i, 95, 286, 40);
		c.setColor (new Color (239, 246, 252));
		c.fillOval (450, 40, 80, 80);
		c.fillOval (420, 50, 65, 65);
		c.fillOval (500, 60, 60, 60);
		c.setColor (new Color (194, 195, 196));
		c.fillOval (700 - i, 100, 70, 30);
		c.fillOval (760 - i, 95, 20, 40);
		c.setColor (new Color (168, 0, 5));
		c.fillRect (785 - i, 95, 200, 40);
		c.setColor (Color.black);
		c.drawLine (775 - i, 100, 785 - i, 100);
		c.drawLine (775 - i, 130, 785 - i, 130);
		c.setColor (Color.white);
		c.setFont (new Font ("Franklin Gothic Medium Cond", Font.PLAIN, 40));
		c.drawString ("AlphaAttack", 795 - i, 126);
		try
		{
		    Thread.sleep (10);
		}
		catch (InterruptedException e)
		{
		}
	    }
	}


	for (int i = -91 ; i < 110 ; i++) //for loop 10
	{
	    c.setColor (new Color (168, 209, 247));
	    c.fillRect (50, 24 + i, 40, 67);
	    c.setColor (new Color (242, 182, 72));
	    c.fillOval (50, 50 + i, 40, 40);
	    int firex[] = {50, 90, 70};
	    int firey[] = {65 + i, 65 + i, 25 + i};
	    c.fillPolygon (firex, firey, 3);
	    c.setColor (new Color (242, 209, 150));
	    c.fillOval (55, 58 + i, 32, 32);
	    int firex1[] = {55, 87, 70};
	    int firey1[] = {74 + i, 74 + i, 34 + i};
	    c.fillPolygon (firex1, firey1, 3);
	    c.setColor (new Color (249, 224, 99));
	    c.fillOval (58, 65 + i, 26, 26);
	    int firex2[] = {58, 84, 71};
	    int firey2[] = {78 + i, 78 + i, 48 + i};
	    c.fillPolygon (firex2, firey2, 3);
	    try
	    {
		Thread.sleep (10);
	    }
	    catch (InterruptedException e)
	    {
	    }
	}
	for (int i = 110 ; i < 177 ; i++) //for loop 11
	{
	    synchronized (c)
	    {
		c.setColor (new Color (168, 209, 247));
		c.fillRect (50, 24 + i, 40, 67);
		c.setColor (new Color (242, 182, 72));
		c.fillOval (50, 50 + i, 40, 40);
		int firex[] = {50, 90, 70};
		int firey[] = {65 + i, 65 + i, 25 + i};
		c.fillPolygon (firex, firey, 3);
		c.setColor (new Color (242, 209, 150));
		c.fillOval (55, 58 + i, 32, 32);
		int firex1[] = {55, 87, 70};
		int firey1[] = {74 + i, 74 + i, 34 + i};
		c.fillPolygon (firex1, firey1, 3);
		c.setColor (new Color (249, 224, 99));
		c.fillOval (58, 65 + i, 26, 26);
		int firex2[] = {58, 84, 71};
		int firey2[] = {78 + i, 78 + i, 48 + i};
		c.fillPolygon (firex2, firey2, 3);

		c.setColour (new Color (168, 175, 175));
		c.fillRect (0, 200, 175, 67);
		c.setColour (new Color (116, 119, 122));
		for (int x = 0 ; x < 6 ; x++)
		{
		    for (int y = 0 ; y < 2 ; y++)
			c.fillRect (4 + x * 30, 210 + y * 30, 15, 15);
		}
		try
		{
		    Thread.sleep (10);
		}
		catch (InterruptedException e)
		{
		}
	    }
	}
	try
	{
	    Thread.sleep (700);
	}
	catch (InterruptedException e)
	{
	}
    }


    /*
    This method is the run method, and it is used to begin the running of the Thread.
    */
    public void run ()
    {
	intro ();
    }
} // SplashScreen class
