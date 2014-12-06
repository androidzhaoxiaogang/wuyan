package net.wecash.server.dao;

import java.io.InputStream;

import org.bson.types.ObjectId;

import com.mongodb.gridfs.GridFSDBFile;

/**
 * 
 * @author xkk
 *
 */
public interface FileDAO {

	public GridFSDBFile getFile(ObjectId id);

	public Object addFile(String fileName, InputStream in);

	public Object addFile(byte[] b);

	public ObjectId deleteFile(ObjectId id);

	Object addFile(String filename, byte[] b);

}
