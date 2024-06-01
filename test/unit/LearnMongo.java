import java.io.File;
import java.io.IOException;

import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;


public class LearnMongo {

	public static void main(String[] args) throws MongoException, IOException {
		Mongo mongo = new Mongo("192.168.0.2", 27017);
		DB db = mongo.getDB("mp3db");
		
		
		// save image
		String newFilename = "django.mp3";
		File mp3File = new File("src/resources/django.mp3");
		GridFS gfsMp3 = new GridFS(db, "mp3");
		GridFSInputFile gfsFile = gfsMp3.createFile(mp3File);
		gfsFile.setFilename(newFilename);
		gfsFile.setContentType("audio/mpeg");
		System.out.println("before " + gfsFile.toString());
		gfsFile.save();
		
		// get mp3
		GridFSDBFile imageForOutput = gfsMp3.findOne(newFilename);
		System.out.println("get info " + imageForOutput);
		
		// print image
		DBCursor cursor = gfsMp3.getFileList();
		while (cursor.hasNext()) {
			System.out.println("list " + cursor.next());
		}
		
		// save into another image
		imageForOutput.writeTo("newsong.mp3");
		
		// delete image
		//gfsMp3.remove(gfsMp3.findOne(newFilename));

	}

}
