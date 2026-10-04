import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import javax.imageio.ImageIO;

public class Client {
    private static boolean shareScreen = false;

    public static void main(String[] args) {
        String host = "127.0.0.1";

        new Thread(() -> startChatClient(host, 65432)).start();
        new Thread(() -> startScreenClient(host, 65433)).start();
    }

    private static void startChatClient(String host, int port) {
        try {
            Socket socket = new Socket(host, port);
            System.out.println("[Chat] Connected to Server!");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

            new Thread(() -> {
                try {
                    String msg;
                    while ((msg = in.readLine()) != null) {
                        System.out.println("\n[Server]: " + msg);
                        System.out.print("[Client]: ");
                    }
                } catch (IOException e) {
                    System.out.println("\n[Chat Disconnected]");
                }
            }).start();

            System.out.println("\n--- Commands ---\nType 'share' -> Start Screen Sharing\nType 'stop'  -> Stop Screen Sharing\nOr type any text to chat\n");

            while (true) {
                System.out.print("[Client]: ");
                String input = console.readLine();
                if (input == null) break;

                if (input.equalsIgnoreCase("share")) {
                    shareScreen = true;
                    System.out.println("[System]: Screen Sharing Started!");
                } else if (input.equalsIgnoreCase("stop")) {
                    shareScreen = false;
                    System.out.println("[System]: Screen Sharing Stopped.");
                } else {
                    out.println(input);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void startScreenClient(String host, int port) {
        try {
            Socket socket = null;
            while (socket == null) {
                try {
                    socket = new Socket(host, port);
                } catch (Exception e) {
                    Thread.sleep(500);
                }
            }

            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            Robot robot = new Robot();
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

            while (true) {
                if (shareScreen) {
                    BufferedImage capture = robot.createScreenCapture(screenRect);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(capture, "jpg", baos);
                    byte[] bytes = baos.toByteArray();

                    dos.writeInt(bytes.length);
                    dos.write(bytes);
                    dos.flush();
                }
                Thread.sleep(100);
            }
        } catch (Exception e) {
            System.out.println("[Screen Sharing Error]");
        }
    }
}