/*
 * Copyright (c) 2006-2009 by Dirk Riehle, http://dirkriehle.com
 *
 * This file is part of the Wahlzeit photo rating application.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */

package org.wahlzeit.services;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.wahlzeit.model.AccessRights;
import org.wahlzeit.model.CaseId;
import org.wahlzeit.model.FlagReason;
import org.wahlzeit.model.Gender;
import org.wahlzeit.model.Photo;
import org.wahlzeit.model.PhotoCase;
import org.wahlzeit.model.PhotoId;
import org.wahlzeit.model.PhotoManager;
import org.wahlzeit.model.PhotoSize;
import org.wahlzeit.model.PhotoStatus;
import org.wahlzeit.model.Tags;
import org.wahlzeit.model.UserStatus;
import org.wahlzeit.utils.StringUtil;

/**
 * A simple abstract implementation of Persistent with write count and dirty bit.
 * Also defines (but does not use) the field "ID" for subclass use.
 * 
 * @author dirkriehle
 *
 */



public abstract class DataObject implements Persistent {
	
	/**
	 * Not used in the class but needed by broad array of subclasses
	 */
	public static final String ID = "id";

	/**
	 * 
	 */
	protected transient int writeCount = 0;
	
	/**
	 * 
	 */
	public final boolean isDirty() {
		return writeCount != 0;
	}
	
	/**
	 * 
	 */
	public final void resetWriteCount() {
		writeCount = 0;
	}
	
	/**
	 * 
	 */
	public final void incWriteCount() {
		writeCount++;
	}
	
	/**
	 * 
	 */
	public final void touch() {
		incWriteCount();
	}
	
	public void writeOn(ResultSet rset) throws SQLException{
		writeOn(this, rset);
	}
	
	public static void writeOn(Persistent pers, ResultSet rset) throws SQLException{
		writeOn(pers, pers.getClass(), rset);
	}
	
	public static void writeOn(Persistent pers, Class<?> c, ResultSet rset) throws SQLException{
		System.out.println("start writing on "+pers.getClass());
		
		//System.out.println()
		try{
			Field fields[] = c.getDeclaredFields();
			String db = "";
			Database annotation = null;
			
			for(Field f : fields){
				f.setAccessible(true);
				
				annotation = f.getAnnotation(Database.class);
				
				if(annotation != null){
					db = annotation.value();
					
					System.out.println("writing to: "+db+ " ("+f.getType()+")");
					
					if(f.getType() == PhotoId.class)
						rset.updateInt(db,((PhotoId)f.get(pers)).asInt());
					else if(f.getType() == CaseId.class)
						rset.updateInt(db,((CaseId)f.get(pers)).asInt());
					else if(f.getType() == AccessRights.class)
						rset.updateInt(db,((AccessRights)f.get(pers)).asInt());
					else if(f.getType() == Gender.class)
						rset.updateInt(db,((Gender)f.get(pers)).asInt());
					else if(f.getType() == FlagReason.class)
						rset.updateInt(db,((FlagReason)f.get(pers)).asInt());
					else if(f.getType() == Photo.class){
						if(f.get(pers) != null)
							rset.updateInt(db,((Photo)f.get(pers)).getId().asInt());
						else
							rset.updateInt(db,0);
					}else if(f.getType() == Tags.class)
						rset.updateString(db,((Tags)f.get(pers)).asString());
					else if(f.getType() == PhotoStatus.class)
						rset.updateInt(db,((PhotoStatus)f.get(pers)).asInt());
					else if(f.getType() == UserStatus.class)
						rset.updateInt(db,((UserStatus)f.get(pers)).asInt());
					else if(f.getType() == Language.class)
						rset.updateInt(db,((Language)f.get(pers)).asInt());
					else if(f.getType() == EmailAddress.class){
						if(f.get(pers) != null)
							rset.updateString(db,((EmailAddress)f.get(pers)).asString());
						else
							rset.updateString(db,"");
					}else if (f.getType() == URL.class)
						rset.updateString(db,((URL)f.get(pers)).toString());
					else if (f.getType() == String.class)
						rset.updateString(db,(String)f.get(pers));
					else if (f.getType() == int.class)
						rset.updateInt(db,((Integer)f.get(pers)));
					else if (f.getType() == Boolean.class)
						rset.updateBoolean(db,(Boolean)f.get(pers));
					else if (f.getType() == Long.class)
						rset.updateLong(db,(Long)f.get(pers));
				}
			}
			
			//recursive search
			if(c.getSuperclass() != null)
				DataObject.writeOn(pers,c.getSuperclass(),rset);
			
		}catch(Exception e){
			System.out.println("MESSAGE: "+e.getMessage());
			
		}
	
	}
	
	public void readFrom(ResultSet rset) throws SQLException{
		readFrom(this, rset);
	}
	
	public static void readFrom(Persistent pers, ResultSet rset) throws SQLException{
		readFrom(pers, pers.getClass(), rset);
	}
	
	public static void readFrom(Persistent pers, Class<?> c, ResultSet rset) throws SQLException {
		
		System.out.println("started reading on "+pers.getClass());
		
		//System.out.println()
		try{
			Field fields[] = c.getDeclaredFields();
			String db = "";
			Database annotation = null;
			
			for(Field f : fields){
				f.setAccessible(true);
				
				annotation = f.getAnnotation(Database.class);
				
				if(annotation != null){
					db = annotation.value();
					
					System.out.println("reading from: "+db+ " ("+f.getType()+")");
					
					if(f.getType() == PhotoId.class)
						f.set(pers, PhotoId.getId(rset.getInt(db)));
					else if(f.getType() == CaseId.class)
						f.set(pers, new CaseId(rset.getInt(db)));
					else if(f.getType() == AccessRights.class)
						f.set(pers, AccessRights.getFromInt(rset.getInt(db)));
					else if(f.getType() == Gender.class)
						f.set(pers, Gender.getFromInt(rset.getInt(db)));
					else if(f.getType() == FlagReason.class)
						f.set(pers, FlagReason.getFromInt(rset.getInt(db)));
					else if(f.getType() == Photo.class)
							f.set(pers, PhotoManager.getPhoto(PhotoId.getId(rset.getInt(db))));
					else if(f.getType() == Tags.class)
						f.set(pers, new Tags(rset.getString(db)));
					else if(f.getType() == PhotoStatus.class)
						f.set(pers, PhotoStatus.getFromInt(rset.getInt(db)));
					else if(f.getType() == UserStatus.class)
						f.set(pers, UserStatus.getFromInt(rset.getInt(db)));
					else if(f.getType() == Language.class)
						f.set(pers, Language.getFromInt(rset.getInt(db)));
					else if(f.getType() == EmailAddress.class){
						if(rset.getString(db).equals(""))
							f.set(pers, null);
						else
							f.set(pers, EmailAddress.getFromString(rset.getString(db)));
					}else if (f.getType() == URL.class){
						if(StringUtil.isValidURL(rset.getString(db))){
							f.set(pers, StringUtil.asUrl(rset.getString(db)));
						}
					}else if (f.getType() == String.class)
						f.set(pers, rset.getString(db));
					else if (f.getType() == int.class)
						f.set(pers, rset.getInt(db));
					else if (f.getType() == Boolean.class)
						f.set(pers, rset.getBoolean(db));
					else if (f.getType() == Long.class)
						f.set(pers, rset.getLong(db));
				}
			}
			
			//recursive search
			if(c.getSuperclass() != null)
				DataObject.readFrom(pers,c.getSuperclass(),rset);
			
		}catch(Exception e){
			System.out.println("MESSAGE: "+e.getMessage());
			
		}
		
		
	}
}
