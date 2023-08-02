package com.example.soleproject.dto;

import com.example.soleproject.entity.ScholarshipScnu;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ScholarshipForm {
    private Long id;
    private String title;
    //    private String fesdate;
    private String startdate;
    private String finaldate;
    private String place;
    private String info;

    private String filename;

    private String filepath;

    private String latitude;
    private String longitude;

    public ScholarshipScnu toEntity() {
        return new ScholarshipScnu(id,title,startdate,finaldate,place,info,filename,filepath,latitude,longitude);
    }
}
