package com.client;


import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ClientHandler implements IoHandler
{

	private final Logger logger = (Logger) LoggerFactory.getLogger(getClass());


	public ClientHandler()
	{
	
	}


	@Override
	public void sessionOpened(IoSession session)
	{
		logger.info("Client session created:" + session.getId());
	}
	
	@Override
	public void sessionClosed(IoSession session) throws Exception 
	{
		
		logger.info("Client session closed:" + session.getId());
	}
	
	@Override
	public void sessionCreated(IoSession session) throws Exception 
	{
		logger.info("Client session created:" + session.getId());
	}

	@Override
	public void sessionIdle(IoSession arg0, IdleStatus arg1) throws Exception 
	{
		
		
	}

	@Override
	public void messageReceived(IoSession session, Object message)
	{
		logger.info("Message received in the client is: " + message.toString());
		
		// Procesar mensaje para ver si cumple algun filtro 
		// Llamar a un metodo de envio del servidor
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
	{
		session.close();
	}

	@Override
	public void messageSent(IoSession arg0, Object message) throws Exception 
	{
		

		logger.info("Message sent for the client is: " + message.toString());
	}

	

	


}
