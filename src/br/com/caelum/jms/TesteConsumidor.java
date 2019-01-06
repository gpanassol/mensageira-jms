package br.com.caelum.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TesteConsumidor {

	public static void main(String[] args) throws Exception {
		
		//ABRE CONEXAO
		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection conn = factory.createConnection();
		conn.start();
		
		//RECEBE NOVAS MENSAGEM
		Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE); //confirmar o recebimento da mensagem
		Destination fila = (Destination) context.lookup("financeiro"); //queue.financeiro
		MessageConsumer consumer = session.createConsumer(fila);
		
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {

				TextMessage text = (TextMessage) message;
				try {
					System.out.println(text.getText());
					
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});

		new Scanner(System.in).nextLine();
		
		session.close();
		conn.close();
		context.close();		
	}
}
