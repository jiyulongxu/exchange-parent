package com.knowlegene.parent.process.tool;

import com.knowlegene.parent.config.common.constantenum.DBOperationEnum;
import com.knowlegene.parent.config.common.constantenum.DatabaseTypeEnum;
import com.knowlegene.parent.config.common.event.HiveExportType;
import com.knowlegene.parent.config.common.event.HiveImportType;
import com.knowlegene.parent.config.common.event.MySQLExportType;
import com.knowlegene.parent.config.common.event.MySQLImportType;
import com.knowlegene.parent.config.util.BaseUtil;
import com.knowlegene.parent.process.pojo.SwapOptions;
import com.knowlegene.parent.process.swap.dispatcher.SwapMaster;
import com.knowlegene.parent.process.swap.JobBase;
import com.knowlegene.parent.process.swap.event.HiveExportTaskEvent;
import com.knowlegene.parent.process.swap.event.HiveImportTaskEvent;
import com.knowlegene.parent.process.swap.event.MySQLExportTaskEvent;
import com.knowlegene.parent.process.swap.event.MySQLImportTaskEvent;
import com.knowlegene.parent.process.util.*;
import com.knowlegene.parent.scheduler.service.Service;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.TreeMap;

/**
 * @Author: limeng
 * @Date: 2019/8/20 19:12
 */
@Data
public  class BaseSwapTool {
    private final Logger logger=LoggerFactory.getLogger(getClass());
    private SwapOptions options;
    public static String isImport = "import";
    public static String isExport = "export";
    private static String fromPre = "from_";
    private static String toSuffix = "to_";


    public BaseSwapTool(SwapOptions options) {
        this.options = options;
        setDataSource();
    }

    public Logger getLogger() {
        return logger;
    }
    public BaseSwapTool() {

    }

    private static final Map<String, Object> TOOLS;
    static {
        TOOLS = new TreeMap<String, Object>();
        registerTool(fromPre+DatabaseTypeEnum.HIVE.getName(),new HiveExportTaskEvent(HiveExportType.T_EXPORT));
        registerTool(toSuffix+DatabaseTypeEnum.HIVE.getName(),new HiveImportTaskEvent(HiveImportType.T_IMPORT));

        registerTool(fromPre+DatabaseTypeEnum.MYSQL.getName(),new MySQLExportTaskEvent(MySQLExportType.T_EXPORT));
        registerTool(toSuffix+DatabaseTypeEnum.MYSQL.getName(),new MySQLImportTaskEvent(MySQLImportType.T_IMPORT));
    }

    private static void registerTool(String toolName,
                                     Object cls) {
        Object  existing = TOOLS.get(toolName);
        if (null != existing) {
            // Already have a tool with this name. Refuse to start.
            throw new RuntimeException("A plugin is attempting to register a tool "
                    + "with name " + toolName + ", but this tool already exists ("
                    + existing.getClass().getName() + ")");
        }
        TOOLS.put(toolName, cls);
    }

    public static Object getTask(String toolName){
        return TOOLS.get(toolName);
    }

    public void run() throws Exception {
        SwapMaster swapMaster = new SwapMaster("Swap MRAppMaster");
        swapMaster.serviceInit();
        swapMaster.serviceStart();
        new JobBase(options).run();
    }


    /**
     * 设置源
     */
    private void setDataSource(){
        String fromName = options.getFromName();
        String toName = options.getToName();


        if(fromName.equalsIgnoreCase(toName) && fromName.equalsIgnoreCase(DatabaseTypeEnum.HIVE.getName())){
            DataSourceUtil.setHiveImExport(DBOperationEnum.HIVE_EXPORT.getName(),options);
            DataSourceUtil.setHiveImExport(DBOperationEnum.HIVE_IMPORT.getName(),options);

        } else if(DatabaseTypeEnum.isDB(fromName) && DatabaseTypeEnum.isDB(toName)){
            DataSourceUtil.setDBImExport(queryOperation(fromName,true,false),options);
            DataSourceUtil.setDBImExport(queryOperation(toName,false,false),options);

        }else{
            queryOperation(fromName,true,true);
            queryOperation(toName,false,true);
        }


    }

    private String queryOperation(String name,boolean isFrom,boolean isOptions){
        DatabaseTypeEnum databaseTypeEnum = DatabaseTypeEnum.queryValue(name);
        String result="";
        switch (databaseTypeEnum){
            case HIVE:
                if(isFrom) result = DBOperationEnum.HIVE_EXPORT.getName();
                else result = DBOperationEnum.HIVE_IMPORT.getName();
                if(isOptions) DataSourceUtil.setHive(result,options);
                break;
            case MYSQL:
                if(isFrom) result = DBOperationEnum.MYSQL_EXPORT.getName();
                else  result = DBOperationEnum.MYSQL_IMPORT.getName();
                if(isOptions) DataSourceUtil.setDb(result,options);
                break;
            case ES:
                if(isFrom) result = DBOperationEnum.ES_EXPORT.getName();
                else  result = DBOperationEnum.ES_IMPORT.getName();

                break;
            case ELASTICSEARCH:
                if(isFrom) result = DBOperationEnum.ES_EXPORT.getName();
                else  result = DBOperationEnum.ES_IMPORT.getName();
                break;
            case GBASE:
                if(isFrom)  result = DBOperationEnum.GBASE_EXPORT.getName();
                else  result = DBOperationEnum.GBASE_IMPORT.getName();
                if(isOptions) DataSourceUtil.setDb(result,options);
                break;
            case FILE:
                if(isFrom)  result = DBOperationEnum.FILE_EXPORT.getName();
                else  result = DBOperationEnum.FILE_IMPORT.getName();
                break;
            case ORACLE:
                if(isFrom)  result = DBOperationEnum.ORACLE_EXPORT.getName();
                else  result = DBOperationEnum.ORACLE_IMPORT.getName();
                if(isOptions) DataSourceUtil.setDb(result,options);
                break;
        }
        return result;
    }


}
