package com.client;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class Client
{

		/*
			READ BUFFER		[        ]
										<-------------->
			WRITE BUFFER	[        ]            
		*/

	// Puerto local SSH 
	private static final int PORT = 2001;
	
	
	

	public static void main(String[] args) throws IOException, InterruptedException
	{
		
		System.out.println("Starting client ... ");
		IoConnector connector = new NioSocketConnector();
		
		
		connector.getSessionConfig().setReadBufferSize(2048);
	
		connector.getFilterChain().addLast("logger", new LoggingFilter());
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
	
		connector.setHandler(new ClientHandler());
		
		/*  
		 * IOFuture
		 * Represents the completion of an asynchronous I/O operation on an IoSession. Can be listened for completion using a IoFutureListener.
		 * 		CloseFuture, ConnectFuture, ReadFuture, WriteFuture
		 */
		System.out.println("Connecting ...");
		ConnectFuture future = connector.connect(new InetSocketAddress("127.0.0.1", PORT));
	
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
			System.out.println("No connected");
			return;
		}
		System.out.println("Connected");
		
		//Returns IoSession which is the result of connect operation.
		IoSession session = future.getSession();
		
		System.out.println("Writing...");
		session.write("HOLA FONDO NORTE");
	
		//Enables or disabled IoSession.read() operation.
		//session.getConfig().setUseReadOperation(true);
		
		/*
			getCloseFuture()
			Returns the CloseFuture of this session. This method returns the same instance whenever user calls it.
			
			CloseFuture
				for asynchronous close requests
		*/
		session.getCloseFuture().awaitUninterruptibly();

	
		System.out.println("After Writing");
		
		// Releases any resources allocated by this service. 
		// Please note that this method might block as long as there are any sessions managed by this service.
		connector.dispose();

	}


}
