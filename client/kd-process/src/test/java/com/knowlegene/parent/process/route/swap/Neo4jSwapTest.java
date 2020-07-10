package com.knowlegene.parent.process.route.swap;

import com.knowlegene.parent.process.SwapDirectApplication;
import com.knowlegene.parent.process.pojo.SwapOptions;
import com.knowlegene.parent.process.runners.SwapRunners;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @Author: limeng
 * @Date: 2019/9/30 14:09
 */
@RunWith(JUnit4.class)
public class Neo4jSwapTest   extends SwapRunners {

    private static SwapDirectApplication application;
    private static SwapOptions swapOptions;
    @Override
    public void setJobStream() {


    }
    @BeforeClass
    public static void beforeClass(){
        application=new SwapDirectApplication();
        swapOptions = new SwapOptions();
    }
    @AfterClass
    public static void  afterClass(){
        application.setSwapOptions(swapOptions);
        application.start();

    }


    /**
     * 导出 export
     * hive -> neo4j
     * save
     */
    @Test
    public void testExportSave(){

        swapOptions.setFromName("hive");
        swapOptions.setHiveClass("org.apache.hive.jdbc.HiveDriver");
        swapOptions.setHiveUrl("jdbc:hive2://192.168.200.117:10000/default");
        swapOptions.setHiveUsername("hive");
        swapOptions.setHivePassword("hive");
        swapOptions.setHiveTableName("node1");


        swapOptions.setToName("neo4j");
        swapOptions.setNeoUrl("bolt://localhost:7687");
        swapOptions.setNeoUsername("neo4j");
        swapOptions.setNeoPassword("limeng");

        //id:ID(Node) name iscp regCap regCapTyp invGrtTyp
        swapOptions.setNeoFormat("id:ID(Node) name age");


    }

    @Test
    public void testExportSave2(){
        swapOptions.setFromName("hive");
        swapOptions.setHiveClass("org.apache.hive.jdbc.HiveDriver");
        swapOptions.setHiveUrl("jdbc:hive2://192.168.200.117:10000/linkis_db");
        swapOptions.setHiveUsername("hdfs");
        swapOptions.setHivePassword("hdfs");
        swapOptions.setHiveTableName("node1");

        swapOptions.setToName("neo4j");
        swapOptions.setNeoUrl("bolt://localhost:7687");
        swapOptions.setNeoUsername("neo4j");
        swapOptions.setNeoPassword("limeng");
        /**
         * 标签名称Node 语句字段名称跟hive表列名一致
         */
        //CREATE (a:Node {id: {id}, name: {name},age:{age},regcap:{regcap},regcaptyp:{regcaptyp},invgrttyp:{invgrttyp} })
        swapOptions.setCypher("CREATE (a:Node {name: {name},age:{age}})");


    }

    /**
     * 导出 export
     * hive -> neo4j
     * relate
     */
    @Test
    public void testExportRelate(){
        swapOptions.setFromName("hive");
        swapOptions.setHiveClass("org.apache.hive.jdbc.HiveDriver");
        swapOptions.setHiveUrl("jdbc:hive2://192.168.200.117:10000/linkis_db");
        swapOptions.setHiveUsername("hdfs");
        swapOptions.setHivePassword("hdfs");
        swapOptions.setHiveTableName("pretest_rel");

        swapOptions.setToName("neo4j");
        swapOptions.setNeoUrl("bolt://localhost:7687");
        swapOptions.setNeoUsername("neo4j");
        swapOptions.setNeoPassword("limeng");
        /**
         * type 为固定列，标识关系的标签名称
         * 开始 start_id 固定列
         * 结束 end_id 固定列
         *
         */
        swapOptions.setNeoFormat(":START_ID(Node) :END_ID(Node) weight type");


    }

    @Test
    public void testExportRelate2(){
        swapOptions.setFromName("hive");
        swapOptions.setHiveClass("org.apache.hive.jdbc.HiveDriver");
        swapOptions.setHiveUrl("jdbc:hive2://192.168.200.117:10000/linkis_db");
        swapOptions.setHiveUsername("hdfs");
        swapOptions.setHivePassword("hdfs");
        swapOptions.setHiveTableName("relate1");

        swapOptions.setToName("neo4j");
        swapOptions.setNeoUrl("bolt://localhost:7687");
        swapOptions.setNeoUsername("neo4j");
        swapOptions.setNeoPassword("limeng");
        /**
         * 节点 标签名称 Node
         * 连接 标签名称Test
         * 语句字段名称跟hive表列名一致
         */
        /**
         * MATCH (a:Node),(b:Node) WHERE a.id={start_id} AND b.id={end_id} " +
         *                 " CREATE (a)-[r:Test {weight:{weight} ,isperson:{isperson} ,createdate:{createdate} ,updatedate:{updatedate} , type:{type} , title:{title}  }] ->(b)
         */
        swapOptions.setCypher("MATCH (a:Node),(b:Node) WHERE a.id={startid} AND b.id={endid} " +
                " CREATE (a)-[r:Test {weight:{weight}  , type:{type}   }] ->(b) ");


    }


}
