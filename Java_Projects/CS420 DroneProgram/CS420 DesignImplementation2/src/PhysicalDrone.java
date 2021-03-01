



/***
 * Interface containing a selection of possible drone flight commands mostly independent of drone manufacturer
 * 09/30/2019 v1.0
 * @author MasterControlProgram
 *
 */
public interface PhysicalDrone {

    /***
     * This is a required method for every flight routine
     * Starts the drone process and readies it to recieve additional SDK instructions
     * MUST BE INCLUDED AT THE BEGINNING OF EVERY FLIGHT ROUTINE
     */
    public void beginProgram();

    /***
     * This is a required method for every flight routine
     * Terminates external processes and cleans up any internal objects
     * MUST BE INCLUDED AT THE END OF EVERY FLIGHT ROUTINE
     */
    public void endProgram();

    /***
     * Sends the "takeoff" signal to an active drone
     * Trying to fly in low light or high wind may cause errors
     * Trying to take off with a low battery may fail
     */
    public void takeoff();

    /***
     * Sends the "land" signal to an active drone
     * Drone may land in place despite any obstacles below it
     * Make sure your landing area is clear
     */
    public void land();

    /***
     * Activates the drone's onboard video stream
     * may not function without additional outside processes
     */
    public void streamOn();

    /***
     * Deactivates a running video stream
     */
    public void streamOff();

    /***
     * Turns on the mission pad SDK functionality of a drone
     * Must be enabled before other mission pad based methods will function
     */
    public void missionPadOn();

    /***
     * Turns off the mission pad SDK functionality of a drone
     */
    public void missionPadOff();

    /***
     * Sets whether the drone looks for mission pads in specific directions determined by the SDK
     * @param param integer value starting with 0 up to the total directional combinations allowed by the SDK
     */
    public void missionPadDirection(int param);

    /***
     * Requests drone increase altitude
     * System of measurement varies by manufacturer
     * @param up integer value starting with SDK lower bound up to upper bound of possible distance travelled
     */
    public void flyUpward(int up);

    /***
     * Requests drone decrease altitude
     * System of measurement varies by manufacturer
     * @param down integer value starting with SDK lower bound up to upper bound of possible distance travelled
     */
    public void flyDown(int down);

    /***
     * Requests drone fly in forward heading relative to airframe
     * System of measurement varies by manufacturer
     * @param front integer value starting with SDK lower bound up to upper bound of possible distance travelled
     */
    public void flyForward(int front);

    /***
     * Requests drone fly in reverse heading relative to airframe
     * System of measurement varies by manufacturer
     * @param back integer value starting with SDK lower bound up to upper bound of possible distance travelled
     */
    public void flyBackward(int back);

    /***
     * Requests drone fly left heading relative to airframe
     * System of measurement varies by manufacturer
     * @param left integer value starting with SDK lower bound up to upper bound of possible distance travelled
     */
    public void flyLeft(int left);

    /***
     * Requests drone fly right heading relative to airframe
     * System of measurement varies by manufacturer
     * @param right integer value starting with SDK lower bound up to upper bound of possible distance travelled
     */
    public void flyRight(int right);

    /***
     * Requests drone fly to point in three dimensions relative to current position as origin
     * @param x integer value of point along the x axis with lower bound up and upper bound of possible distance set by SDK
     * @param y integer value of point along the y axis with lower bound up and upper bound of possible distance set by SDK
     * @param z integer value of point along the z axis with lower bound up and upper bound of possible distance set by SDK
     * @param speed integer value setting some distance travelled per some unit time determined by SDK
     */
    public void gotoXYZ(int x, int y, int z, int speed);

    /***
     * Requests drone fly to point in three dimensions and search for symbol recognition system pattern with specific ID at location
     * @param x integer value of point along the x axis with lower bound up and upper bound of possible distance set by SDK
     * @param y integer value of point along the y axis with lower bound up and upper bound of possible distance set by SDK
     * @param z integer value of point along the z axis with lower bound up and upper bound of possible distance set by SDK
     * @param speed integer value setting some distance travelled per some unit time determined by SDK
     * @param ID string identification number of a particular symbol recognition system pattern
     */
    public void gotoMissionPadXYZ(int x, int y, int z, int speed, String ID);

    /***
     * Requests drone fly along a curve in three dimensions defined by two points
     * Starts with current location as origin flying through first point and ending at second point
     * @param x1 integer value of mid point along the x axis with lower bound up and upper bound of possible distance set by SDK
     * @param y1 integer value of mid point along the y axis with lower bound up and upper bound of possible distance set by SDK
     * @param z1 integer value of mid point along the z axis with lower bound up and upper bound of possible distance set by SDK
     * @param x2 integer value of end point along the x axis with lower bound up and upper bound of possible distance set by SDK
     * @param y2 integer value of end point along the y axis with lower bound up and upper bound of possible distance set by SDK
     * @param z2 integer value of end point along the z axis with lower bound up and upper bound of possible distance set by SDK
     * @param speed integer value setting some distance travelled per some unit time determined by SDK
     */
    public void flyCurve(int x1, int y1, int z1, int x2, int y2, int z2, int speed);

