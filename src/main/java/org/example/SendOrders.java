package org.example;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Scanner;

public class SendOrders {
    private final static String QUEUE_NAME = "orders";
    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        while(true) {
            try (Connection connection = factory.newConnection();
                 Channel channel = connection.createChannel()) {
                channel.queueDeclare(QUEUE_NAME, false, false, false, null);
                Scanner input = new Scanner(System.in);
                System.out.print("Digite o nome do pedido: ");
                String orderName = input.nextLine();
                System.out.print("Digite a descrição do pedido: ");
                String orderDesc = input.nextLine();
                String message = "{ orderName: " + orderName + ", orderDesc: " + orderDesc + "}";
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                System.out.println("[x] Pedido enviado: '" + message + "'");
            }
        }
    }
}
