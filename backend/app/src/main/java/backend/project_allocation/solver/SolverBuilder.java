package backend.project_allocation.solver;

import backend.project_allocation.domain.Schedule;
import org.optaplanner.core.api.solver.SolverManager;
import org.optaplanner.core.config.solver.SolverConfig;
import org.optaplanner.core.config.solver.SolverManagerConfig;
import org.optaplanner.core.config.solver.termination.TerminationConfig;
import org.springframework.stereotype.Service;

@Service
public class SolverBuilder {

    private SolverConfig solverConfig = SolverConfig.createFromXmlResource("scheduleSolverConfig.xml");

    public SolverBuilder withTermination(Long terminationTimeInMinutes){
        solverConfig.setTerminationConfig(new TerminationConfig().withMinutesSpentLimit(terminationTimeInMinutes));
        return this;
    }

    public SolverManager<Schedule, Long> build(){
        return SolverManager.create(solverConfig, new SolverManagerConfig());
    }
}
