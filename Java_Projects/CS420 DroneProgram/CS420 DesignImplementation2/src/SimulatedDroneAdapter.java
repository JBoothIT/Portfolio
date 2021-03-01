

public class SimulatedDroneAdapter implements PhysicalDrone{
    private SimulatedDroneAnimation animator;

    public SimulatedDroneAdapter(SimulatedDroneAnimation animator){
        this.animator = animator;
    }

    //leave blank
    public void beginProgram() {

    }

    //leave blank
    public void endProgram() {

    }

    public void takeoff() {

    }

    public void land() {

    }

    //leave blank
    public void streamOn() {

    }

    //leave blank
    public void streamOff() {

    }

    //leave blank
    public void missionPadOn() {

    }

    //leave blank
    public void missionPadOff() {

    }

    //leave blank
    public void missionPadDirection(int param) {

    }

    //leave blank
    public void flyUpward(int up) {

    }

    //leave blank
    public void flyDown(int down) {

    }

    public void flyForward(int front) {
        animator.moveForward(front);
    }

    public void flyBackward(int back) {
        animator.moveBackward(back);
    }

    public void flyLeft(int left) {
        animator.moveLeft(left);
    }

    public void flyRight(int right) {
        animator.moveRight(right);
    }

    public void gotoXYZ(int x, int y, int z, int speed) {
        animator.gotoXYZ(x, y, z);
    }

    public void gotoMissionPadXYZ(int x, int y, int z, int speed, String ID) {

    }

    //leave plank
    public void flyCurve(int x1, int y1, int z1, int x2, int y2, int z2, int speed) {

    }

    //leave blank
    public void flyCurveMissionPad(int x1, int y1, int z1, int x2, int y2, int z2, int speed, String ID) {

    }

    public void turnCW(int degrees) {
        animator.turnCW(degrees);
    }

    public void turnCCW(int degrees) {
        animator.turnCCW(degrees);
    }

    public void flip(String direction) {
        animator.turnCW(180);
    }

    //leave blank
    public void jumpMissionPad(int x, int y, int z, int speed, int yaw, String ID1, String ID2) {

    }

    public void hoverInPlace(int seconds) throws InterruptedException {
        animator.wait(seconds);
    }

    //leave blank
    public void stopInPlace() {

    }

    // leave blank
    public void setSpeed(int speed) {

    }

    //leave blank
    public double getSpeed() {
        return 0;
    }

    //leave blank
    public int getBattery() {
        return 0;
    }

    //leave blank
    public int getFlightTime() {
        return 0;
    }

    //leave blank
    public int getHeight() {
        return 0;
    }

    //leave blank
    public int getTemp() {
        return 0;
    }

    //leave blank
    public int getAttitudePitch() {
        return 0;
    }

    //leave blank
    public int getAttitudeRoll() {
        return 0;
    }

    //leave blank
    public int getAttitudeYaw() {
        return 0;
    }

    //leave blank
    public double getBarometer() {
        return 0;
    }

    //leave blank
    public double getAccelerationX() {
        return 0;
    }

    //leave blank
    public double getAccelerationY() {
        return 0;
    }

    //leave blank
    public double getAccelerationZ() {
        return 0;
    }

    //leave blank
    public int getTOF() {
        return 0;
    }

    //leave blank
    public String getWIFI() {
        return null;
    }

    //leave blank
    public String getVersionSDK() {
        return null;
    }

    //leave blank
    public String getSerialNumber() {
        return null;
    }
}
