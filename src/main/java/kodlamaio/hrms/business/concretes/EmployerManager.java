package kodlamaio.hrms.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kodlamaio.hrms.business.abstracts.EmployerService;
import kodlamaio.hrms.core.abstracts.EmailService;
import kodlamaio.hrms.core.abstracts.HrmsValidationService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.dataAccess.abstracts.EmployerDao;
import kodlamaio.hrms.entities.concretes.Employer;
import kodlamaio.hrms.entities.concretes.User;

@Service
public class EmployerManager implements EmployerService {

	private EmployerDao employerDao;
	
	

	@Autowired
	public EmployerManager(EmployerDao employerDao) {
		super();
		this.employerDao = employerDao;
		
	}
	private HrmsValidationService hrmsValidationService = new HrmsValidationService() {

		@Override
		public boolean isValidate() {
			
			return false;
		}};
	
	private EmailService emailService = new EmailService() {

		@Override
		public boolean isEmailVerified() {
			
			return false;
		}
		
	};

	@Override
	public DataResult<List<Employer>> getall() {

		return new SuccessDataResult<List<Employer>>(this.employerDao.findAll(), "Listed");
	}

	@Override
	public Result add(Employer employer) {
		if (this.employerDao.existsByEmail(employer.getEmail()) == true)
			return new ErrorResult("This e-mail is added to the system");
		else if (isSameDomain(employer) == true)
			return new ErrorResult("Email address and Domain must be the same");

		else if (emailService.isEmailVerified()==false)
			return new ErrorResult("Email not verified. Registration failed");
		else if (hrmsValidationService.isValidate()==false)
			return new ErrorResult("Employee did not approve. Registration failed");
		this.employerDao.save(employer);
		return new SuccessResult("Email verified. Registration successful");

	}

	private boolean isSameDomain(Employer employer) {
		String email = employer.getEmail();
		String[] emailSplit = email.split("@");
		if (!emailSplit[1].equals(employer.getWebAddress()))
			return false;
		return true;
	}
	
	
}
