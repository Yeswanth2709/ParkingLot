package services;

import models.Gate;
import repositories.GateRepository;

public class GateServiceImpl implements GateService{
    private GateRepository gateRepository;

    public GateServiceImpl(GateRepository gateRepository) {
        this.gateRepository = gateRepository;
    }

    @Override
    public Gate getGateById(int gateId) {
        return gateRepository.getGateById(gateId);
    }
}
