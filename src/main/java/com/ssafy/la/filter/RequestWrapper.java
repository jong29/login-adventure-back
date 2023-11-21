package com.ssafy.la.filter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;


public class RequestWrapper extends HttpServletRequestWrapper {
	private byte[] bytes;
	private String requestBody;

	public RequestWrapper(HttpServletRequest request) throws IOException {
		super(request);
		InputStream in = super.getInputStream();
		this.bytes = convertInputStreamToByteArray(in);
		this.requestBody = new String(this.bytes);
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		final ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		return  new ServletImpl(bis);
	}

	public String getRequestBody() {
		return this.requestBody;
	}

    class ServletImpl extends ServletInputStream {
    	private InputStream is;
		public ServletImpl(InputStream bis){
			is = bis;
		}

		@Override
		public int read() throws IOException {
			return is.read();
		}

		@Override
		public int read(byte[] b) throws IOException {
			return is.read(b);
		}

		@Override
		public boolean isFinished() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isReady() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void setReadListener(ReadListener listener) {
			// TODO Auto-generated method stub
			
		}
    }

    public byte[] convertInputStreamToByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;

        try {
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
        } finally {
            // Make sure to close the InputStream
            inputStream.close();
        }

        return byteArrayOutputStream.toByteArray();
    }
}
