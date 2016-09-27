/*******************************************************************************************
 *	Copyright (c) 2016, zzg.zhou(11039850@qq.com)
 * 
 *  Monalisa is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU Lesser General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.

 *	This program is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU Lesser General Public License for more details.

 *	You should have received a copy of the GNU Lesser General Public License
 *	along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************************/
package test.com.tsc9526.monalisa.orm.service.action;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;

import test.com.tsc9526.monalisa.orm.mysql.MysqlDB;
import test.com.tsc9526.monalisa.orm.mysql.mysqldb.TestRecordV2;

import com.tsc9526.monalisa.orm.service.DBS;
import com.tsc9526.monalisa.orm.service.Response;
import com.tsc9526.monalisa.orm.service.servlet.MonalisaServlet;

/**
 * 
 * @author zzg.zhou(11039850@qq.com)
 */
public abstract class AbstractActionTest {
	@BeforeClass
	public void init(){
		//MysqlDB.DB.getCfg().setProperty(DbProp.PROP_DB_SQL_DEBUG.getFullKey(), "true");
		DBS.add("db1",MysqlDB.DB);
		
		//clear data
		TestRecordV2.DELETE().truncate();
		
		for(int i=1;i<=10;i++){
			new TestRecordV2().parse("{recordId: "+i+", name: 'ns0"+i+"', title: 'title"+i+"'}").save();
		}
	}
	
	protected MockHttpServletRequest createRequest(String requestURI){
		String name=this.getClass().getSimpleName();
		int p=name.indexOf("Action");
		String method=name.substring(0,p).toUpperCase();
		
		MockHttpServletRequest       req=new MockHttpServletRequest(method,requestURI);
			
		return req;
	}
	
	protected Response getRespone(MockHttpServletRequest req)throws Exception{
		MockHttpServletResponse resp=new MockHttpServletResponse(); 
		
		MonalisaServlet ms=new MonalisaServlet();
		ms.service(req, resp);
		 
		Assert.assertEquals("application/json;charset=utf-8", resp.getContentType());
		
		String body=resp.getContentAsString();
		 
		return Response.fromJson(body); 
	}
}