package broadbandrecomendation.service.impl;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import broadbandrecomendation.exception.ResourceNotFoundException;
import broadbandrecomendation.model.BroadbandPlan;
import broadbandrecomendation.repository.BroadbandPlanRepository;
import broadbandrecomendation.service.PlanService;

@Service
@Transactional
public class PlanServiceImpl implements PlanService {

    @Autowired
    private BroadbandPlanRepository broadbandPlanRepository;

    @Override
    public BroadbandPlan addPlan(BroadbandPlan plan) {
        return broadbandPlanRepository.save(plan);
    }

    @Override
    public BroadbandPlan updatePlan(Long id, BroadbandPlan updatedPlan) {
        BroadbandPlan existing = broadbandPlanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plan not found with id: " + id));

        existing.setName(updatedPlan.getName());
        existing.setSpeed(updatedPlan.getSpeed());
        existing.setPrice(updatedPlan.getPrice());
        existing.setDataCap(updatedPlan.getDataCap());
        existing.setDescription(updatedPlan.getDescription());

        return broadbandPlanRepository.save(existing);
    }

    @Override
    public void deletePlan(Long id) {
        BroadbandPlan existing = broadbandPlanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plan not found with id: " + id));
        broadbandPlanRepository.delete(existing);
    }

    @Override
    public List<BroadbandPlan> getAllPlans() {
        return broadbandPlanRepository.findAll();
    }

    @Override
    public List<BroadbandPlan> recommendPlans(int minSpeed, double maxBudget) {
        List<BroadbandPlan> recommended = broadbandPlanRepository
                .findBySpeedGreaterThanEqualAndPriceLessThanEqual(minSpeed, maxBudget);

        if (recommended.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No suitable broadband plans found for given speed and budget");
        }

        // Sort by price ascending then speed descending
        recommended.sort(Comparator
                .comparingDouble(BroadbandPlan::getPrice)
                .thenComparing(BroadbandPlan::getSpeed, Comparator.reverseOrder()));

        return recommended;
    }

    @Override
    public List<BroadbandPlan> comparePlans(List<Long> planIds) {
        List<BroadbandPlan> plans = broadbandPlanRepository.findAllById(planIds);
        if (plans.isEmpty()) {
            throw new ResourceNotFoundException("No plans found for comparison");
        }
        return plans;
    }
}