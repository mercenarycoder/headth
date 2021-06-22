package androidi.developer.headthapp;

import androidi.developer.headthapp.Report.reportOf3;

import java.util.ArrayList;

public class bundleReport
{
    String date;
    ArrayList<reportOf3> list;

    public bundleReport(String date, ArrayList<reportOf3> list) {
        this.date = date;
        this.list = list;
    }

    public String getDate() {
        return date;
    }

    public ArrayList<reportOf3> getList() {
        return list;
    }
}
