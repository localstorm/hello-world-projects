package org.localstorm.comet;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

public class TestCallback implements GenericCallback
{  
  private String resource;

  public TestCallback(String resource)
  {
    this.resource = resource;
  }

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException
  {
    InputStream is = Class.class.getResourceAsStream(resource);
    String s = IOUtils.toString(is);
    response.getOutputStream().print(s);
    IOUtils.closeQuietly(is);
  }

}
