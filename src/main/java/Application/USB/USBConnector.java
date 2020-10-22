package Application.USB;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

@Component
public class USBConnector {
    private static final String PORT_NUMBER = "/dev/ttyUSB0";
    private static SerialPort serialPort;

    private static boolean sendButton = false;
    private static boolean sendJoyStick = false;
    public static Deque<String> buttonData = new ArrayDeque<>();
    public static Deque<String> joyStickData = new ArrayDeque<>();

    static {
        serialPort = new SerialPort(PORT_NUMBER);
        try {
            serialPort.openPort();
            serialPort.setParams(SerialPort.BAUDRATE_9600,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN |
                    SerialPort.FLOWCONTROL_RTSCTS_OUT);
            serialPort.addEventListener(new PortReader(), SerialPort.MASK_RXCHAR);
        } catch (SerialPortException ex) {
            ex.printStackTrace();
        }
    }

    public static void startSendingButtonData(WebSocketSession session) {
        USBConnector.sendButton = true;
        Thread sendThread = new Thread(() -> {
            while (sendButton) {
                if (!buttonData.isEmpty()) {
                    String dataToSend = buttonData.removeFirst();
                    try {
                        session.sendMessage(new TextMessage(dataToSend));
                        Thread.sleep(1000);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        sendThread.start();
    }

    public static void stopSendingButtonData() {
        USBConnector.sendButton = false;
    }

    public static void startSendingJoyStickData(WebSocketSession session) {
        USBConnector.sendJoyStick = true;
        Thread sendThread = new Thread(() -> {
            while (sendJoyStick) {
                if (!joyStickData.isEmpty()) {
                    String dataToSend = joyStickData.removeFirst();
                    try {
                        session.sendMessage(new TextMessage(dataToSend));
                        Thread.sleep(1000);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        sendThread.start();
    }

    public static void stopSendingJoyStickData() {
        USBConnector.sendJoyStick = false;
    }

    private static class PortReader implements SerialPortEventListener {
        public void serialEvent(SerialPortEvent event) {
            if (event.isRXCHAR() && event.getEventValue() > 0) {
                try {
                    String receivedData = serialPort.readString(event.getEventValue());
                    StringTokenizer tokenizer = new StringTokenizer(receivedData, " ", false);
                    String joyStickX = tokenizer.nextToken();
                    String joyStickY = tokenizer.nextToken();
                    String button = tokenizer.nextToken();

                    buttonData.addLast(button);
                    joyStickData.addLast(joyStickX + " " + joyStickY);
                } catch (SerialPortException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}