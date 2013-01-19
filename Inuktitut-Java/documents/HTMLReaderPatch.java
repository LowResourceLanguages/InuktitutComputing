package documents;

import java.io.*;

public class HTMLReaderPatch extends FilterReader {
	
	public HTMLReaderPatch ( Reader r ) {
		super( r );
	}
	
	private int state = 0;
	
	public int read() throws IOException {
		int c = 0;
		switch ( state ) {
		case 0:
			c = in.read();
			if ( c == '&' ) {
				state = 1;
			}
			break;
		case 1:
			c = in.read();
			if ( c == '#' ) {
				state = 2;
			} else if ( c == '&' ) {
			} else {
				state = 0;
			}
			break;
		case 2:
			c = in.read();
			if ( c == ';' ) {
				state = 3;
			} else if ( c >= '0' && c <= '9'
					 || c >= 'a' && c <= 'a'
	                 || c >= 'A' && c <= 'F'
		             || c == 'x' || c == 'X' ) {
			} else if ( c == '&' ) {
				state = 1;
			} else {
				state = 0;
			}
			break;
		case 3:
			c = in.read();
			if ( c == ' ' ) {
				c = '&';
				state = 4;
			} else if (c == 10 ) {
				c = '&';
				state = 8;
			} else if ( c == '&' ) {
				state = 1;
			} else {
				state = 0;
			}
			break;
		case 4:
			c = '#';
			state = 5;
			break;
		case 5:
			c = '3';
			state = 6;
			break;
		case 6:
			c = '2';
			state = 7;
			break;
		case 7:
			c = ';';
			state = 3;
			break;
		case 8:
				c = '#';
				state = 9;
				break;
		case 9:
				c = '1';
				state = 10;
				break;
		case 10:
				c = '0';
				state = 11;
				break;
		case 11:
			c = ';';
			state = 3;
			break;
		}
		return c;
	}
	
	public int read( char cbuf[], int off, int len ) throws IOException {
		for ( int i = off; i < len; i++ ){
			int c = this.read();
			if ( c >= 0 ) {
				cbuf[ i ] = (char) c;
			} else {
				return (i > 0 ? i : c);
			}
		}
		return cbuf.length;
	}
	
	public int read( char cbuf[] ) throws IOException {
		return read( cbuf, 0, cbuf.length );
	}
	
	public void close() throws IOException {
		state = 0;
		in.close();
	}
	
	public boolean markSupported() {
		return false;
	}
	
	public boolean ready() throws IOException {
		return in.ready();
	}
	
	public void reset() throws IOException {
		in.reset();
		state = 0;
	}
	
	public long skip( long n ) throws IOException {
		int c;
		for ( long i = 0; i < n; i++ ) {
			c = read();
			if ( c < 0 ) {
				return i;
			}
		}
		return n;
	}

}
