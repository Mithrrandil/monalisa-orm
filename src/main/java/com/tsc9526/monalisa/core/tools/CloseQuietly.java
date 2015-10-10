package com.tsc9526.monalisa.core.tools;

import java.io.Closeable;
import java.lang.reflect.Method;

/**
 * 
 * @author zzg.zhou(11039850@qq.com)
 */
public class CloseQuietly {

	public static void close(Closeable c){
		try{
			if(c!=null){
				c.close();
			}
		}catch(Exception e){}
	}
	
	public static void close(Object x){
		if(x instanceof Closeable){
			close((Closeable)x);
		}else if(x instanceof AutoCloseable){
			close((AutoCloseable)x);
		}else{		
			try{
				Method m=x.getClass().getMethod("close");
				m.invoke(x);
			}catch(NoSuchMethodException e){
				//do nothing
			}catch(Exception e){
				throw new RuntimeException(e);
			}
		}
	}

	public static void close(AutoCloseable c) {
		try{
			if(c!=null){
				c.close();
			}
		}catch(Exception e){}
		
	}
}

