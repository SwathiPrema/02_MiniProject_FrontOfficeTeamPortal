package in.abc.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.abc.binding.DashboardResponse;
import in.abc.binding.EnquiryForm;
import in.abc.binding.EnquirySearchCriteria;
import in.abc.entity.StudentEnquiryEntity;
import in.abc.repo.StudentEnquiryRepo;
import in.abc.service.EnquiryService;

@Controller
public class EnquiryController {

	@Autowired
	private EnquiryService enqService;

	@Autowired
	private HttpSession session;

	@Autowired
	private StudentEnquiryRepo studentRepo;

	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "index";
	}

	@GetMapping("/edit")
	public String edit(@RequestParam("enqId") Integer enqId, Model model) {

		Optional<StudentEnquiryEntity> findById = studentRepo.findById(enqId);
		if (findById.isPresent()) {
			StudentEnquiryEntity studentEnqEntity = findById.get();
			model.addAttribute("formObj", studentEnqEntity);

		}
		return "add-enquiry";
	}

	@GetMapping("/delete")
	public String delete(@RequestParam("enqId") Integer enqId, Model model) {

		studentRepo.deleteById(enqId);

		model.addAttribute("msg", "Product Deleted");
		model.addAttribute("enquires", studentRepo.findAll());
		return "View-enquires";

	}
	@GetMapping("/viewenquires")
	public String getAllproducts(Model model) {
		List<StudentEnquiryEntity> list = studentRepo.findAll();
		model.addAttribute("enquires", list);
		return "View-enquires";
	}

	@GetMapping("/dashboard")
	public String dashboardPage(Model model) {

		Integer userId = (Integer) session.getAttribute("userId");

		DashboardResponse dashboardData = enqService.getDashboardData(userId);

		model.addAttribute("dashboardData", dashboardData);

		return "dashboard";
	}

	@GetMapping("/enquiry")
	public String addEnquiryPage(Model model) {

		model.addAttribute("formObj", new EnquiryForm());

		initForm(model);

		return "add-enquiry";
	}

	@PostMapping("/addEnq")
	public String addEnquiry(@ModelAttribute("formObj") EnquiryForm formObj, Model model) {

		boolean status = enqService.saveEnquiry(formObj);

		if (status) {
			model.addAttribute("successMsg", "Enquiry Added..");
		} else {
			model.addAttribute("errMsg", "Problem Occured..");
		}
		return "add-enquiry";
	}

	private void initForm(Model model) {
		// get courses for dropdown

		List<String> courses = enqService.getCourses();

		// get enqStatus for dropdown

		List<String> enqStatus = enqService.getEnqStatuses();

		// create binding class obj

		EnquiryForm formObj = new EnquiryForm();

		// set data in model obj

		model.addAttribute("courseNames", courses);
		model.addAttribute("statusNames", enqStatus);
		model.addAttribute("formObj", formObj);

	}

	@GetMapping("/enquires")
	public String viewEnquiryPage(Model model) {
		initForm(model);
		// model.addAttribute("searchForm", new EnquirySearchCriteria());
		List<StudentEnquiryEntity> enquires = enqService.getEnquires();
		model.addAttribute("enquires", enquires);
		return "View-enquires";

	}
	
	@GetMapping("/filter-enquries")
	public String getFilteredEnqs(@RequestParam("cname") String cname, @RequestParam("status") String status,
			@RequestParam("mode") String mode, Model model) {

		EnquirySearchCriteria criteria = new EnquirySearchCriteria();
		criteria.setCourseName(cname);
		criteria.setClassMode(mode);
		criteria.setEnqStatus(status);

		Integer userId = (Integer) session.getAttribute("userId");

		List<StudentEnquiryEntity> filteredEnqs = enqService.getFilteredEnqs(criteria, userId);

		model.addAttribute("enquires", filteredEnqs);

		return "filter-enquiry-page";
	}

}
