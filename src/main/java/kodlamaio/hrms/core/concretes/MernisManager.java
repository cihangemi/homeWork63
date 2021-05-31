package kodlamaio.hrms.core.concretes;

import kodlamaio.hrms.core.abstracts.MernisService;
import kodlamaio.hrms.entities.concretes.Employer;

public class MernisManager implements MernisService {

	@Override
	public boolean isMernisConfirmed() {
		
		return true;
	}

}
