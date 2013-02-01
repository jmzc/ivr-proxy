package com.server;



import java.net.InetSocketAddress;
import java.nio.charset.Charset;


import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ProxyClient implements IoHandler
{

	private final Logger logger = (Logger) LoggerFactory.getLogger(getClass());
	
		/*
			READ BUFFER		[        ]
										<-------------->
			WRITE BUFFER	[        ]            
		*/

	// Puerto local SSH 
	private static final int PORT = 2002;
	
	
	// Cliente
	private IoSession 	session 	= null;
	private IoConnector connector 	= null;

	private IoSession   server	= null;

	
	

	public ProxyClient(IoSession server) 
	{
		super();

		this.server = server;
		
	}

	
	public boolean connected()
	{
		return ( session != null && session.isConnected());
	}
	
	
	public void connect() throws Exception 
	{
		if (this.connected())
			return;
		
		try
		{

			this.connector = new NioSocketConnector();
			
			this.connector.getSessionConfig().setReadBufferSize(2048);
		
			//this.connector.getFilterChain().addLast("logger", new LoggingFilter());
			//this.connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
		
			this.connector.setHandler(this);
			
			/*  
			 * IOFuture
			 * Represents the completion of an asynchronous I/O operation on an IoSession. Can be listened for completion using a IoFutureListener.
			 * 		CloseFuture, ConnectFuture, ReadFuture, WriteFuture
			 */
			System.out.println("Connecting client ...");
			ConnectFuture future = this.connector.connect(new InetSocketAddress("127.0.0.1", PORT));
		
			/*
			 * 	await()
				Wait for the asynchronous operation to complete.
				
				addListener(IoFutureListener<?> listener)
				Adds an event listener which is notified when this future is completed.
			 */
			
			// Wait for the asynchronous operation to complete uninterruptibly.
			future.awaitUninterruptibly();
		
			if (!future.isConnected())
			{
				throw new Exception();
				
			}
			System.out.println("Client connected");
			
			//Returns IoSession which is the result of connect operation.
			this.session = future.getSession();
					
			this.session.getConfig().setUseReadOperation(true);
			
			
		}
		catch(Exception e)
		{
			
			this.close(true);
			
		}
		
		
	}

	public void write(Object m) throws Exception
	{
		System.out.println("Proxy message ..." + m);
		if (this.session != null && this.session.isConnected())
		{
			System.out.println("Message proxied:" + m);
			this.session.write(m);
		}
		else
			throw new Exception();
	}

	
	public void close(boolean b)
	{
		// Closes this session immediately or after all queued write requests are flushed. 
		// This operation is asynchronous. Wait for the returned CloseFuture if you want to wait for the session actually closed.
		if (this.session != null)
		{
			CloseFuture future = this.session.close(b);
			future.awaitUninterruptibly();
			
		}
		
		// Releases any resources allocated by this service. 
		// Please note that this method might block as long as there are any sessions managed by this service.
		if (this.connector != null)
			this.connector.dispose();
		
	}

	
	
	@Override
	public void sessionOpened(IoSession session)
	{
		logger.info("Client session created:" + session.getId());
	}
	
	@Override
	public void sessionClosed(IoSession session) throws Exception 
	{
		
		System.out.println("Client session closed:" + session.getId());
	}
	
	@Override
	public void sessionCreated(IoSession session) throws Exception 
	{
		System.out.println("Client session created:" + session.getId());
	}

	@Override
	public void sessionIdle(IoSession arg0, IdleStatus arg1) throws Exception 
	{
		
		
	}

	@Override
	public void messageReceived(IoSession session, Object message)
	{
		
		System.out.println("Message received in the client is: " + message.toString());
		// Procesar mensaje para ver si cumple algun filtro 
		
		
		// Llamar a un metodo de envio del servidor
		ProxyServer.proxy.write(server,message);
		
		
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
	{
		session.close(false);
	}

	@Override
	public void messageSent(IoSession arg0, Object message) throws Exception 
	{

		System.out.println("Message sent for the client is: " + message.toString());
	}

	
	
	
}
