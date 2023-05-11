package in.abc.service;

import in.abc.binding.LoginForm;
import in.abc.binding.SignUpForm;
import in.abc.binding.UnlockForm;

public interface UserService {
	
	public boolean signup(SignUpForm form);
	
	public boolean unlockAccount(UnlockForm form);
		
    public String login(LoginForm form);
    
    public boolean forgotPwd(String email);
    
	}


