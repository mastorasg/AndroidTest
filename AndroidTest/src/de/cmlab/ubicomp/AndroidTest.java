package de.cmlab.ubicomp;

import java.util.Enumeration;
import java.util.Observable;
import java.util.Observer;
//import java.awt.Toolkit;
//import java.awt.event.KeyEvent;
import java.net.*;


import de.cmlab.ubicomp.lib.SensorUDPReceiver;
import de.cmlab.ubicomp.lib.model.AndroidSensor;

/**
 * It is testing the SensorUDPReceiver
 * ONLY for orientation sensor, button[0] and button[1]
 * @author Mastoras Georgios, Name2, Name3, Name4
 */
public class AndroidTest {

    /**
     * This is the main method
     * to show the server Address and port
     * and initiate the SensorUDPReceiver
     * @param args not used
     */
    public static void main(String[] args) {
        int port = 5555;

        /*
         *  In order to avoid mismatch of ip setting on Android device
         *  inform the user about the current server settings
         *  Show the ipv4 and port reserved to listen for messages
         */
        String ip;
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                // filters out 127.0.0.1 and inactive interfaces like Virtual Machines
                if (iface.isLoopback() || !iface.isUp() || iface.getDisplayName().contains("VMware") || iface.getDisplayName().contains("Virtual"))
                    continue;

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    ip = addr.getHostAddress();
                    if (!ip.contains(":")) { // if not ipv6
                        System.out.println("On ip: " + ip + " port " + port);
                    }
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }

	    /*initiate a receiver by defining a port 
		number that will be sent to the receiver from the app*/
        SensorUDPReceiver receiver = new SensorUDPReceiver(port);
		/*create a listener as shown below and let it implement Observer to
		to get the app updates*/
        SensorUDPListener listener = new SensorUDPListener();
        receiver.addObserver(listener);

    }

    /**
     * Constructs and activates the SensorUDPReceiver
     */
    public static class SensorUDPListener implements Observer {

        float[] orientation = new float[3]; // only the orientation value is inspecetd
        int watch = 0; // the number of characters in Actuator bar

        /**
         * Auto-generated constructor (empty)
         */
        SensorUDPListener() {
            // TODO Auto-generated constructor stub
        }

        /**
         * update method is called automatically
         * on any message from android device
         * @param o the Server object
         * @param arg the Sensor object (Android device values)
         */
        @Override
        public void update(Observable o, Object arg) {
            /*check SensorUDPReceiver Java Docs for the AndroidSensor API*/
            AndroidSensor sensorValues = (AndroidSensor) arg;
            orientation = sensorValues.getOrientation();
            if (sensorValues.getTouchedButtons()[1] && watch < 100) watch++;
            if (sensorValues.getTouchedButtons()[0] && watch > 0) watch--;
            String out = String.format("Orientation: X:%f,Y:%f,Z:%f", orientation[0], orientation[1], orientation[2]);
            System.out.println(out);
            for (int i = 0; i < watch; i++) {
                System.out.print(">");
            }
            System.out.println();
            System.out.println("============================");
        }

    }
}
