package com.altiux.eum.esite.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class AllParkingSpaceUsersDTO {
	private @JsonProperty("parkingCompanyId") String parkingCompanyId;
	private @JsonProperty("parkingSpaceId") String parkingSpaceId;
	private @JsonProperty("userResponse") List<UserAdditionResponseDTO> userResponse;

	public String getParkingCompanyId() {
		return parkingCompanyId;
	}

	public void setParkingCompanyId(String parkingCompanyId) {
		this.parkingCompanyId = parkingCompanyId;
	}

	public String getParkingSpaceId() {
		return parkingSpaceId;
	}

	public void setParkingSpaceId(String parkingSpaceId) {
		this.parkingSpaceId = parkingSpaceId;
	}

	public List<UserAdditionResponseDTO> getUserResponse() {
		return userResponse;
	}

	public void setUserResponse(List<UserAdditionResponseDTO> userResponse) {
		this.userResponse = userResponse;
	}

	public AllParkingSpaceUsersDTO() {
		super();
	}

	public AllParkingSpaceUsersDTO(String parkingCompanyId, String parkingSpaceId,
			List<UserAdditionResponseDTO> userResponse) {
		super();
		this.parkingCompanyId = parkingCompanyId;
		this.parkingSpaceId = parkingSpaceId;
		this.userResponse = userResponse;
	}
}
