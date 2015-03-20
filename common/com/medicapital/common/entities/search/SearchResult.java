package com.medicapital.common.entities.search;

import java.util.List;
import com.medicapital.common.entities.SerializableEntity;

public class SearchResult<E extends SerializableEntity> implements SerializableEntity {

	private int tootalResultsCount;

	private List<E> results;

	public SearchResult() {
		// empty
	}

	public void setResults(List<E> results) {
		this.results = results;
	}

	public void setTootalResultsCount(int tootalResultsCount) {
		this.tootalResultsCount = tootalResultsCount;
	}

	public List<E> getResults() {
		return results;
	}

	public int getTootalResultsCount() {
		return tootalResultsCount;
	}

	@Override
	public int getId() {
		return 0;
	}

	@Override
	public void setId(int entityId) {
		// empty
	}
}
