package in.abc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.abc.binding.DashboardResponse;
import in.abc.binding.EnquiryForm;
import in.abc.binding.EnquirySearchCriteria;
import in.abc.entity.CourseEntity;
import in.abc.entity.EnquiryStatusEntity;
import in.abc.entity.StudentEnquiryEntity;
import in.abc.entity.UserDetailsEntity;
import in.abc.repo.CourseRepo;
import in.abc.repo.EnquiryStatusRepo;
import in.abc.repo.StudentEnquiryRepo;
import in.abc.repo.UserDetailsRepo;


@Service
public class EnquiryServiceImpl implements EnquiryService {
	
	DashboardResponse response = new DashboardResponse();
	
	@Autowired
	private UserDetailsRepo  userDtlsRepo;
	
	@Autowired
	private StudentEnquiryRepo enqRepo;
	
	@Autowired
	private CourseRepo courseRepo;
	
	@Autowired
	private EnquiryStatusRepo statusRepo;
	
	@Autowired
	private HttpSession session;

	public DashboardResponse getDashboardData(Integer userId) {
		
		Optional<UserDetailsEntity> findById=userDtlsRepo.findById(userId);

		if(findById.isPresent()) {
			UserDetailsEntity userEntity = findById.get();
			
			List<StudentEnquiryEntity> enquries = userEntity.getEnquries();
			
            Integer totalEnquriesCnt = enquries.size();
			
            Integer enrolledCnt = enquries.stream()			
            .filter(e -> e.getEnqStatus().equals("Enrolled"))
			.collect(Collectors.toList()).size();
            
            Integer lostCnt = enquries.stream()
            .filter(e -> e.getEnqStatus().equals("Lost"))
            .collect(Collectors.toList()).size();
            
            response.setTotalEnquriesCnt(totalEnquriesCnt);
            response.setEnrolledCnt(enrolledCnt);
            response.setLostCnt(lostCnt);
			}
		
		return response;
	}

	@Override
	public List<String> getCourses() {
		
		List<CourseEntity> findAll = courseRepo.findAll();
		
		List<String> names = new ArrayList<>();
		
		for(CourseEntity entity : findAll) {
			names.add(entity.getCourseName());
		}
		
		return names;
	}
	@Override
	public List<String> getEnqStatuses() {
		
    List<EnquiryStatusEntity> findAll = statusRepo.findAll();

	List<String> status = new ArrayList<>();
	
	for(EnquiryStatusEntity entity : findAll) {
		status.add(entity.getEnqStatus());
	}
	
		return status;
	}
	@Override
	public boolean saveEnquiry(EnquiryForm form) {
		
		StudentEnquiryEntity enqEntity = new StudentEnquiryEntity();
		BeanUtils.copyProperties(form, enqEntity);
		
		Integer userId = (Integer) session.getAttribute("userId");
		
		UserDetailsEntity userEntity = userDtlsRepo.findById(userId).get();
		enqEntity.setUser(userEntity);
		
		enqRepo.save(enqEntity);
		
		return true;
	}
	
	public List<StudentEnquiryEntity> getEnquires(){
		Integer userId = (Integer) session.getAttribute("userId");
		Optional<UserDetailsEntity> findById = userDtlsRepo.findById(userId);
		if(findById.isPresent()) {
			UserDetailsEntity userDtlsEntity = findById.get();
			List<StudentEnquiryEntity> enquires = userDtlsEntity.getEnquries();
			return enquires;		
		}
		return null;
	}
	@Override
	public List<StudentEnquiryEntity> getFilteredEnqs(EnquirySearchCriteria criteria, Integer userId) {
		Optional<UserDetailsEntity> findById = userDtlsRepo.findById(userId);
		if(findById.isPresent()) {
			UserDetailsEntity userDtlsEntity = findById.get();
			List<StudentEnquiryEntity> enquires = userDtlsEntity.getEnquries();
			
			if(null!=criteria.getCourseName()
					& !"".equals(criteria.getCourseName())) {
				enquires = enquires.stream()
				.filter(e -> e.getCourseName().equals(criteria.getCourseName()))
				.collect(Collectors.toList());
			}

			if(null!=criteria.getEnqStatus()
					& !"".equals(criteria.getEnqStatus())) {
				enquires = enquires.stream()
				.filter(e -> e.getEnqStatus().equals(criteria.getEnqStatus()))
				.collect(Collectors.toList());
			}
			if(null!=criteria.getClassMode()
					& !"".equals(criteria.getClassMode())) {
				enquires = enquires.stream()
				.filter(e -> e.getClassMode().equals(criteria.getClassMode()))
				.collect(Collectors.toList());
			}
			
			
			return enquires;		
		}
		return null;			
}
}

	
	