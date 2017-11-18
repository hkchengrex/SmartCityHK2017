package model;


import java.util.List;

import model.ModelResponse;
import parser.GeneralInfo;

/**
 * Created by cheng on 2/2/2017.
 */

public interface BaseTransportShotModel {

    ModelResponse<GeneralInfo> getTransport();
}
