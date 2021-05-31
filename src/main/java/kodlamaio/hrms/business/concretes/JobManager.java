package kodlamaio.hrms.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.JobService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.JobDao;
import kodlamaio.hrms.entities.concretes.Job;

@Service
public class JobManager implements JobService{
	private JobDao jobDao;
	
	@Autowired
	public JobManager(JobDao jobDao) {
		this.jobDao = jobDao;
	}

	@Override
	public DataResult<List<Job>> getAll() {
		// TODO Auto-generated method stub
		return new SuccessDataResult<>(this.jobDao.findAll());
	}

	@Override
	public DataResult<List<Job>> findByPositionIs(String position) {
		// TODO Auto-generated method stub
		return new SuccessDataResult<>(this.jobDao.findByPosition(position));
	}

	@Override
	public Result add(Job job) {
		if (this.findByPositionIs(job.getPosition()).getData().size()!=0){
			return new ErrorResult("This job position already exists");
		}

		this.jobDao.save(job);
		return new SuccessResult("Process succeeded");
	}
	

	
	
		
		
		
		


	

	

}
