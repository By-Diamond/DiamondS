package com.notdiamond.diamonds.core;

public class DiamondSFunction {
    String Name;
    Boolean Status = false;
    public DiamondSFunction(String FunctionName) {
        Name = FunctionName;
    }
    public void SetStatus(boolean NewStatus){
        Status = NewStatus;
    }
    public String GetName(){
        return Name;
    }
    public Boolean GetStatus(){
        return Status;
    }
}
