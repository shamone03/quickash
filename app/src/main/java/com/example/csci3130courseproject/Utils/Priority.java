package com.example.csci3130courseproject.Utils;

import android.widget.Spinner;

public class Priority {
     public enum PRIORITY {
        LOW,
        MEDIUM,
        HIGH,
        URGENT
    }
    public static PRIORITY setLowPriority(){
        return PRIORITY.LOW;
    }

    public static PRIORITY setMedPriority(){ return PRIORITY.MEDIUM; }

    public static PRIORITY setHighPriority(){ return  PRIORITY.HIGH; }

    public static PRIORITY setUrgentPriority(){ return PRIORITY.URGENT; }

    public static boolean isValidPriority(String priority){
         priority = priority.toLowerCase();
         return priority.matches("low") || priority.matches("medium") || priority.matches("high")
                 || priority.matches("urgent");
    }

    public static PRIORITY getPriorityFromString(String priority){
         if (isValidPriority(priority)) {
            switch (priority.toLowerCase()) {
                case "low":
                    return setLowPriority();
                case "medium":
                    return setMedPriority();
                case "high":
                    return setHighPriority();
                case "urgent":
                    return setUrgentPriority();
            }
        }
         return setLowPriority();
    }

    public static PRIORITY getPriorityFromSpinner(Spinner menu){
         switch (menu.getSelectedItemPosition()){
             case 0:
             case 1:
                 return setLowPriority();
             case 2:
                 return setMedPriority();
             case 3:
                 return setHighPriority();
             case 4:
                 return setUrgentPriority();
         }
         return setLowPriority();
    }
}
