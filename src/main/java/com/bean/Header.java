package com.bean;


import com.thoughtworks.xstream.annotations.XStreamAsAttribute;



public class Header
{

	@XStreamAsAttribute
	private String name;
	
	@XStreamAsAttribute
	private String value;

	
	
	public Header(String name, String value)
	{
		super();
		this.name = name;
		this.value = value;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}
	
	
	
	
	
}
