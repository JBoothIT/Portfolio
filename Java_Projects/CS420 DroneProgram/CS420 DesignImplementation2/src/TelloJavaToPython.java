


import java.io.*;
import java.util.Scanner;

/***
 * Class for communnicating between java and python with the intent of controlling a DJI Tello Drone
 * All methods are public and static
 * 9/30/2019 v1.0
 * @author MasterControlProgram: Seth Lewis
 *
 */
public class TelloJavaToPython {

    private static BufferedReader inp;
    private static BufferedWriter out;
    private static Process p;
    private static final String pathToPython = Constants.PYTHON_PATH + " ";
    private static final String filePath = Constants.FILE_PATH_DEVELOPER;

    /***
     * Helper method for simplification of print statements
     * @param s string to be printed
     */
    public static void print(String s) {
        System.out.println(s);
    }

    /***
     * This method pipes strings to the Tello's Python control program
     * Strings are sent to the script and stripped of extra characters then sent to the drone
     * @param msg String command message to be sent to Tello
     * @return String representation of the Tello response message
     */
    public static String pipe(String msg) {
        String ret;

        try {
            out.write(msg + "\n");
            out.flush();
            ret = inp.readLine();
            while (ret.equals("") || ret.equals("forced stop")) {
                ret = inp.readLine();
            }
            return ret;
        }
        catch (Exception err) {
            err.printStackTrace();
        }
        return "error";
    }

    /***
     * A method that takes in an array of Tello commands and then passes them on to the drone via the single command method
     * Only used by the sample programs below, not efficient or advised for flight routines beyond testing
     * @param cmds String array of Tello command instructions
     */
    public static void commandDrone(String[] cmds) {

        for (int i = 0; i < cmds.length; i++) {
            commandDrone(cmds[i]);
        }
    }

    /***
     * A method that sends the Tello command strings on to the piping method and prints the call and response to verify correctness
     * @param cmd String Tello command instruction
     * @return String reponse of Tell can be used in conditionals for better flight control and informational messages
     */
    public static String commandDrone(String cmd) {

        String call = "";

        String response = "";

        call = cmd;
        print(call);
        response = pipe(call);
        print(response);
        return response;
    }

    /***
     * Method that contains all the necessary elements to set the drone to recieve additional commands
     */
    public static void launchInitialization() {

        String cmd = pathToPython + filePath;

        try {

            print(cmd);
            print(System.getProperty("user.dir"));
            print("Tello Demo" + "\n");

            print("Tello: command takeoff land flip forward back left right" + "\n" + "       up down cw ccw speed speed?" + "\n");

            print("end -- quit demo" + "\n");

            p = Runtime.getRuntime().exec(cmd);

            inp = new BufferedReader( new InputStreamReader(p.getInputStream(), "UTF-8"));
            out = new BufferedWriter( new OutputStreamWriter(p.getOutputStream()));

            commandDrone("command");
        }


        catch (Exception err) {
            err.printStackTrace();
        }
    }

    /***
     * Method that cleans up any remaing assets and terminates a flight sequence
     */
    public static void launchCompletion() {

        try {

            commandDrone("end");

            p.destroy();
            print('\n' + "End Program");
            inp.close();
            out.close();
        }

        catch (Exception err) {
            err.printStackTrace();
        }
    }

    /***
     * This is a demonstration program designed to show a subset of possible commands
     * along with the required intial and ending commands required by the Tello SDK
     * and the required ordering of the "takeoff" and "land" messages
     * @param cmds
     */
    public static void runProgramArray(String[] cmds) {

        String cmd = pathToPython + filePath;

        try {

            print(cmd);
            print(System.getProperty("user.dir"));
            print("Tello Demo" + "\n");

            print("Tello: command takeoff land flip forward back left right" + "\n" + "       up down cw ccw speed speed?" + "\n");

            print("end -- quit demo" + "\n");

            p = Runtime.getRuntime().exec(cmd);

            inp = new BufferedReader( new InputStreamReader(p.getInputStream(), "UTF-8"));
            out = new BufferedWriter( new OutputStreamWriter(p.getOutputStream()));

            commandDrone(cmds);

            p.destroy();
            print('\n' + "End Program");
            inp.close();
            out.close();
        }

        catch (Exception err) {
            err.printStackTrace();
        }
    }

    /***
     * This is a demonstration program that is almost identical in function to the
     * original python script execution but instead using a scanner to pipe strings to the Tello SDK
     * Once running you must enter "command" in the console before you can access the other
     * functions of the Tello SDK
     */
    public static void runProgramScanner() {

        String cmd = pathToPython + filePath;

        try {

            print(cmd);
            print(System.getProperty("user.dir"));
            print("Tello Demo" + "\n");

            print("Tello: command takeoff land flip forward back left right" + "\n" + "       up down cw ccw speed speed?" + "\n");

            print("end -- quit demo" + "\n");

            Process p = Runtime.getRuntime().exec(cmd);

            inp = new BufferedReader( new InputStreamReader(p.getInputStream(), "UTF-8"));
            out = new BufferedWriter( new OutputStreamWriter(p.getOutputStream()) );
            Scanner scan = new Scanner(System.in);

            String command = scan.nextLine();

            while(!command.equals("end")) {
                commandDrone(command);
                command = scan.nextLine();
            }

            commandDrone(command);

            p.destroy();
            print('\n' + "End Program");
            inp.close();
            out.close();
            scan.close();
        }

        catch (Exception err) {
            err.printStackTrace();
        }
    }

    /***
     * Main contains various test calls to verify proper connection to drone, execution of piping, and drone control
     * @param args not used
     */
    public static void main(String[] args) {

        String[] cmds = {"command", "battery?", "takeoff", "ccw 360", "flip b", "land", "end"};

        //runProgramArray(cmds);
        runProgramScanner();

        //launchInitialization(); // step 1 (required)
        //commandDrone("battery?"); // step 2
        //commandDrone("takeoff"); // step 3
        //commandDrone("land"); // step 4 (if you did step 2 and you don't do this...)
        //launchCompletion(); //step 5 (required)
    }
}