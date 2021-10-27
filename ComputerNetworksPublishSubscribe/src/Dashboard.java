import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class Dashboard {


			 /* This method converts a string into a byte array, attaches the byte array as
			 * payload to a datagram packet and sends the packet to a given hostname and
			 * port number; currently, the name is fixed to the local host and to port
			 * number is fixed to 12345.
			 *
			 * @param args
			 */
	
			public static void main(String[] args) {
				final int DEST_PORT = 12345;
				final int RECV_PORT = 12346;
				final int MTU = 1500;

				DatagramPacket packet;
				DatagramSocket socket;
				InetAddress address;
				int port;

				ObjectOutputStream ostream;
				ByteArrayOutputStream bstream;
				ObjectInputStream ostream1;
				ByteArrayInputStream bstream1;
				byte[] buffer;

				try {
					System.out.println("Dashboard is trying to subscribe to broker...");

					// extract destination from arguments
					address= InetAddress.getLocalHost();   // InetAddress.getByName(args[0]);
					port= DEST_PORT;                       // Integer.parseInt(args[1]);

					// convert string "Hello World" to byte array
					bstream= new ByteArrayOutputStream();
					ostream= new ObjectOutputStream(bstream);
					ostream.writeUTF("Dashboard subscription received");
					ostream.flush();
					buffer= bstream.toByteArray();

					// create packet addressed to destination
					packet= new DatagramPacket(buffer, buffer.length,
							address, port);

					// create socket and send packet
					socket= new DatagramSocket();
					socket.send(packet);
				}
				catch(Exception e) {
					e.printStackTrace();
				}
					try {

						// extract destination from arguments
						address= InetAddress.getLocalHost(); // InetAddress.getByName(args[0]);
						port= RECV_PORT;                     // Integer.parseInt(args[1]);

						// create buffer for data, packet and socket
						buffer= new byte[MTU];
						packet= new DatagramPacket(buffer, buffer.length);
						socket= new DatagramSocket(port, address);

						// attempt to receive packet
						
						socket.receive(packet);

						// extract data from packet
						buffer= packet.getData();
						bstream1= new ByteArrayInputStream(buffer);
						ostream1= new ObjectInputStream(bstream1);

						// print data and end of program
						System.out.println("Dashboard: " + ostream1.readUTF());
						System.out.println("Program end");
					}
					catch(Exception e) {
						e.printStackTrace();
					}

				}
				
				
}

