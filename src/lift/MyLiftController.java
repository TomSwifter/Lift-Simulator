
package lift;

/**
 * Null implementation of a Lift Controller.
 * @author K. Bryson
 */
//this is the only class where I have to perform some work.
    //I need to add:
/**
 *synchronized methods and wait/notify/notifyAll
 * conditional synchronization to satisfy the specification given in the LiftController interface
 * */

public class MyLiftController implements LiftController {

	//9 possible floors to go up or down to:
	private boolean[] pressedUP = new boolean[9];
	private boolean[] pressedDOWN = new boolean[9];
	
	//9 floors to be at:
	private boolean[] liftToFloor = new boolean[9];
	private int floor;
	
	private boolean areDoorsOpen;
    Direction direction;
	
	


    /* Interface for People */
    public void pushUpButton(int floor) throws InterruptedException 
    {	
    	//going around synchronizing the entire method and causing a deadlock
    	synchronized(this) {
    		pressedUP[floor] = true;
    	}
    	
		// waiting on lift to arrive at the right floor and open its doors
        while(!(direction == Direction.UP && this.floor == floor && areDoorsOpen))
        {
        	//going around synchronizing the entire method and causing a deadlock
        	synchronized(this) {
        		this.wait();
        	}
        }
    }
    	

    public void pushDownButton(int floor) throws InterruptedException 
    {
    	//exactly the same as pushUPButton - 
    	//the instructor could actually provide one method - PushedButton
    	
    	synchronized(this) {
    		pressedDOWN[floor] = true;
    	}
    	
        // waiting on lift to arrive at the right floor and open its doors
        while(!(direction == Direction.DOWN && this.floor == floor && areDoorsOpen)) {
        	
        	synchronized(this) {
        		this.wait();
        	}
        	
        }

        
    }
    
    public void selectFloor(int floor) throws InterruptedException 
    {
    	synchronized(this) {
    		liftToFloor[floor] = true;
    	}
   
        //lift open doors at selected floor. Waiting for people to exit
        while(!(areDoorsOpen && this.floor == floor))
        	synchronized(this) {
        		this.wait();
        	}
        
    }

    
    /* Interface for Lifts */
    public boolean liftAtFloor(int floor, Direction direction) {
    	synchronized(this) {
    		this.direction = direction;
    		this.floor = floor;
    	}
    	    	
    	if(direction == Direction.UP)
    	{
    		if(pressedUP[floor] == true || liftToFloor[floor] == true)
    		{ 
    			return true;
    		}
    			
    			
    	}
    	else if (direction == Direction.DOWN)
    	{
    		if(pressedDOWN[floor] == true || liftToFloor[floor] == true)
    		{
    			return true;
    		}
    	}
    	
    	//default 
		return false;
    	
    }

    public void doorsOpen(int floor) throws InterruptedException 
    {
    	//calling synchronzied helper method to avoid deadlock 
    	_doorsOpen(floor);
    	
    	//making the thread sleep to run at the correct order
    	//value in sleep can be changed but higher values means slightly slower running time
    	Thread.sleep(5);
    }

    private synchronized void _doorsOpen(int floor) throws InterruptedException 
    {
    	//helper method for doorsOpen
    	
    	if(direction == Direction.UP) pressedUP[floor] = false;
    	if (direction == Direction.DOWN) pressedDOWN[floor] = false;
    	liftToFloor[floor] = false;
    	this.areDoorsOpen = true;
		notifyAll();
    }

    public synchronized void doorsClosed(int floor) {
    	this.areDoorsOpen = false;
    }
}
