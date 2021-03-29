package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class View extends JFrame
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JPanel contentPane, panel;
    private JTextField txtPrint;
    private JTextField txtBackground;
    private JLabel imgBackground, imgPrint;
    private int index = 1, numImages;
    private String documentsPath = System.getProperty("user.home") + File.separatorChar + "Documents"
	    + File.separatorChar + "Stamper" + File.separatorChar;

    private String printPath = "", backgroundPath = "";
    private Point offset, offset2;
    private JLabel squares[];

    /**
     * Create the frame.
     */
    public View()
    {
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 758, 557);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	contentPane.setLayout(null);

	JLabel lblBackground = new JLabel("Custom background:");
	lblBackground.setForeground(Color.LIGHT_GRAY);
	lblBackground.setBounds(397, 100, 196, 19);
	contentPane.add(lblBackground);

	JLabel lblPrint = new JLabel("Print:");
	lblPrint.setForeground(Color.LIGHT_GRAY);
	lblPrint.setBounds(397, 12, 196, 19);
	contentPane.add(lblPrint);

	panel = new JPanel();
	panel.setBounds(12, 12, 367, 452);
	contentPane.add(panel);
	panel.setLayout(null);

	imgPrint = new JLabel("");
	imgPrint.addMouseListener(new MouseAdapter()
	{
	    @Override
	    public void mousePressed(MouseEvent e)
	    {
		offset = e.getPoint();
		hideResizers();
	    }

	    @Override
	    public void mouseReleased(MouseEvent e)
	    {
		showResizers();
		actualizar();
	    }
	});
	imgPrint.addMouseMotionListener(new MouseMotionAdapter()
	{
	    @Override
	    public void mouseDragged(MouseEvent e)
	    {
		int x = e.getPoint().x - offset.x;
		int y = e.getPoint().y - offset.y;
		Point location = imgPrint.getLocation();
		location.x += x;
		location.y += y;
		imgPrint.setLocation(location);
	    }
	});

	squares = new JLabel[3];

	for (int i = 0; i < squares.length; i++)
	{
	    squares[i] = new JLabel();
	    squares[i].setSize(15, 15);
	    squares[i].setVisible(true);
	    squares[i].setIcon(resizeImage("src/assets/square.png", 8, 8));

	    squares[i].addMouseListener(new MouseAdapter()
	    {
		@Override
		public void mousePressed(MouseEvent e)
		{
		    offset2 = e.getPoint();
		}

		@Override
		public void mouseReleased(MouseEvent e)
		{
		}
	    });
	    squares[i].addMouseMotionListener(new MouseMotionAdapter()
	    {
		@Override
		public void mouseDragged(MouseEvent e)
		{
		    int x = e.getPoint().x - offset2.x;
		    int y = e.getPoint().y - offset2.y;
		    Point location = e.getComponent().getLocation();

		    if (e.getComponent() == squares[0])
		    {
			moveAndResize(e.getComponent(), location, x, 0);
		    } else if (e.getComponent() == squares[1])
		    {
			moveAndResize(e.getComponent(), location, 0, y);
		    } else if (e.getComponent() == squares[2])
		    {
			moveAndResize(e.getComponent(), location, x, x);
		    }
		    imgPrint.setIcon(resizeImage(txtPrint.getText(), imgPrint.getWidth(), imgPrint.getHeight()));
		    putResizers();
		}
	    });

	    panel.add(squares[i]);
	}

	imgPrint.setBounds(51, 119, 180, 180);
	panel.add(imgPrint);

	imgBackground = new JLabel("");
	imgBackground.setBounds(0, 0, 367, 452);
	panel.add(imgBackground);

	txtPrint = new JTextField();
	txtPrint.addKeyListener(new KeyAdapter()
	{
	    @Override
	    public void keyTyped(KeyEvent e)
	    {
		if (e.getKeyChar() == '\n')
		{
		    actualizar();
		}
	    }
	});
	txtPrint.setBounds(391, 32, 338, 19);
	contentPane.add(txtPrint);
	txtPrint.setColumns(10);

	JButton btnOpen = new JButton("Open");
	btnOpen.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent e)
	    {
		txtPrint.setText(open(printPath));
		printPath = txtPrint.getText();
		actualizar();
	    }
	});
	btnOpen.setFocusable(false);
	btnOpen.setFocusPainted(false);
	btnOpen.setBounds(612, 63, 117, 25);
	contentPane.add(btnOpen);

	txtBackground = new JTextField();
	txtBackground.setBounds(391, 126, 338, 19);
	txtBackground.addKeyListener(new KeyAdapter()
	{
	    @Override
	    public void keyTyped(KeyEvent e)
	    {
		if (e.getKeyChar() == '\n')
		{
		    actualizar();
		}
	    }
	});
	contentPane.add(txtBackground);
	txtBackground.setColumns(10);

	JButton btnOpen_1 = new JButton("Open");
	btnOpen_1.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent e)
	    {
		txtBackground.setText(open(backgroundPath));
		backgroundPath = txtBackground.getText();
		actualizar();
	    }
	});
	btnOpen_1.setFocusable(false);
	btnOpen_1.setFocusPainted(false);
	btnOpen_1.setBounds(612, 157, 117, 25);
	contentPane.add(btnOpen_1);

	JButton btnRight = new JButton(">");
	btnRight.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent e)
	    {
		cargarNumImages();
		index++;
		txtBackground.setText("");
		if (index > numImages)
		{
		    index = 1;
		}
		actualizar();
	    }
	});
	btnRight.setFocusable(false);
	btnRight.setFocusPainted(false);
	btnRight.setBounds(262, 476, 117, 25);
	contentPane.add(btnRight);

	JButton btnLeft = new JButton("<");
	btnLeft.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent e)
	    {
		cargarNumImages();
		index--;
		txtBackground.setText("");
		if (index <= 0)
		{
		    index = numImages;
		}
		actualizar();
	    }
	});
	btnLeft.setFocusable(false);
	btnLeft.setFocusPainted(false);
	btnLeft.setBounds(12, 476, 117, 25);
	contentPane.add(btnLeft);

	JButton btnHidePivots = new JButton("Hide pivots");
	btnHidePivots.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent e)
	    {
		hideResizers();
	    }
	});
	btnHidePivots.setBounds(397, 220, 117, 25);
	btnHidePivots.setFocusable(false);
	btnHidePivots.setFocusPainted(false);
	contentPane.add(btnHidePivots);

	actualizar();
    }

    public void moveAndResize(Component component, Point location, int x, int y)
    {
	location.x += x;
	location.y += y;
	component.setLocation(location);
	Dimension size = imgPrint.getSize();
	size.width += x;
	size.height += y;
	imgPrint.setSize(size);
    }

    public void cargarNumImages()
    {
	File images = new File(documentsPath + "background");
	if (images.exists())
	{
	    numImages = images.listFiles().length;
	    System.out.println(numImages + "");
	} else
	{
	    images.mkdirs();
	}
    }

    public String open()
    {
	return open("");
    }

    public String open(String defaultPath)
    {
	JFileChooser fc = new JFileChooser(defaultPath);

	int op = fc.showOpenDialog(this);

	if (op == JFileChooser.APPROVE_OPTION)
	{
	    return fc.getSelectedFile().getAbsolutePath();
	}

	return "";
    }

    public ImageIcon resizeImage(String url, int width, int height)
    {
	ImageIcon imageIcon = new ImageIcon(url); // load the image to a imageIcon
	Image image = imageIcon.getImage(); // transform it
	Image newimg = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
	imageIcon = new ImageIcon(newimg);
	return imageIcon;
    }

    public void putResizers()
    {
	squares[0].setLocation(
		imgPrint.getLocation().x - (int) (squares[0].getWidth() * 0.5) + (int) (imgPrint.getWidth()),
		imgPrint.getLocation().y - (int) (squares[0].getHeight() * 0.5) + (int) (imgPrint.getHeight() * 0.5));
	squares[1].setLocation(
		imgPrint.getLocation().x - (int) (squares[0].getWidth() * 0.5) + (int) (imgPrint.getWidth() * 0.5),
		imgPrint.getLocation().y - (int) (squares[0].getHeight() * 0.5) + (int) (imgPrint.getHeight()));
	squares[2].setLocation(
		imgPrint.getLocation().x - (int) (squares[0].getWidth() * 0.5) + (int) (imgPrint.getWidth()),
		imgPrint.getLocation().y - (int) (squares[0].getHeight() * 0.5) + (int) (imgPrint.getHeight()));

    }

    public void showResizers()
    {
	if (squares != null)
	{
	    for (int i = 0; i < squares.length; i++)
	    {
		squares[i].setVisible(true);
	    }
	    System.out.println("show");
	}
    }

    public void hideResizers()
    {
	if (squares != null)
	{
	    for (int i = 0; i < squares.length; i++)
	    {
		squares[i].setVisible(false);
	    }
	    System.out.println("hide");
	}
    }

    public void actualizar()
    {
	if (!txtBackground.getText().isEmpty())
	{
	    index = 0;
	    imgBackground
		    .setIcon(resizeImage(txtBackground.getText(), imgBackground.getWidth(), imgBackground.getHeight()));
	} else
	{
	    File backgrounds = new File(documentsPath + "background");
	    imgBackground.setIcon(resizeImage(backgrounds.listFiles()[index - 1].getAbsolutePath(),
		    imgBackground.getWidth(), imgBackground.getHeight()));
	}
	if (!txtPrint.getText().isEmpty())
	{
	    imgPrint.setIcon(resizeImage(txtPrint.getText(), imgPrint.getWidth(), imgPrint.getHeight()));
	    showResizers();
	    putResizers();
	} else
	{
	    hideResizers();
	}
    }
}
