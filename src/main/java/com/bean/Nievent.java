package com.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;


@XStreamAlias("Nievent")
public class Nievent
{
	@XStreamAsAttribute
	private String version;
	
	 @XStreamAsAttribute
	private String subject;
	 
	@XStreamAlias("Header")
	private Header header;
	
	@XStreamAlias("Body")
	private Body body;
	
	
	public Nievent(String version, String subject)
	{
		super();
		this.version = version;
		this.subject = subject;
	}

	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

	public String getSubject()
	{
		return subject;
	}

	public void setSubject(String subject)
	{
		this.subject = subject;
	}

	public Header getHeader()
	{
		return header;
	}

	public void setHeader(Header header)
	{
		this.header = header;
	}
	
	
	public void setVariable(String name, String value)
	{
		header = new Header("2.1","");
		body = new Body();	
		body.setVpcml(new Vpcml(new AsrVariableEvent(new Variable(name, value))));
	}
	

}
