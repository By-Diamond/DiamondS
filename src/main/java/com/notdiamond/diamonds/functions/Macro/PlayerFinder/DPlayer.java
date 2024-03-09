package com.notdiamond.diamonds.functions.Macro.PlayerFinder;

public class DPlayer implements Comparable<DPlayer>{
    String ID;
    Float Distance;
    Double X;
    Double Y;
    Double Z;
    public DPlayer(String ID_,Float Distance_,Double X_,Double Y_,Double Z_) {
        ID=ID_;
        Distance = Distance_;
        X=X_;
        Y=Y_;
        Z=Z_;
    }

    @Override
    public int compareTo(DPlayer o) {
        if(this.Distance.compareTo(o.Distance) == -1){
            return -1;
        }
        if(this.Distance.compareTo(o.Distance) == 0){
            return 0;
        }
        if(this.Distance.compareTo(o.Distance) == 1){
            return 1;
        }
        return 0;
    }
}
