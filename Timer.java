import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Timer extends JFrame implements ActionListener,Runnable
{
	private long min,sec;
	boolean thread_stop;
	String s;
	Container c;
	JTextField minute,second,result;
	JButton start,stop;
	Timer(String str)
	{
		super(str);
	    c=this.getContentPane();
		this.setLayout(null);
		//Button
		start=new JButton("START");
		stop=new JButton("STOP");
		//Text Field
	    minute=new JTextField("0",2);
	    second=new JTextField("0",2);
	    result=new JTextField();
	    
	    minute.setBounds(250,320,150,30);
	    second.setBounds(550,320,150,30);
	    result.setBounds(250,200,450,30);
	    start.setBounds(280,450,100,30);
        stop.setBounds(580,450,100,30);
        
        
	    result.setEnabled(false);
	    stop.setEnabled(false);
	    
	    
	    minute.setToolTipText("Minute Field");
	    second.setToolTipText("Second Field");
	    result.setToolTipText("Result Box");
        
        
        start.addActionListener(this);
        stop.addActionListener(this);
        
        
        c.add(start);
        c.add(stop);
	    c.add(minute);
	    c.add(second);
	    c.add(result);
	}
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==start)
		{
			result.setText("Started!!");
			thread_stop=false;
			start.setEnabled(false);
			stop.setEnabled(true);
			minute.setEnabled(false);
			second.setEnabled(false);
			this.getMinAndSec();
		}
		if(ae.getSource()==stop)
		{
			result.setText("Stopped!!");
			thread_stop=true;
			start.setEnabled(true);
			stop.setEnabled(false);
			minute.setEnabled(true);
			second.setEnabled(true);
		}
		
	}
	public void run()
	{
		synchronized(this)
		{
			while(sec>=0&&!thread_stop)
			{
				try {
					Thread.sleep(1000);
					if(sec==0&&min>=1)
					{
						sec=60;
						min-=1;
					}
				}
				catch(InterruptedException ie) {}
				sec-=1;
				s=""+sec;
				second.setText(s);
				s=""+min;
				minute.setText(s);
				if(sec==0&&min==0)
				{
					result.setText("Time Over!!");
					stop.setEnabled(false);
					start.setEnabled(true);
					minute.setEnabled(true);
					second.setEnabled(true);
					return;
				}
			}
		}
	}
	void getMinAndSec()
	{
		try {
		min=Integer.parseInt(minute.getText());
		sec=Integer.parseInt(second.getText());
		if(min>=1&&sec==0)
		{
			sec=60;
			min-=1;
		}
		if(min==0&&sec==0||min<0||sec<0)
		{
			throw new NumberFormatException();
		}
		this.threadCreation();
		}
		catch(NumberFormatException n) {
			result.setText("Please enter a positive two digit number or just zero.");
			minute.setText("0");
			second.setText("0");
			return ;
		}
	}
	void threadCreation()
	{
		Thread t=new Thread(this);
		t.start();
	}
	public static void main(String args[])
	{
		Timer timer=new Timer("Timer Application");
		timer.setSize(1000,700);
		timer.setVisible(true);
		timer.setDefaultCloseOperation(Timer.EXIT_ON_CLOSE);
	}
}
