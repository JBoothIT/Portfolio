



import java.util.concurrent.TimeUnit;

public class PhysicalDroneTello implements PhysicalDrone {

    public PhysicalDroneTello() {

    }

    public void beginProgram() {
        TelloJavaToPython.launchInitialization();
    }

    public void endProgram() {
        TelloJavaToPython.launchCompletion();
    }

    public void takeoff() {
        TelloJavaToPython.commandDrone("takeoff");
    }

    public void land() {
        TelloJavaToPython.commandDrone("land");
    }

    public void streamOn() {
        TelloJavaToPython.commandDrone("streamon");
    }

    public void streamOff() {
        TelloJavaToPython.commandDrone("streamoff");
    }

    public void missionPadOn() {
        TelloJavaToPython.commandDrone("mon");
    }

    public void missionPadOff() {
        TelloJavaToPython.commandDrone("moff");
    }

    public void missionPadDirection(int param) {
        TelloJavaToPython.commandDrone("mdirection " + param);
    }

    public void flyUpward(int up) {
        if (up < 20 || up > 500) return;
        TelloJavaToPython.commandDrone("up " + up);
    }

    public void flyDown(int down) {
        if (down < 20 || down > 500) return;
        TelloJavaToPython.commandDrone("down " + down);
    }

    public void flyForward(int front) {
        if (front < 20 || front > 500) return;
        TelloJavaToPython.commandDrone("forward " + front);
    }

    public void flyBackward(int back) {
        if (back < 20 || back > 500) return;
        TelloJavaToPython.commandDrone("back " + back);
    }

    public void flyLeft(int left) {
        if (left < 20 || left > 500) return;
        TelloJavaToPython.commandDrone("left " + left);
    }

    public void flyRight(int right) {
        if (right < 20 || right > 500) return;
        TelloJavaToPython.commandDrone("right " + right);
    }

    public void gotoXYZ(int x, int y, int z, int speed) {
        if (x < -500 || x > 500 || y < -500 || y > 500 || z < -500 || z > 500
                || speed < 10 || speed > 100) return;
        y = -y; // compensating for screen coordinates
        String fstr = String.format("go %1$d %2$d %3$d %4$d", x, y, z, speed);
        TelloJavaToPython.commandDrone(fstr);
    }

    public void gotoMissionPadXYZ(int x, int y, int z, int speed, String ID) {
        if (x < -500 || x > 500 || y < -500 || y > 500 || z < -500 || z > 500
                || speed < 10 || speed > 100) return;
        y = -y; // compensating for screen coordinates
        String fstr = String.format("go %1$d %2$d %3$d %4$d %5$s", x, y, z, speed, ID);
        TelloJavaToPython.commandDrone(fstr);
    }

    public void flyCurve(int x1, int y1, int z1, int x2, int y2, int z2, int speed) {
        if (x1 < -500 || x1 > 500 || y1 < -500 || y1 > 500 || z1 < -500 || z1 > 500 ||
                x2 < -500 || x2 > 500 || y2 < -500 || y2 > 500 || z2 < -500 || z2 > 500
                || speed < 10 || speed > 60) return;
        String fstr = String.format("curve %1$d %2$d %3$d %4$d %5$d %6$d %7$d", x1, y1, z1, x2, y2, z2, speed);
        TelloJavaToPython.commandDrone(fstr);
    }

    public void flyCurveMissionPad(int x1, int y1, int z1, int x2, int y2, int z2, int speed, String ID) {
        if (x1 < -500 || x1 > 500 || y1 < -500 || y1 > 500 || z1 < -500 || z1 > 500 ||
                x2 < -500 || x2 > 500 || y2 < -500 || y2 > 500 || z2 < -500 || z2 > 500
                || speed < 10 || speed > 60) return;
        String fstr = String.format("curve %1$d %2$d %3$d %4$d %5$d %6$d %7$d %8$s", x1, y1, z1, x2, y2, z2, speed, ID);
        TelloJavaToPython.commandDrone(fstr);
    }

    public void turnCW(int degrees) {
        if (degrees < 1 || degrees > 360) return;
        TelloJavaToPython.commandDrone("cw " + degrees);
    }

    public void turnCCW(int degrees) {
        if (degrees < 1 || degrees > 360) return;
        TelloJavaToPython.commandDrone("ccw " + degrees);
    }

    public void flip(String direction) {
        TelloJavaToPython.commandDrone("flip " + direction);
    }

    public void jumpMissionPad(int x, int y, int z, int speed, int yaw, String ID1, String ID2) {
        // TODO Auto-generated method stub

    }

