package in.abc.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.abc.entity.EnquiryStatusEntity;

public interface EnquiryStatusRepo extends JpaRepository<EnquiryStatusEntity, Integer>{

	/*public List<EnqStatusEntity> findAll();

	public void saveAll(List<EnqStatusEntity> asList);

	public void deleteAll();*/

}
 