package in.abc.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/*import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
*/

@Entity
@Table(name = "AIT_ENQUIRY_STATUS")

public class EnquiryStatusEntity {
	
	@Id
	@GeneratedValue
	private Integer EnqId;
	
	private String EnqStatus;

	public Integer getEnqId() {
		return EnqId;
	}

	public void setEnqId(Integer enqId) {
		EnqId = enqId;
	}

	public String getEnqStatus() {
		return EnqStatus;
	}

	public void setEnqStatus(String enqStatus) {
		EnqStatus = enqStatus;
	}
}
