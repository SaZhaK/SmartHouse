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

@Component
public class USBConnector {
    private static final String PORT_NUMBER = "/dev/ttyUSB0";
    private static SerialPort serialPort;

    private static boolean send = false;
    public static Deque<String> data = new ArrayDeque<>();

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

    public static void startSending(WebSocketSession session) {
        USBConnector.send = true;
        Thread sendThread = new Thread(() -> {
            while (send) {
                if (!data.isEmpty()) {
                    System.out.println(data.toString());

                    String dataToSend = data.removeFirst();
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

    public static void stopSending() {
        USBConnector.send = false;
    }

    private static class PortReader implements SerialPortEventListener {
        public void serialEvent(SerialPortEvent event) {
            if (event.isRXCHAR() && event.getEventValue() > 0) {
                try {
                    String receivedData = serialPort.readString(event.getEventValue());
                    data.addLast(receivedData);
                } catch (SerialPortException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}