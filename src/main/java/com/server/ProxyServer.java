package com.server;


import java.io.IOException;
import java.net.InetSocketAddress;

import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandler;
/*
import java.nio.charset.Charset;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;

*/
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProxyServer implements IoHandler
{
	private static final Logger logger = (Logger) LoggerFactory.getLogger(ProxyServer.class);
	
	
	public Integer port;

	private static ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"services.xml"});		
	
	
	public static void main(String[] args) throws IOException
	{
		try
		{
			
			context.getBean(ProxyServer.class).listen();
			
		}
		catch(Exception e)
		{
			logger.error("Error proxy server [" + e.getMessage() + "]");
		}
	
	}
	

	public ProxyServer(int port) 
	{
		this.port = port;
	}


	private Map<Long,ProxyClient> h = new HashMap<Long,ProxyClient>();

	
	private ProxyClient getProxyClientMap(IoSession session)
	{
		return h.get(session.getId());
	}
	
	private void setProxyClientMap(IoSession session, ProxyClient client)
	{
		h.put(session.getId(),client);
	}
	
	public ProxyClient getProxyClient(IoSession session) throws Exception 
	{
		
		ProxyClient c1 = this.getProxyClientMap(session);

		if (c1!= null && c1.connected())
			return c1;
		else
		{
			this.setProxyClientMap(session, null);
			
			ProxyClient c2 = (ProxyClient)context.getBean("client", session);
			c2.connect();

			this.setProxyClientMap(session, c2);
				
			return c2;
		}
	
	}
	
	
	private void listen() throws Exception
	{
		
		IoAcceptor acceptor = new NioSocketAcceptor(); 

		/*
		Two filters has been used
			-	the first one is the “LoggingFilter” which logs all the events and requests and 
			-	the second one is the “ProtocolCodecFilter” which is used to convert an incoming ByteBuffer into message POJO.
		*/ 
		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		//acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));

		acceptor.setHandler(this);
		
		acceptor.getSessionConfig().setReadBufferSize(2048);
		//acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);

		acceptor.bind(new InetSocketAddress("127.0.0.1", this.port));

	}
	
	
	public void write(IoSession session, Object message)
	{
		// TODO REGLAS 
		session.write(message);
	}
	
	@Override
	public void sessionCreated(IoSession session) throws Exception 
	{
		logger.info("Server session created:" + session.getId());
		
			
	}

	
	@Override
	public void sessionOpened(IoSession session)
	{
		logger.info("Server session opened:" + session.getId());
		
	}
	
	@Override
	public void sessionClosed(IoSession session) throws Exception 
	{
		logger.info("Session server closed:" + session.getId());
		
		ProxyClient client = this.getProxyClientMap(session);
		if ( client != null )
			client.close(false);
	}
	

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
	{
		logger.info("Session server idle:" + session.getId());
		
		try
		{
			ProxyClient client = this.getProxyClientMap(session);
			if ( client != null )
				client.close(false);
			
		}
		catch(Exception e)
		{
			logger.error("Error when closing proxy client [" + e.getMessage() + "]");
		}
		
		session.close(false);
		
		
		
		
	}


	@Override
	public void messageReceived(IoSession session, Object message)
	{

		try
		{

			ProxyClient client = this.getProxyClient(session);
			client.write(message);
			
		}
		catch(Exception e)
		{

			logger.error("Server error when sending message to IVR [" + e.getMessage() + "]");
		}
		
		
		
		
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
	{
		
		session.close(false);
	}

	@Override
	public void messageSent(IoSession arg0, Object message) throws Exception 
	{
		logger.info("Message sent from the server");
	}


}
