package com.java4u.eum.esystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class OperationsDTO {

	private @JsonProperty("operationId") String operationId;

	private @JsonProperty("moduleOperationName") String moduleOperationName;

	private @JsonProperty("readPermission") Boolean readPermission;

	private @JsonProperty("executePermission") Boolean executePermission;

	private @JsonProperty("writePermission") Boolean writePermission;

	public String getOperationId() {
		return operationId;
	}

	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}

	public String getModuleOperationName() {
		return moduleOperationName;
	}

	public void setModuleOperationName(String moduleOperationName) {
		this.moduleOperationName = moduleOperationName;
	}

	public Boolean getReadPermission() {
		return readPermission;
	}

	public void setReadPermission(Boolean readPermission) {
		this.readPermission = readPermission;
	}

	public Boolean getExecutePermission() {
		return executePermission;
	}

	public void setExecutePermission(Boolean executePermission) {
		this.executePermission = executePermission;
	}

	public Boolean getWritePermission() {
		return writePermission;
	}

	public void setWritePermission(Boolean writePermission) {
		this.writePermission = writePermission;
	}

	public OperationsDTO(String moduleOperationName, Boolean readPermission, Boolean executePermission,
			Boolean writePermission) {
		super();
		this.moduleOperationName = moduleOperationName;
		this.readPermission = readPermission;
		this.executePermission = executePermission;
		this.writePermission = writePermission;
	}

	public OperationsDTO(String operationId, String moduleOperationName, Boolean readPermission,
			Boolean executePermission, Boolean writePermission) {
		super();
		this.operationId = operationId;
		this.moduleOperationName = moduleOperationName;
		this.readPermission = readPermission;
		this.executePermission = executePermission;
		this.writePermission = writePermission;
	}

	@Override
	public String toString() {
		return "OperationsDTO [operationId=" + operationId + ", moduleOperationName=" + moduleOperationName
				+ ", readPermission=" + readPermission + ", executePermission=" + executePermission
				+ ", writePermission=" + writePermission + "]";
	}

	public OperationsDTO() {

	}

}
