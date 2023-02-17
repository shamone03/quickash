package com.example.csci3130courseproject;

public class Priority {
     enum PRIORITY {
        LOW,
        MEDIUM,
        HIGH,
        URGENT
    }

    public PRIORITY setLowPriority(){
        return PRIORITY.LOW;
    }

    public PRIORITY setMedPriority(){ return PRIORITY.MEDIUM; }

    public PRIORITY setHighPriority(){ return  PRIORITY.HIGH; }

    public PRIORITY setUrgentPriority(){ return PRIORITY.URGENT; }

    public boolean isValidPriority(String priority){
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
}
