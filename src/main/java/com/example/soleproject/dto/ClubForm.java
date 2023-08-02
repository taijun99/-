package com.example.soleproject.dto;

import com.example.soleproject.entity.ClubScnu;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ClubForm {
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

    public ClubScnu toEntity() {
        return new ClubScnu(id,title,startdate,finaldate,place,info,filename,filepath,latitude,longitude);
    }
}
