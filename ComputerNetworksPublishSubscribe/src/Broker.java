import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Broker{

	/**
	 * This method converts a string into a byte array, attaches the byte array as
	 * payload to a datagram packet and sends the packet to a given hostname and
	 * port number; currently, the name is fixed to the local host and to port
	 * number is fixed to 12345.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		final int RECV_PORT = 12345;
		final int MTU = 1500;

		DatagramPacket packet;
		DatagramSocket socket;
		InetAddress address;
		int port;

		ObjectInputStream ostream;
		ByteArrayInputStream bstream;
		byte[] buffer;


		try {
			System.out.println("Start broker");

			// extract destination from arguments
			address= InetAddress.getLocalHost(); // InetAddress.getByName(args[0]);
			port= RECV_PORT;                     // Integer.parseInt(args[1]);
			socket= new DatagramSocket(port, address);

			// create buffer for data, packet and socket
			for(int i=0; i < 2; i++) {
				buffer= new byte[MTU];
				packet= new DatagramPacket(buffer, buffer.length);


				// attempt to receive packet
				if(i == 0) {
				System.out.println("Trying to receive subscription from dashboard...");
				}
				else System.out.println("Trying to receive temperature data from sensors...");
				socket.receive(packet);


				// extract data from packet
				buffer= packet.getData();
				bstream= new ByteArrayInputStream(buffer);
				ostream= new ObjectInputStream(bstream);


				// print data and end of program
				System.out.println("" + ostream.readUTF());

			}
			
			final int DEST_PORT = 12346;

			DatagramPacket packet1;
			DatagramSocket socket1;
			InetAddress address1;
			int port1;

			ObjectOutputStream ostream1;
			ByteArrayOutputStream bstream1;
		

			try {
				System.out.println("Broker is trying to publish data to the dashboard...");

				// extract destination from arguments
				address1= InetAddress.getLocalHost();   // InetAddress.getByName(args[0]);
				port1= DEST_PORT;                       // Integer.parseInt(args[1]);

				// convert string "Hello World" to byte array
				bstream1= new ByteArrayOutputStream();
				ostream1= new ObjectOutputStream(bstream1);
				ostream1.writeUTF("Temperature data successfully received from the broker and published to the dashboard!");
				ostream1.flush();
				buffer= bstream1.toByteArray();

				// create packet addressed to destination
				packet1= new DatagramPacket(buffer, buffer.length,
						address1, port1);

				// create socket and send packet
				socket1= new DatagramSocket();
				socket1.send(packet1);

				
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	}
	

	

	