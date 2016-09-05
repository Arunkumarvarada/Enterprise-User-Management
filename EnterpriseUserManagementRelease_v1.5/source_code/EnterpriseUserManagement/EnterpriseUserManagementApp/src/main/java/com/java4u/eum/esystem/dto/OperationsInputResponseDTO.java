package com.altiux.eum.esystem.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class OperationsInputResponseDTO {
	private @JsonProperty("insertionStatus") String insertionStatus;

	private @JsonProperty("No Of Operations Inserted") int noOfOperationsInserted;

	private @JsonProperty("Total Number Of Operations in the System") int totalNumberOfOperationsInserted;

	public String getInsertionStatus() {
		return insertionStatus;
	}

	public void setInsertionStatus(String insertionStatus) {
		this.insertionStatus = insertionStatus;
	}

	public int getNoOfOperationsInserted() {
		return noOfOperationsInserted;
	}

	public void setNoOfOperationsInserted(int noOfOperationsInserted) {
		this.noOfOperationsInserted = noOfOperationsInserted;
	}

	public int getTotalNumberOfOperationsInserted() {
		return totalNumberOfOperationsInserted;
	}

	public void setTotalNumberOfOperationsInserted(int totalNumberOfOperationsInserted) {
		this.totalNumberOfOperationsInserted = totalNumberOfOperationsInserted;
	}

	public OperationsInputResponseDTO(String insertionStatus, int noOfOperationsInserted,
			int totalNumberOfOperationsInserted) {
		super();
		this.insertionStatus = insertionStatus;
		this.noOfOperationsInserted = noOfOperationsInserted;
		this.totalNumberOfOperationsInserted = totalNumberOfOperationsInserted;
	}

	public OperationsInputResponseDTO() {
		super();
	}

}
