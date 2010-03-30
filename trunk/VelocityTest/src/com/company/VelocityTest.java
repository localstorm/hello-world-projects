package com.company;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public class VelocityTest {

	public static void main(String[] args) throws Exception {
		VelocityEngine ve = new VelocityEngine();
		ve.init();
		/* next, get the Template */
		Template t = ve.getTemplate("template.vm");
		/* create a context and add data */
		VelocityContext context = new VelocityContext();
		context.put("handlerName", "DEFAULT");
		context.put("package", "com.company");
		
		List<String> attributeNames = new ArrayList<String>();
		attributeNames.add("attr");
		context.put("attributeNames", attributeNames);
		/* now render the template into a StringWriter */
		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		/* show the World */
		System.out.println(writer.toString());

	}

}
