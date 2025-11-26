package broadbandrecomendation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import broadbandrecomendation.model.BroadbandPlan;
import broadbandrecomendation.service.PlanService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PlanController {

	@Autowired
	private PlanService planService;

	// ------------------------- CUSTOMER & ADMIN ------------------------------

	@GetMapping("/plans/recommend")
	public ResponseEntity<?> getRecommendedPlans(@RequestParam int speed, @RequestParam double budget) {
		List<BroadbandPlan> recommended = planService.recommendPlans(speed, budget);
		return ResponseEntity.ok(recommended);
	}

	@PostMapping("/plans/compare")
	public ResponseEntity<?> comparePlans(@RequestBody List<Long> planIds) {
		List<BroadbandPlan> compared = planService.comparePlans(planIds);
		return ResponseEntity.ok(compared);
	}

	// ---------------------------- ADMIN ONLY --------------------------------

	@PostMapping("/admin/add")
	public ResponseEntity<?> addPlan(@RequestBody BroadbandPlan plan) {
		BroadbandPlan saved = planService.addPlan(plan);
		return ResponseEntity.ok(saved);
	}

	@PutMapping("/admin/{id}")
	public ResponseEntity<?> updatePlan(@PathVariable Long id, @RequestBody BroadbandPlan plan) {
		BroadbandPlan updated = planService.updatePlan(id, plan);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping("/admin/{id}")
	public ResponseEntity<?> deletePlan(@PathVariable Long id) {
		planService.deletePlan(id);
		return ResponseEntity.ok("Plan deleted successfully");
	}

	@GetMapping("/admin/all")
	public ResponseEntity<?> getAllPlans() {
		List<BroadbandPlan> all = planService.getAllPlans();
		return ResponseEntity.ok(all);
	}
	
	@GetMapping("/getAllPlans")
	public ResponseEntity<?> getPlans() {
		List<BroadbandPlan> all = planService.getAllPlans();
		return ResponseEntity.ok(all);
	}
}

/*
 * import java.util.List;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.http.ResponseEntity; import
 * org.springframework.web.bind.annotation.GetMapping; import
 * org.springframework.web.bind.annotation.PostMapping; import
 * org.springframework.web.bind.annotation.RequestBody; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.RequestParam; import
 * org.springframework.web.bind.annotation.RestController;
 * 
 * import broadbandrecomendation.model.BroadbandPlan; import
 * broadbandrecomendation.service.PlanService; /*
 * 
 * @RestController
 * 
 * @RequestMapping("api/plans") public class PlanController {
 * 
 * @Autowired private PlanService planservice;
 * 
 * @GetMapping("/recommend") public ResponseEntity<List<BroadbandPlan>>
 * getRecommendedPlans(@RequestParam int speed, @RequestParam double budget){
 * return ResponseEntity.ok(planservice.recommendPlans(speed, budget));
 * 
 * }
 * 
 * @PostMapping("/compare") public ResponseEntity<List<BroadbandPlan>>
 * comparePlans(@RequestBody List<Long>planIds){ return
 * ResponseEntity.ok(planservice.comparePlans(planIds)); }
 * 
 * 
 * }
 */