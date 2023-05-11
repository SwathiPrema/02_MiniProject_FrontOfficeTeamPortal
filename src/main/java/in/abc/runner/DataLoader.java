package in.abc.runner;

import java.util.Arrays;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import in.abc.entity.CourseEntity;
import in.abc.entity.EnquiryStatusEntity;
import in.abc.repo.CourseRepo;
import in.abc.repo.EnquiryStatusRepo;

@Component
public class DataLoader implements ApplicationRunner{
	
	
   @Autowired
	private CourseRepo courseRepo;
   
   
   @Autowired
   private EnquiryStatusRepo statusRepo;
   
   @Override
	public void run(ApplicationArguments args) throws Exception {
		
		courseRepo.deleteAll();
		
		CourseEntity c1 = new CourseEntity();
		c1.setCourseName("Java-Realtime-Project");
		
		CourseEntity c2 = new CourseEntity();
		c2.setCourseName("AWS");
		
		CourseEntity c3 = new CourseEntity();
		c3.setCourseName("DevOps");
		
		CourseEntity c4 = new CourseEntity();
		c4.setCourseName("SpringBoot");
		
		courseRepo.saveAll(Arrays.asList(c1,c2,c3,c4));
		
		statusRepo.deleteAll();

		EnquiryStatusEntity s1 = new EnquiryStatusEntity();
		s1.setEnqStatus("New");
		
		EnquiryStatusEntity s2 = new EnquiryStatusEntity();
		s2.setEnqStatus("Enrolled");
		
		EnquiryStatusEntity s3 = new EnquiryStatusEntity();
		s3.setEnqStatus("Lost");
		
		statusRepo.saveAll(Arrays.asList(s1, s2, s3));
	}
}