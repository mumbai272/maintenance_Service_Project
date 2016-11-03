package com.android.maintenance.DTO;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by anand on 25-Oct-16.
 */
public class MachinesDTO implements Serializable {

    private ArrayList<MachineDetailDTO> machine;

    public MachinesDTO(ArrayList<MachineDetailDTO> machine) {
        this.machine = machine;
    }

    public ArrayList<MachineDetailDTO> getMachine() {
        return machine;
    }

    public void setMachine(ArrayList<MachineDetailDTO> machine) {
        this.machine = machine;
    }
}
