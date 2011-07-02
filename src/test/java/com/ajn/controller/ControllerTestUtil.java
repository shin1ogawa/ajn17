package com.ajn.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletInputStream;

import org.slim3.controller.SimpleController;
import org.slim3.tester.MockHttpServletRequest;

/**
 * utilities for testing of controller class.
 * @author shin1ogawa
 */
public class ControllerTestUtil {

	/**
	 * create {@link ServletInputStream} for using 
	 * {@link MockHttpServletRequest#setInputStream(ServletInputStream)} and return it.
	 * @param content arrays of byte that will be used for input of {@link SimpleController}.
	 * @return {@link ServletInputStream}
	 * @author shin1ogawa
	 */
	public static ServletInputStream newJsonInputStream(byte[] content) {
		return new JsonInputStream(content);
	}


	static class JsonInputStream extends ServletInputStream {

		InputStream in;


		JsonInputStream(byte[] content) {
			this.in = new ByteArrayInputStream(content);
		}

		@Override
		public int available() throws IOException {
			return in.available();
		}

		@Override
		public int read() throws IOException {
			return in.read();
		}

		@Override
		public int read(byte[] b, int off, int len) throws IOException {
			return in.read(b, off, len);
		}
	}
}