    /***
     * Requests drone fly along a curve in three dimensions defined by two points and then search for symbol recognition system pattern with specific ID at location
     * Starts with current location as origin flying through first point and ending at second point
     * @param x1 integer value of mid point along the x axis with lower bound up and upper bound of possible distance set by SDK
     * @param y1 integer value of mid point along the y axis with lower bound up and upper bound of possible distance set by SDK
     * @param z1 integer value of mid point along the z axis with lower bound up and upper bound of possible distance set by SDK
     * @param x2 integer value of end point along the x axis with lower bound up and upper bound of possible distance set by SDK
     * @param y2 integer value of end point along the y axis with lower bound up and upper bound of possible distance set by SDK
     * @param z2 integer value of end point along the z axis with lower bound up and upper bound of possible distance set by SDK
     * @param speed integer value setting some distance travelled per some unit time determined by SDK
     * @param ID string identification number of a particular symbol recognition system pattern
     */
    public void flyCurveMissionPad(int x1, int y1, int z1, int x2, int y2, int z2, int speed, String ID);

    /***
     * Requests drone to rotate around it's central axis in a clockwise direction by a certain number of degrees relative to an above viewpoint
     * @param degrees integer value of amount to rotate in degrees
     */
    public void turnCW(int degrees);

    /***
     * Requests drone to rotate around it's central axis in a counter clockwise direction by a certain number of degrees relative to an above viewpoint
     * @param degrees integer value of amount to rotate in degrees
     */
    public void turnCCW(int degrees);

    /***
     * Requests the drone perform a 360 degree flip in one of the four headings relative to the airframe
     * @param direction string dependent on SDK represents forward, backward, left, or right
     */
    public void flip(String direction);

    /***
     * NOT IN USE
     * @param x
     * @param y
     * @param z
     * @param speed
     * @param yaw
     * @param ID1
     * @param ID2
     */
    public void jumpMissionPad(int x, int y, int z, int speed, int yaw, String ID1, String ID2);

    /***
     * Requests the drone hover at current altitude for a set number of seconds
     * depending on the safety features of the drone steps must be taken to prevent emergency landings and
     * communication loss
     * @param seconds number of seconds to hover in place
     * @throws InterruptedException uses timer classes and may be interruptable
     */
    public void hoverInPlace(int seconds) throws InterruptedException;

    /***
     * Requests the drone stope in place, considered a placeholder command when not coupled with a timer
     */
    public void stopInPlace();

    /***
     * Sets drone speed for all motion requests that do not include speed as a variable: Untested
     * @param speed integer value setting some distance travelled per some unit time determined by SDK
     */
    public void setSpeed(int speed);

    /***
     * Gets the drone's current speed setting
     * @return double measurement of airspeed based on SDK units
     */
    public double getSpeed();

    /***
     * Gets the drone's currently remaining battery life as a percentage
     * @return integer representation of percentage
     */
    public int getBattery();

    /***
     * Gets the current amount of time since the drone was issued the first flight command
     * @return integer value of time based on SDK units
     */
    public int getFlightTime();

    /***
     * Gets the current altitude of the drone
     * @return integer value of altitude based on SDK units
     */
    public int getHeight();

    /***
     * Gets the drone's current internal temperature
     * @return integer value of internal temp based on SDK units
     */
    public int getTemp();

    /***
     * Gets dorne's IMU attitude data and returns pitch
     * @return integer value of pitch in degrees
     */
    public int getAttitudePitch();

    /***
     * Gets dorne's IMU attitude data and returns roll
     * @return integer value of roll in degrees
     */
    public int getAttitudeRoll();

    /***
     * Gets dorne's IMU attitude data and returns yaw
     * @return integer value of yaw in degrees
     */
    public int getAttitudeYaw();

    /***
     * Gets drone's onboard barometric pressure reading at current altitude
     * @return double precision value of pressure reading based on SDK units
     */
    public double getBarometer();

    /***
     * Gets drone's IMU angular acceleration data and returns the X component
     * @return double precision acceleration value based on SDK units
     */
    public double getAccelerationX();

    /***
     * Gets drone's IMU angular acceleration data and returns the Y component
     * @return double precision acceleration value based on SDK units
     */
    public double getAccelerationY();

    /***
     * Gets drone's IMU angular acceleration data and returns the Z component
     * @return double precision acceleration value based on SDK units
     */
    public double getAccelerationZ();

    /***
     * Gets drone's current time of flight distance from initial location
     * @return integer value of distance based on SDK units
     */
    public int getTOF();

    /***
     * Request drone's unique SSID
     * @return string representation of SSID
     */
    public String getWIFI();

    /***
     * Request drone's current firmware version
     * @return string representation of firmware version number
     */
    public String getVersionSDK();

    /***
     * Requests drone's manufacturer serial ID
     * @return string representation of serial ID
     */
    public String getSerialNumber();

}
