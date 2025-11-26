package broadbandrecomendation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import broadbandrecomendation.model.BroadbandPlan;

public interface BroadbandPlanRepository extends JpaRepository<BroadbandPlan, Long> {
	List<BroadbandPlan> findBySpeedGreaterThanEqualAndPriceLessThanEqual(int speed, double price);
	

}
