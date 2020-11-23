package Application.USB;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Queue;
import java.util.StringTokenizer;
import java.util.concurrent.ArrayBlockingQueue;

@Component
public class USBConnector {
    private static final String PORT_NUMBER = "/dev/ttyUSB0";
    private static SerialPort serialPort;

    private static boolean sendButton = false;
    private static boolean sendJoyStick = false;
    private static boolean sound = false;
    private static boolean fan = false;
    private static boolean red = false;
    private static boolean green = false;
    private static boolean blue = false;
    public static Queue<String> buttonData = new ArrayBlockingQueue(21);
    public static Queue<String> joyStickData = new ArrayBlockingQueue<>(21);

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

    public void startSendingButtonData(WebSocketSession session) {
        USBConnector.sendButton = true;
        Thread sendThread = new Thread(() -> {
            while (sendButton) {
                try {
                    if (!buttonData.isEmpty()) {
                        String dataToSend = buttonData.remove();
                        session.sendMessage(new TextMessage(dataToSend));
                        Thread.sleep(100);
                    } else {
                        Thread.sleep(400);
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        sendThread.start();
    }

    public void stopSendingButtonData() {
        USBConnector.sendButton = false;
    }

    public void startSendingJoyStickData(WebSocketSession session) {
        USBConnector.sendJoyStick = true;
        Thread sendThread = new Thread(() -> {
            while (sendJoyStick) {
                try {
                    if (!joyStickData.isEmpty()) {
                        String dataToSend = joyStickData.remove();
                        session.sendMessage(new TextMessage(dataToSend));
                        Thread.sleep(100);
                    } else {
                        Thread.sleep(400);
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        sendThread.start();
    }

    public void stopSendingJoyStickData() {
        USBConnector.sendJoyStick = false;
    }

    public void sound() {
        USBConnector.sound = !USBConnector.sound;
        try {
            if (USBConnector.sound) {
                serialPort.writeString("sound");
            } else {
                serialPort.writeString("stopSound");
            }
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    public void red() {
        USBConnector.red = !USBConnector.red;
        try {
            if (USBConnector.red) {
                serialPort.writeString("red");

            } else {
                serialPort.writeString("stopRed");
            }
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    public void green() {
        USBConnector.green = !USBConnector.green;
        try {
            if (USBConnector.green) {
                serialPort.writeString("green");

            } else {
                serialPort.writeString("stopGreen");
            }
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    public void blue() {
        USBConnector.blue = !USBConnector.blue;
        try {
            if (USBConnector.blue) {
                serialPort.writeString("blue");

            } else {
                serialPort.writeString("stopBlue");
            }
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    public void fan() {
        USBConnector.fan = !USBConnector.fan;
        try {
            if (USBConnector.fan) {
                serialPort.writeString("fan");
            } else {
                serialPort.writeString("stopFan");
            }
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    public void angle(String angle) {
        int roundedAngle = (int) Math.round(Double.parseDouble(angle));
        try {
            serialPort.writeString(String.valueOf(roundedAngle));
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
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

                    int code = 0;
                    int x_position = Integer.valueOf(joyStickX);
                    int y_position = Integer.valueOf(joyStickY);

                    if ((x_position > 190 && x_position < 280) && (y_position > 190 && y_position < 280)) {
                        code = 11;
                    } else if ((x_position > 0 && x_position < 30) && (y_position > 0 && y_position < 30)) {
                        code = 21;
                    } else if ((x_position > 30 && x_position < 190) && (y_position > 30 && y_position < 190)) {
                        code = 31;
                    } else if ((x_position > 330 && x_position < 400) && (y_position > 330 && y_position < 400)) {
                        code = 12;
                    } else if ((x_position > 280 && x_position < 330) && (y_position > 280 && y_position < 330)) {
                        code = 22;
                    } else if ((x_position > 500 && x_position < 650) && (y_position > 500 && y_position < 650)) {
                        code = 32;
                    } else if ((x_position > 400 && x_position < 500) && (y_position > 400 && y_position < 500)) {
                        code = 13;
                    } else if ((x_position > 650 && x_position < 700) && (y_position > 650 && y_position < 700)) {
                        code = 23;
                    } else if ((x_position > 700 && x_position < 1024) && (y_position > 700 && y_position < 1024)) {
                        code = 33;
                    } else {
                        code = 22;
                    }

                    if (joyStickData.size() > 10) {
                        Thread.sleep(300);
                    } else {
                        joyStickData.add(String.valueOf(code));
                    }

                    if (buttonData.size() > 10) {
                        Thread.sleep(300);
                    } else {
                        buttonData.add(button);
                    }
                } catch (SerialPortException | InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}