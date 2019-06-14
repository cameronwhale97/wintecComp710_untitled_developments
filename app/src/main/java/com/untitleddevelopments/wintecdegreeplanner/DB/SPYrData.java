package com.untitleddevelopments.wintecdegreeplanner.DB;
import java.util.List;
/**
 * SPYrData
 * Contains 2 Lists of SP Mod. For a particular year level of a module
 * 1. modules yet to complete
 * 2. modules completed
 * Used in mutable data which is passed to ui.StuPlan/Placeholder fragment
 * Author Geoff Genner
 */
public class SPYrData {
    public List<SPMod> yTComp;
    public List<SPMod> compl;

    public SPYrData(List<SPMod> yTComp, List<SPMod> compl) {
        this.yTComp = yTComp;
        this.compl = compl;
    }

    public List<SPMod> getModsYTComp() {
        return yTComp;
    }

    public void setModsYTComp(List<SPMod> yTComp) {
        this.yTComp = yTComp;
    }

    public List<SPMod> getModsComp() {
        return compl;
    }

    public void setModsComp(List<SPMod> compl) {
        this.compl = compl;
    }
}
