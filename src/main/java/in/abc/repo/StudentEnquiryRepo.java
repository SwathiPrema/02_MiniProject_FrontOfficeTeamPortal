package in.abc.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.abc.entity.StudentEnquiryEntity;


public interface StudentEnquiryRepo extends JpaRepository<StudentEnquiryEntity,Integer>{

}
