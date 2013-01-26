package com.bean;

import com.thoughtworks.xstream.XStream;


import com.thoughtworks.xstream.io.xml.DomDriver;
//import com.thoughtworks.xstream.io.xml.StaxDriver;

public class TestMain
{


	public static void main(String[] args)
	{
		/*
		 * You require xstream-[version].jar, xpp3-[version].jar and xmlpull-[version].jar in the classpath. 
		 * Xpp3 is a very fast XML pull-parser implementation. 
		 * If you do not want to include these dependencies,
		 * you can use a standard JAXP DOM parser or since Java 6 the integrated StAX parser instead
		 */
		
		XStream xstream = new XStream(new DomDriver()); 
		//XStream xstream = new XStream(new StaxDriver()); // does not require XPP3 library starting with Java 6
		xstream.processAnnotations(NieventList.class);
		xstream.processAnnotations(Nievent.class);
		xstream.processAnnotations(Header.class);
		xstream.processAnnotations(Body.class);
		xstream.processAnnotations(Vpcml.class);
		xstream.processAnnotations(AsrVariableEvent.class);
		xstream.processAnnotations(Variable.class);

		NieventList nieventList = new  NieventList();

		Nievent nievent = new Nievent("2.1","modality.voice.evtrecognitionparametersreported");
		nievent.setVariable("COLA_LLENA", "");
		nieventList.addNievent(nievent);

		
		String xml = xstream.toXML(nieventList);
		
		System.out.println(xml);
		
		
	}

}
