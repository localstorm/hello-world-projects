/**
 * Copyright 1986-2007 Alexey Kuznetsov
 *
 * All rights reserved. 
 */

package org.localstorm.crypto;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.localstorm.crypto.rc4.RC4OutputStream;

public class Main {
	public static void main(String[] args) throws IOException {
		
		
		OutputStream fos = new FileOutputStream("output.txt");
		RC4OutputStream rc4os = new RC4OutputStream(fos, "hello");
		rc4os.write("hello, world!".getBytes());
		rc4os.close();
		
	}
}