    public void hoverInPlace(int seconds) {
        int cycles = seconds/15;
        int remainder = seconds % 15;
        if (cycles == 0) {
            TelloJavaToPython.commandDrone("stop");
            try {
                TimeUnit.SECONDS.sleep(seconds);
            } catch (InterruptedException e) {
                return;
            }
        }
        else {
            for (int i = 0; i < cycles; i++) {
                TelloJavaToPython.commandDrone("stop");
                try {
                    TimeUnit.MILLISECONDS.sleep(14970); // less than exactly 15 sec to prevent failsafe landing
                } catch (InterruptedException e) {
                    return;
                }
            }
            if (remainder > 0) {
                TelloJavaToPython.commandDrone("stop");
                try {
                    TimeUnit.SECONDS.sleep(remainder);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }

    public void stopInPlace() {
        TelloJavaToPython.commandDrone("stop");
    }

    public void setSpeed(int speed) {
        if (speed < 10 || speed > 100) return;
        TelloJavaToPython.commandDrone("speed " + speed);
    }

    public double getSpeed() {
        String speed = TelloJavaToPython.commandDrone("speed?");
        return Double.parseDouble(speed);
    }

    public int getBattery() {
        String battery = TelloJavaToPython.commandDrone("battery?");
        return Integer.parseInt(battery);
    }

    public int getFlightTime() {
        String time = TelloJavaToPython.commandDrone("time?");
        String timeInt = time.substring(0, time.length() - 1);
        return Integer.parseInt(timeInt);
    }

    public int getHeight() {
        String height = TelloJavaToPython.commandDrone("height?");
        String heightInt = height.substring(0, height.length() - 2); // in decimeters
        int heightInCentimeters = 10 * Integer.parseInt(heightInt);
        return heightInCentimeters;
    }

    public int getTemp() {
        String temperature = TelloJavaToPython.commandDrone("temp?");
        String[] arrayOfStr = temperature.split("~|C", 2);
        int temp1 = Integer.parseInt(arrayOfStr[0]);
        int temp2 = Integer.parseInt(arrayOfStr[1].substring(0, arrayOfStr[1].length() - 1));
        return (temp1 + temp2)/2;
    }

    public int getAttitudePitch() {
        String attitude = TelloJavaToPython.commandDrone("attitude?");
        String[] arrayOfStr = attitude.split(":|;", 7);
        int pitch = Integer.parseInt(arrayOfStr[1]);
        return pitch;
    }

    public int getAttitudeRoll() {
        String attitude = TelloJavaToPython.commandDrone("attitude?");
        String[] arrayOfStr = attitude.split(":|;", 7);
        int roll = Integer.parseInt(arrayOfStr[3]);
        return roll;
    }

    public int getAttitudeYaw() {
        String attitude = TelloJavaToPython.commandDrone("attitude?");
        String[] arrayOfStr = attitude.split(":|;", 7);
        int pitch = Integer.parseInt(arrayOfStr[5]);
        return pitch;
    }

    public double getBarometer() {
        String barometer = TelloJavaToPython.commandDrone("baro?");
        return Double.parseDouble(barometer);
    }

    public double getAccelerationX() {
        String acceleration = TelloJavaToPython.commandDrone("acceleration?");
        String[] arrayOfStr = acceleration.split(":|;", 7);
        double x = Double.parseDouble(arrayOfStr[1]);
        return x;
    }

    public double getAccelerationY() {
        String acceleration = TelloJavaToPython.commandDrone("acceleration?");
        String[] arrayOfStr = acceleration.split(":|;", 7);
        double y = Double.parseDouble(arrayOfStr[3]);
        return y;
    }

    public double getAccelerationZ() {
        String acceleration = TelloJavaToPython.commandDrone("acceleration?");
        String[] arrayOfStr = acceleration.split(":|;", 7);
        double z = Double.parseDouble(arrayOfStr[5]);
        return z;
    }

    public int getTOF() {
        String timeOfFlight = TelloJavaToPython.commandDrone("height?");
        String timeOfFlightInt = timeOfFlight.substring(0, timeOfFlight.length() - 2);
        return Integer.parseInt(timeOfFlightInt);
    }

    public String getWIFI() {
        return TelloJavaToPython.commandDrone("wifi?");
    }

    public String getVersionSDK() {
        return TelloJavaToPython.commandDrone("sdk?");
    }

    public String getSerialNumber() {
        return TelloJavaToPython.commandDrone("sn?");
    }

}
