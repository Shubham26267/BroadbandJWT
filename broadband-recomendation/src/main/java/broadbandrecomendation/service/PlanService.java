package broadbandrecomendation.service;

import java.util.List;

import broadbandrecomendation.model.BroadbandPlan;

public interface PlanService {
	
	BroadbandPlan addPlan(BroadbandPlan plan);
    BroadbandPlan updatePlan(Long id, BroadbandPlan updatedPlan);
    void deletePlan(Long id);
    List<BroadbandPlan> getAllPlans();
    List<BroadbandPlan> recommendPlans(int minSpeed, double maxBudget);
    List<BroadbandPlan> comparePlans(List<Long> planIds);


}
