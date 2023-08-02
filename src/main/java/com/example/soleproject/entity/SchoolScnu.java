package com.example.soleproject.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class SchoolScnu extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    //    @Column
//    private String fesdate;
    @Column
    private String startdate;
    @Column
    private String finaldate ;
    @Column
    private String place ;
    @Column
    private String info;
    @Column
    private String filename;
    @Column
    private String filepath;
    @Column
    private String latitude;
    @Column
    private String longitude;


    public MainEntity toMainEntity() {
        return new MainEntity(null,title,startdate,finaldate,place,info,filename,filepath,latitude,longitude);
    }

}

