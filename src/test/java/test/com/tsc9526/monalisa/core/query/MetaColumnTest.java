package test.com.tsc9526.monalisa.core.query;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.tsc9526.monalisa.core.meta.MetaColumn;

/**
 * 
 * @author zzg.zhou(11039850@qq.com)
 */
@Test
public class MetaColumnTest {
  
  public void testParseRemark()throws Exception {
	  MetaColumn c=new MetaColumn();
	  c.setRemarks(
			  "#annotation"
			  +"\r\n{ "
			  +"\r\n@regex(\"[0-9]+\",\"Error message!\")"
			  +"\r\n@min(10)"
			  +"\r\n@max(256)"
			  +"\r\n}"
			  +"\r\n"			 
			  +"\r\n#bool{}"
			  
			  +"\r\n#value{"
			  +"\r\nx+y"
  			  +"\r\n}");
	  
	  Assert.assertEquals(c.getCode("Bool"),"");
	  Assert.assertEquals(c.getJavaType(),"Boolean");
	  
	  c.setRemarks("#enum{com.xx.Status}");
	  Assert.assertEquals(c.getJavaType(),"Status");
	  
	  c.setRemarks("#json{com.xx.Js}");
	  Assert.assertEquals(c.getJavaType(),"Js");
  }
}
