package com.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;




public class Vpcml
{

	@XStreamAsAttribute
	private String id = "";
	

	@XStreamAlias("asr-variable-event")
	private AsrVariableEvent asrVariableEvent;


	public Vpcml(AsrVariableEvent asrVariableEvent)
	{
		super();
		this.asrVariableEvent = asrVariableEvent;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}
	
	



	public AsrVariableEvent getAsrVariableEvent()
	{
		return asrVariableEvent;
	}

	public void setAsrVariableEvent(AsrVariableEvent asrVariableEvent)
	{
		this.asrVariableEvent = asrVariableEvent;
	}
	
	
}
