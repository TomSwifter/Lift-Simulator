
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


    /* Interface for People */
    public void pushUpButton(int floor) {

    }

    public void pushDownButton(int floor) {

    }
    
    public void selectFloor(int floor) {

    }

    
    /* Interface for Lifts */
    public boolean liftAtFloor(int floor, Direction direction) {

        return true;
    }

    public void doorsOpen(int floor) {

    }

    public void doorsClosed(int floor) {
    	
    }
}
