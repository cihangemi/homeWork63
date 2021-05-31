package kodlamaio.hrms.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import kodlamaio.hrms.business.abstracts.CandidateService;
import kodlamaio.hrms.core.abstracts.EmailService;
import kodlamaio.hrms.core.abstracts.MernisService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.CandidateDao;
import kodlamaio.hrms.entities.concretes.Candidate;
import kodlamaio.hrms.entities.concretes.Employer;

public class CandidateManager implements CandidateService {
	private CandidateDao candidateDao;

	@Autowired
	public CandidateManager(CandidateDao candidateDao) {
		super();
		this.candidateDao = candidateDao;
	}

	@Override
	public DataResult<List<Candidate>> getall() {
		
		return new SuccessDataResult<List<Candidate>>(this.candidateDao.findAll());
	}
	
	private MernisService mernisService = new MernisService() {

		@Override
		public boolean isMernisConfirmed() {
			
			return false;
		}
		
	};
	
	private EmailService emailService = new EmailService() {

		@Override
		public boolean isEmailVerified() {
			
			return false;
		}
		
	};
	
	@Override
	public Result add(Candidate candidate) {
		if(this.candidateDao.existsByEmail(candidate.getEmail())==true)
			return new ErrorResult("This e-mail is registered in the system");
		
		else if(this.candidateDao.existsByNationalId(candidate.getNationalId())==true) 
			return new ErrorResult("This nationalId is registered to the system");
		else if (emailService.isEmailVerified( )==false)
			return new ErrorResult("Email not verified. Registration failed");
		else if (mernisService.isMernisConfirmed()==false)
			return new ErrorResult("Error mernis. Registration failed");
		
		this.candidateDao.save(candidate);
		return new SuccessResult("Email verified. Registration successful");
	}

}
