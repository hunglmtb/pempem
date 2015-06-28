package com.tbs.server.factories;

import java.util.Date;
import java.util.List;

import org.slim3.datastore.Datastore;
import org.slim3.datastore.ModelQuery;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.tbs.server.model.Sample;

public class SampleFactory extends EntityFactory {

	private static final String SAMPLE = "sample";
	private static SampleFactory instance = null;

	public static SampleFactory getInstance() {
		if (instance == null) {
			instance = new SampleFactory();
		}

		return instance;
	}

	public List<Sample> getSamples() {
		List<Sample> sampleEntities = null;
		Key ancesstorkey = KeyFactory.createKey(SAMPLE, SAMPLE);

		ModelQuery<Sample> sampleQuery  = Datastore.query(Sample.class,ancesstorkey);

		int limit = 10;
		int offset = 0;
		sampleQuery.limit(limit);
		sampleQuery.offset(offset);
		//mediaQuery.
		sampleEntities = sampleQuery.asList();

		return sampleEntities;
	}

	public void generateSample() {
		List<Sample> samples = getSamples();
		if (samples==null||samples.size()<=0) {
			Sample sample = null;
			Date now = new Date();
			Key ancestorKey = KeyFactory.createKey(SAMPLE, SAMPLE);
			for (int i = 0; i < 10; i++) {
				sample = new Sample();
				sample.setCurrentSeenTime("currentSeenTime "+i);
				sample.setDone(i%2==0);
				sample.setDonePercent(5*i);
				sample.setLastSeenTime(now);
				sample.setViewCount(i);
				Key key = Datastore.allocateId(ancestorKey, Sample.class);
				sample.setKey(key);
				Datastore.put(sample);
			}
		}
	}
}

