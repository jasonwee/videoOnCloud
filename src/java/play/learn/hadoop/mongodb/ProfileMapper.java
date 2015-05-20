package play.learn.hadoop.mongodb;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Mapper;
import org.bson.BSONObject;

/**
 * http://www.slideshare.net/mongodb/using-mongodb-hadoop-together
 * 
 * slide 19
 *
 */
public class ProfileMapper extends Mapper<Object, BSONObject, IntWritable, IntWritable>{
	
	@Override
	public void map(final Object pKey, final BSONObject pValue, final Context pContext) throws IOException, InterruptedException {
		String user = (String) pValue.get("user");
		Date d1 = (Date) pValue.get("lastUpdate");
		int count = 0;
		List<String> keys = (List<String>) ((BSONObject)pValue.get("tags")).keySet();
		for (String tag : keys) {
			count += ((List)((BSONObject)pValue.get(tag)).get("hist")).size();
		}
		int avg = count / keys.size();
		pContext.write(new IntWritable(count), new IntWritable(avg));
		
	}

}
