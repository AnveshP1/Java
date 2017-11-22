package com.anvesh.camel_filetransfer;

import org.apache.camel.builder.RouteBuilder;

public class SimpleRouteBuilder extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		from("file:C:/inputFolder").to("file:C:/outputFolder");
		
	}
	

}
