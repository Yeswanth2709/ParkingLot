package repositories;

import models.Slab;
import models.VehicleType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SlabRepository {
    private Map<Integer, Slab> map;

    public SlabRepository() {
        this.map=new HashMap<>();
    }

    public SlabRepository(Map<Integer, Slab> map) {
        this.map = map;
    }
    public List<Slab> getSlabsByVehicleType(VehicleType vehicleType){
        List<Slab> slabs=new ArrayList<>();
        for (Map.Entry<Integer, Slab> entry : map.entrySet()) {
            Slab slab = entry.getValue();
            if(slab.getVehicleType().equals(vehicleType)){
                slabs.add(slab);
            }
        }
        slabs.sort((s1,s2)->s1.getStartHour()-s1.getStartHour());
        return slabs;
    }
}
