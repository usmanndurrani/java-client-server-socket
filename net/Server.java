import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Server {
    private static JFrame frame;
    private static JLabel label;

    public static void main(String[] args) {
        new Thread(() -> startChatServer(65432)).start();
        new Thread(() -> startScreenServer(65433)).start();
    }

    private static void startChatServer(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("[Chat Server] Listening on port " + port + "...");
            Socket socket = serverSocket.accept();
            System.out.println("[Chat Server] Client Connected!");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

            new Thread(() -> {
                try {
                    String msg;
                    while ((msg = in.readLine()) != null) {
                        System.out.println("\n[Client]: " + msg);
                        System.out.print("[Server]: ");
                    }
                } catch (IOException e) {
                    System.out.println("\n[Chat Disconnected]");
                }
            }).start();

            while (true) {
                System.out.print("[Server]: ");
                String sendMsg = console.readLine();
                if (sendMsg != null) out.println(sendMsg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void startScreenServer(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("[Screen Server] Listening on port " + port + "...");
            Socket socket = serverSocket.accept();

            DataInputStream dis = new DataInputStream(socket.getInputStream());

            while (true) {
                int length = dis.readInt();
                if (length > 0) {
                    byte[] imageBytes = new byte[length];
                    dis.readFully(imageBytes, 0, length);

                    ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
                    BufferedImage image = ImageIO.read(bais);

                    if (image != null) {
                        SwingUtilities.invokeLater(() -> displayScreen(image));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("[Screen Sharing Disconnected]");
        }
    }

    private static void displayScreen(BufferedImage image) {
        if (frame == null) {
            frame = new JFrame("Live Client Desktop Share");
            label = new JLabel();
            frame.add(new JScrollPane(label));
            frame.setSize(900, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        }
        label.setIcon(new ImageIcon(image));
        frame.repaint();
    }
}