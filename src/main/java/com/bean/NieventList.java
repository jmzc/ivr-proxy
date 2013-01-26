package com.bean;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("NieventList")
public class NieventList
{

	@XStreamImplicit
	private List<Nievent> nievent;

	
	public List<Nievent> getNievent()
	{
		return nievent;
	}

	public void setNievent(List<Nievent> nievent)
	{
		this.nievent = nievent;
	}
	
	
	public void addNievent(Nievent n)
	{
		
		if ( this.nievent == null )
			this.nievent = new ArrayList<Nievent>();
		
		
		this.nievent.add(n);
		
	}
	
	
	
}
