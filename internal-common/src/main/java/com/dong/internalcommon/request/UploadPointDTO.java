package com.dong.internalcommon.request;

import lombok.Data;

@Data
public class UploadPointDTO {

    private String tid;
    private String trid;
    private PointsDTO[] points;
}
