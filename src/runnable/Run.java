package runnable;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import gui.View;

public class Run
{

    public static void main(String[] args)
    {
	try
	{
	    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
		| UnsupportedLookAndFeelException e)
	{
	    System.err.println(e.getMessage());
	}
	
	View view = new View();
	view.setLocationRelativeTo(null);
	view.setResizable(false);
	view.setTitle("Stamper | Sanbope");
	view.setVisible(true);
    }

}