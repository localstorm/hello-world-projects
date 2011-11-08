package org.localstorm.marketwatch.pricing;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;
import org.cyberneko.html.parsers.DOMParser;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

class Html2XmlUrlConnector {
    private String url;
    private File file;

    public Html2XmlUrlConnector(String url, File file) {
        this.url = url;
        this.file = file;
    }

    public void fetch() throws Exception {
        HttpClient client = new HttpClient();
        GetMethod get = new GetMethod(url);
        get.setFollowRedirects(true);
        get.getParams().setParameter("http.socket.timeout", new Integer(5000));

        int statusCode = client.executeMethod(get);

        if (statusCode != HttpStatus.SC_OK) {
            throw new IOException("Request failed: " + get.getStatusLine());
        }

        InputStream is = get.getResponseBodyAsStream();

        if (is == null) {
            throw new IOException("Zero data response.");
        }

        try {
            DOMParser parser = new DOMParser();
            parser.parse(new InputSource(is));
            Document doc = parser.getDocument();

            // Prepare the DOM document for writing
            Source source = new DOMSource(doc);

            Result result = new StreamResult(file);

            // Write the DOM document to the file
            Transformer xformer = TransformerFactory.newInstance().newTransformer();
            xformer.transform(source, result);
        } finally {
            IOUtils.closeQuietly(is);
            get.releaseConnection();
        }
    }


}
