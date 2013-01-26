package com.bean;


import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;


public class AsrVariableEvent
{
	@XStreamAsAttribute
	private String type="values";
	
	@XStreamAlias("variable")
	private Variable variable;

	
	
	public AsrVariableEvent(Variable variable)
	{
		super();
		this.variable = variable;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public Variable getVariable()
	{
		return variable;
	}

	public void setVariable(Variable variable)
	{
		this.variable = variable;
	}
	
	
	
}
