package com.java4u.eum.entities;

import java.sql.Timestamp;

import javax.annotation.Nonnull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "UserRequest")
public class UserRequest {

	@Id
	@GeneratedValue(generator = "user_request_id_generator")
	@GenericGenerator(name = "user_request_id_generator", strategy = "com.altiux.eum.entities.idgenerator.UserRequestIdGenerator")
	@Column(name = "requestId")
	private String requestId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "enterpriseSiteId")
	private EnterpriseSite enterpriseSite;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "requestedUserId")
	private EnterpriseSystemUser enterpriseSystemUser;

	@Column(name = "requestState")
	@Nonnull
	private String requestState;

	@Column(name = "operationType")
	@Nonnull
	private String operationType;

	@Column(name = "operatedUserId")
	private String operatedUserId;

	@Column(name = "createdTime")
	private Timestamp createdTime;

	@Column(name = "completedTime")
	private Timestamp completedTime;

	public EnterpriseSite getEnterpriseSite() {
		return enterpriseSite;
	}

	public void setEnterpriseSite(EnterpriseSite enterpriseSite) {
		this.enterpriseSite = enterpriseSite;
	}

	public UserRequest() {
		super();
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public EnterpriseSite getParkingSpace() {
		return enterpriseSite;
	}

	public void setParkingSpace(EnterpriseSite enterpriseSite) {
		this.enterpriseSite = enterpriseSite;
	}

	public EnterpriseSystemUser getEnterpriseSystemUser() {
		return enterpriseSystemUser;
	}

	public void setEnterpriseSystemUser(EnterpriseSystemUser enterpriseSystemUser) {
		this.enterpriseSystemUser = enterpriseSystemUser;
	}

	public String getRequestState() {
		return requestState;
	}

	public void setRequestState(String requestState) {
		this.requestState = requestState;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getOperatedUserId() {
		return operatedUserId;
	}

	public void setOperatedUserId(String operatedUserId) {
		this.operatedUserId = operatedUserId;
	}

	public Timestamp getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}

	public Timestamp getCompletedTime() {
		return completedTime;
	}

	public void setCompletedTime(Timestamp completedTime) {
		this.completedTime = completedTime;
	}

	public UserRequest(String requestId, EnterpriseSite enterpriseSite, EnterpriseSystemUser enterpriseSystemUser,
			String requestState, String operationType, String operatedUserId, Timestamp createdTime,
			Timestamp completedTime) {
		super();
		this.requestId = requestId;
		this.enterpriseSite = enterpriseSite;
		this.enterpriseSystemUser = enterpriseSystemUser;
		this.requestState = requestState;
		this.operationType = operationType;
		this.operatedUserId = operatedUserId;
		this.createdTime = createdTime;
		this.completedTime = completedTime;
	}

	@Override
	public String toString() {
		return "UserRequest [requestId=" + requestId + ", enterpriseSite=" + enterpriseSite + ", enterpriseSystemUser="
				+ enterpriseSystemUser + ", requestState=" + requestState + ", operationType=" + operationType
				+ ", operatedUserId=" + operatedUserId + ", createdTime=" + createdTime + ", completedTime="
				+ completedTime + "]";
	}

}
