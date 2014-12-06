package net.wecash.server.file.service;

import java.io.InputStream;

import net.wecash.common.service.MongoService;
import net.wecash.server.dao.FileDAO;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

@Service
public class FileService extends MongoService implements FileDAO {

	@Autowired
	ObjectMapper mapper;

	@Override
	public GridFSDBFile getFile(ObjectId id) {
		GridFSDBFile gridFSDBFile = null;
		try {
			MongoTemplate template = factory.getDefaultMongoTemplate();
			GridFS gridFS = new GridFS(template.getDb());
			gridFSDBFile = gridFS.findOne(new BasicDBObject("_id", id));
		} catch (MongoException e) {
			e.printStackTrace();
		}
		return gridFSDBFile;
	}

	@Override
	public Object addFile(String fileName, InputStream in) {
		MongoTemplate template = factory.getDefaultMongoTemplate();
		GridFS gridFS = new GridFS(template.getDb());
		GridFSInputFile inputFile = gridFS.createFile(in, fileName);
		inputFile.save();
		return inputFile.getId();
	}

	@Override
	public Object addFile(byte[] b) {
		MongoTemplate template = factory.getDefaultMongoTemplate();
		GridFS gridFS = new GridFS(template.getDb());
		GridFSInputFile inputFile = gridFS.createFile(b);
		inputFile.save();
		return inputFile.getId();
	}

	@Override
	public ObjectId deleteFile(ObjectId id) {
		ObjectId fileId = null;
		try {
			MongoTemplate template = factory.getDefaultMongoTemplate();
			GridFS gridFS = new GridFS(template.getDb());
			gridFS.remove(id);
		} catch (MongoException e) {
			e.printStackTrace();
		}
		return fileId;
	}
	@Override
	public Object addFile(String filename,byte[] b) {
		MongoTemplate template = factory.getDefaultMongoTemplate();
		GridFS gridFS = new GridFS(template.getDb());
		GridFSInputFile inputFile = gridFS.createFile(b);
		inputFile.setFilename(filename);
		inputFile.save();
		return inputFile.getId();
	}

}
