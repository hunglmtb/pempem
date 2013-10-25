package com.tbs.server.factories;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;

public class EntityFactory {
	
	/** Google app engine datastore */
	private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
	/**
	 * Counts number of entities
	 * @param kind : Kind of entity to count
	 * @return number of entities searched for
	 */
	public int countEntities(String kind){
		Query query = new Query(kind);
		FetchOptions fetchOptions = FetchOptions.Builder.withLimit(100000);
		PreparedQuery pq = datastore.prepare(query);
		
		//return pq.countEntities(fetchOptions);
		return pq.countEntities(fetchOptions);
	}
	/**
	 * Counts number of entities
	 * @param kind : Kind of entity to count
	 * @param filters : list of filter
	 * @return number of entities searched for
	 */
	public int countEntities(String kind,List<Filter> filters){
		Query query = new Query(kind);
		 
		if(filters != null && filters.size() > 0){
			if( filters.size() > 1){
				CompositeFilter compositeFilter = new CompositeFilter(CompositeFilterOperator.AND,filters);
				query.setFilter(compositeFilter);
			} else {
				query.setFilter(filters.get(0));
			}
		}
		
		FetchOptions fetchOptions = FetchOptions.Builder.withLimit(100000);
		PreparedQuery pq = datastore.prepare(query);
		
		//return pq.countEntities(fetchOptions);
		return pq.countEntities(fetchOptions);
	}
}

