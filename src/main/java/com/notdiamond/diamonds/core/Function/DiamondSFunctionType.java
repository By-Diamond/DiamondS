package com.notdiamond.diamonds.core.Function;

import java.util.ArrayList;

public class DiamondSFunctionType {
    String Name ;
    ArrayList<String> Type_SubFuction = new ArrayList();

    public DiamondSFunctionType(String FunctionTypeName) {
        Name = FunctionTypeName;
    }
    public String GetName() {
        return Name;
    }

    public void RegisterFunction(String FunctionName){
        Type_SubFuction.add(FunctionName);
    }

    public ArrayList<String> GetFunctionList(){
        return Type_SubFuction;
    }
}
