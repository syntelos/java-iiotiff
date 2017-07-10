/*
 * iiotiff - Read TIFF file from command line argument, write PNG file.
 * Copyright (C) 2017, John Pritchard, Syntelos
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


/**
 * Read TIFF file from command line argument, write PNG file.
 * 
 * @author John Pritchard, Syntelos
 */
public class iiotiff extends Object {

    private final static String PN = "iiotiff";

    private final static void usage(){
	System.err.flush();
	System.err.printf("Usage:%n\t%s file.tif%n%n",PN);
	System.exit(1);
    }
    private final static void error(Exception x, String t, String f){
	System.err.flush();
	System.err.printf("%s: %s error for '%s'.%n",PN,t,f);
	x.printStackTrace(System.err);
	System.exit(1);
    }
    private final static String outfn(String infn){
	if (null != infn && 0 < infn.length()){
	    int ix = infn.lastIndexOf('.');
	    if (0 < ix){

		return (infn.substring(0,ix)+".png");
	    }
	    else {
		throw new IllegalArgumentException(infn);
	    }
	}
	else {
	    throw new IllegalArgumentException("Empty filename argument");
	}
    }

    public static void main(String[] argv){

	final int argl = argv.length;

	if (0 < argl){
	    String in_fn = argv[0];
	    File in = new File(in_fn);
	    if (in.isFile()){
		try {
		    BufferedImage img = ImageIO.read(in);
		    if (null == img){

			System.err.printf("%s read error for '%s'.%n",PN,in_fn);
		    }
		    else {
			String out_fn = outfn(in_fn);
			try {
			    File out = new File(out_fn);

			    ImageIO.write(img,"png",out);

			    System.exit(0);
			}
			catch (IOException iox){

			    error(iox,"write",out_fn);
			}
		    }
		}
		catch (IOException iox){

		    error(iox,"read",in_fn);
		}
		catch (IllegalArgumentException ila){

		    ila.printStackTrace(System.err);

		    System.exit(1);
		}
	    }
	    else {
		System.err.printf("%s file not found '%s'.%n",PN,in_fn);

		usage();
	    }
	}
	else {
	    usage();
	}
    }
}
