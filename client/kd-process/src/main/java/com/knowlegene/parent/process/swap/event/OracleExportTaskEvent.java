package com.knowlegene.parent.process.swap.event;

import com.knowlegene.parent.config.common.event.OracleExportType;
import com.knowlegene.parent.scheduler.event.AbstractEvent;

/**
 * @Classname
 * @Description TODO
 * @Date 2020/6/11 15:53
 * @Created by limeng
 */
public class OracleExportTaskEvent extends AbstractEvent<OracleExportType> {


    public OracleExportTaskEvent(OracleExportType exportEnum) {
        super(exportEnum);
    }

}
