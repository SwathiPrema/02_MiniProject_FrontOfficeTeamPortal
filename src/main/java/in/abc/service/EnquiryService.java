package in.abc.service;

import java.util.List;

import in.abc.binding.DashboardResponse;
import in.abc.binding.EnquiryForm;
import in.abc.binding.EnquirySearchCriteria;
import in.abc.entity.StudentEnquiryEntity;

public interface EnquiryService {

	public List<String> getCourses();

	public List<String> getEnqStatuses();

	public DashboardResponse getDashboardData(Integer userId);

	public boolean saveEnquiry(EnquiryForm form);

	public List<StudentEnquiryEntity> getEnquires();

	public List<StudentEnquiryEntity> getFilteredEnqs(EnquirySearchCriteria criteria,Integer userId);
}
