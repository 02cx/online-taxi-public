package com.dong.internalcommon.request;

import lombok.Data;

@Data
public class ApiDriverUploadTrackDTO {

    private Long carId;
    private PointsDTO[] points;
}
