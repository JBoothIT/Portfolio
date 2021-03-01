

public interface SimulatedDrone {
    public void moveForward(int front);

    public void moveBackward(int back);

    public void moveLeft(int left);

    public void moveRight(int right);

    public void turnCW(int degrees);

    public void turnCCW(int degrees);

    public void wait(int seconds);

    public void gotoXYZ(int x, int y, int z);
}
