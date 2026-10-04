# Java Client-Server Socket Communication

A Java-based client-server communication project built using **Java Socket Programming**. This project demonstrates real-time communication between a client and a server using TCP sockets, including text chat and live screen sharing.

## 🚀 Project Overview

This project demonstrates how a **Java Client and Server communicate through TCP socket connections**.

The application uses two separate socket connections:

- One connection for **real-time text communication**
- One connection for **live screen sharing**

The client can send messages to the server, receive messages from the server, and share its screen with the server in real time.

The project can be tested on the same computer and can also be configured to work between two computers connected to the same **Local Area Network (LAN)** without requiring internet access.

## ✨ Features

- Client-Server communication using Java TCP sockets
- Real-time two-way text communication
- Live client screen sharing
- Separate socket connections for chat and screen sharing
- Multithreaded communication
- Screen capture using Java AWT Robot
- JPEG image transmission over sockets
- Live screen display using Java Swing
- Command-line based operation
- Can be configured for communication over a local network

## 🛠️ Technologies Used

- Java
- Java Socket Programming
- TCP/IP
- Multithreading
- Java AWT Robot
- Java Swing
- ImageIO
- DataInputStream / DataOutputStream

## 📁 Project Files

```text
java-client-server-socket/
│
├── Client.java
├── Server.java
├── client_screen.png
├── desktop.png
└── README.md
🔌 How the System Works

The application creates two separate TCP connections.

1. Text Chat

The chat connection uses port:

65432

The Client and Server use this connection to send and receive text messages in real time.

2. Screen Sharing

The screen-sharing connection uses port:

65433

When the client enters the command:

share

the client starts capturing its screen using Java's Robot class.

The captured screen is converted into JPEG data and transmitted to the server through the socket connection.

The server receives the image data and displays the client's screen in a Java Swing window.

When the client enters:

stop

screen sharing stops.

🎮 Client Commands
Command	Function
share	Starts live screen sharing
stop	Stops screen sharing
Any other text	Sends a chat message
💻 How to Run
Step 1 — Compile the Project

Open a terminal in the project folder and run:

javac Server.java
javac Client.java
Step 2 — Start the Server

Run:

java Server

The server starts two services:

Chat Server        → Port 65432
Screen Server      → Port 65433
Step 3 — Start the Client

Open another terminal and run:

java Client

The client connects to both server services.

After connection, text messages can be exchanged between the Client and Server.

🌐 Running on Two Computers

The project can also be configured to work between two computers connected to the same Local Area Network (LAN).

By default, the Client uses:

String host = "127.0.0.1";

127.0.0.1 refers to the same computer on which the Client is running.

To connect the Client to a Server running on another computer, replace the host address with the Server computer's local IP address.

For example:

String host = "192.168.1.10";

Both computers should be connected to the same local network, and the required ports should be allowed through the firewall.

🖥️ Architecture
             TCP Socket Communication
                    │
          ┌─────────┴─────────┐
          │                   │
      Chat Socket       Screen Socket
       Port 65432         Port 65433
          │                   │
          ▼                   ▼
      ┌────────┐         ┌────────┐
      │ Client │         │ Client │
      └────┬───┘         └────┬───┘
           │                  │
           │ Text Messages    │ Screen Frames
           ▼                  ▼
      ┌────────┐         ┌────────┐
      │ Server │         │ Server │
      └────────┘         └────────┘
🎯 Learning Objectives

This project was developed to practice and understand:

Client-Server Architecture
TCP Socket Programming
Network Communication
Java Multithreading
Data Transmission
Screen Capture
Image Transmission
Local Area Network Communication
Java Swing GUI
Network Programming Concepts
📸 Screenshots

The repository includes screenshots demonstrating the project and its screen-sharing functionality.

🔐 Security Note

This project is created for educational and networking practice purposes.

The current implementation does not include authentication or encryption.

For a production-ready application, additional security mechanisms such as authentication, authorization, encryption, and secure communication protocols would be required.

👨‍💻 Author

Usman Durrani

Cyber Security Student

⭐ If you find this project useful, feel free to explore the code and learn from it.
