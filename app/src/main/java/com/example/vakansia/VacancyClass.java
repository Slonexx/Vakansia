package com.example.vakansia;

public class VacancyClass {
        String job_name, location, creation_date, salary, schedule;
        //String phone;
    VacancyClass(){

    }

    public VacancyClass(String job_name, String location, String creation_date, String salary, String schedule) {
        this.job_name = job_name;
        this.location = location;
        this.creation_date = creation_date;
        this.salary = salary;
        this.schedule = schedule;
    }

    public String getJob_name() {
        job_name = job_name.split(",")[0];
        return job_name;
    }

    public String getLocation() {
        int startNum = 22;
        int endNum = location.length();
        location = location.substring(startNum, endNum);
        return location;
    }

    /*public String getPhone() {
        return phone;
    }*/

    public String getCreation_date() {
        creation_date = creation_date.split(" ")[0];
        return creation_date;
    }

    public String getSalary() {
        return salary;
    }

    public String getSchedule() {
        return schedule;
    }
}
