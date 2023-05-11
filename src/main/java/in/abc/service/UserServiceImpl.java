package in.abc.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.abc.binding.LoginForm;
import in.abc.binding.SignUpForm;
import in.abc.binding.UnlockForm;
import in.abc.entity.UserDetailsEntity;
import in.abc.repo.UserDetailsRepo;
import in.abc.utils.EmailUtils;
import in.abc.utils.PwdUtils;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	public UserDetailsRepo userDtlsRepo;

	@Autowired
	public EmailUtils emailUtils;
	
	@Autowired
	public HttpSession session;

	// @Override
	public boolean signup(SignUpForm form) {
		UserDetailsEntity user = userDtlsRepo.findByEmail(form.getEmail());

		if (user != null) {
			return false;
		}

		// copy data form binding obj to entity obj
		UserDetailsEntity entity = new UserDetailsEntity();
		BeanUtils.copyProperties(form, entity);

		// generate random pwd and set to object
		String tempPwd = PwdUtils.generateRandomPwd();
		entity.setPwd(tempPwd);

		// set account status as locked
		entity.setAccStatus("Locked");

		// insert record
		userDtlsRepo.save(entity);

		// send email to unlock the account
		String to = form.getEmail();
		String subject = "Unlock your Account : Ashok IT";
		StringBuffer body = new StringBuffer("");
		body.append("<h1> Use below temporary pwd to unlock your account</h1>");

		body.append("Temparary pwd : " + tempPwd);

		body.append("<br/>");

		body.append("<a href=\"http://localhost:8080/unlock?email=" + to + "\">Click Here To Unlock Your Account</a>");

		emailUtils.sendEmail(to, subject, body.toString());

		return true;
	}

	// @Override
	public boolean unlockAccount(UnlockForm form) {

		UserDetailsEntity entity = userDtlsRepo.findByEmail(form.getEmail());

		if (entity.getPwd().equals(form.getTempPwd())) {
			entity.setPwd(form.getNewPwd());
			entity.setAccStatus("UNLOCKED");
			userDtlsRepo.save(entity);
			return true;

		} else {
			return false;
		}

	}

	public String login(LoginForm form) {

		UserDetailsEntity entity = userDtlsRepo.findByEmailAndPwd(form.getEmail(), form.getPwd());

		if (entity == null) {
			return "Invalid Credentials";
		}
		if (entity.getAccStatus().equals("LOCKED")) {
			return "Your Account Locked";
		}
      
		//create session and store user data in session
		session.setAttribute("userId",entity.getUserId());
		
		
		return "success";
	}

	public boolean forgotPwd(String email) {

		// check record present in db with givem mail
		UserDetailsEntity entity = userDtlsRepo.findByEmail(email);

		// if record not availabe send error msg
		if (entity == null) {
			return false;
		}

		// if record available send pwd to email and send success msg
		String Subject = "Recover Password";
		String body = "Your Pwd :: " + entity.getPwd();

		emailUtils.sendEmail(email, Subject, body);

		return true;
	}

}
