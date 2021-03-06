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
package com.tsc9526.monalisa.orm.executor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.tsc9526.monalisa.tools.io.MelpClose;

/**
 * 
 * @author zzg.zhou(11039850@qq.com)
 */
public class ResultLoadExecutor<T>  extends RelationExecutor implements Execute<T>,Cacheable{
	private ResultHandler<T> resultHandler;
	private T result;
	
	public ResultLoadExecutor(ResultHandler<T> resultHandler,T result){
		this.resultHandler=resultHandler;
		this.result=result;
	}
	
	public T execute(Connection conn,PreparedStatement pst) throws SQLException {		
		ResultSet rs=null;
		try{
			rs=setupRelationTables(pst.executeQuery());	
			
			if(rs.next()){
				resultHandler.load(rs, result);	
				return result;
			}else{
				return null;
			}
		}finally{
			MelpClose.close(rs);
		}
	}
	
	public PreparedStatement preparedStatement(Connection conn,String sql)throws SQLException {				 
		return conn.prepareStatement(sql);
	}	
 

}
