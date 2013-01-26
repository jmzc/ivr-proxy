package com.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;




public class Body
{
	@XStreamAlias("vpcml")
	 Vpcml vpcml;

	 
	public Vpcml getVpcml()
	{
		return vpcml;
	}

	public void setVpcml(Vpcml vpcml)
	{
		this.vpcml = vpcml;
	}
	 
	 
	
	 
	 
	
}
