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
 * es
 * @Author: limeng
 * @Date: 2019/9/10 15:24
 */
@RunWith(JUnit4.class)
public class ESSwapTest extends SwapRunners {
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
        application.run();
    }

    @Test
    public void testMysql(){
        swapOptions.setFromName("es");
        swapOptions.setToName("mysql");

        String[] addrs=new String[]{"http://192.168.200.101:9200"};
        String index="lmtest";
        String type="_doc";
        swapOptions.setEsAddrs(addrs);
        swapOptions.setEsIndex(index);
        swapOptions.setEsType(type);
        swapOptions.setEsIdFn("id");

        swapOptions.setUrl("jdbc:mysql://192.168.200.115:3306/kd_test?useSSL=false");
        swapOptions.setTableName("test3_copy");
        swapOptions.setDriverClass("com.mysql.jdbc.Driver");
        swapOptions.setUsername("root");
        swapOptions.setPassword("root");

    }
    @Test
    public void testOracle(){
        swapOptions.setFromName("es");
        swapOptions.setToName("oracle");


        String[] addrs=new String[]{"http://192.168.200.101:9200"};
        String index="lmtest";
        String type="_doc";
        swapOptions.setEsAddrs(addrs);
        swapOptions.setEsIndex(index);
        swapOptions.setEsType(type);
        swapOptions.setEsIdFn("id");

        swapOptions.setUrl("jdbc:oracle:thin:@//192.168.200.25:1521/huaxia");
        swapOptions.setTableName("test1_copy");
        swapOptions.setDriverClass("oracle.jdbc.driver.OracleDriver");
        swapOptions.setUsername("kg");
        swapOptions.setPassword("kg");

    }


    @Test
    public void testGbase(){
        swapOptions.setFromName("es");
        swapOptions.setToName("gbase");



        String[] addrs=new String[]{"http://192.168.200.101:9200"};
        String index="lmtest";
        String type="_doc";
        swapOptions.setEsAddrs(addrs);
        swapOptions.setEsIndex(index);
        swapOptions.setEsType(type);
        swapOptions.setEsIdFn("id");


        swapOptions.setUrl("jdbc:gbase://192.168.100.1:5258/test");
        swapOptions.setTableName("test1_copy");
        swapOptions.setDriverClass("com.gbase.jdbc.Driver");
        swapOptions.setUsername("root");
        swapOptions.setPassword("gbase");
    }

    @Test
    public void testHive(){
        swapOptions.setFromName("es");
        swapOptions.setToName("hive");



        String[] addrs=new String[]{"http://192.168.200.101:9200"};
        String index="lmtest";
        String type="_doc";
        swapOptions.setEsAddrs(addrs);
        swapOptions.setEsIndex(index);
        swapOptions.setEsType(type);
        swapOptions.setEsIdFn("id");

        swapOptions.setHiveClass("org.apache.hive.jdbc.HiveDriver");
        swapOptions.setHiveUrl("jdbc:hive2://192.168.200.117:10000/linkis_db");
        swapOptions.setHiveUsername("hdfs");
        swapOptions.setHivePassword("hdfs");
        swapOptions.setHiveTableName("pretest");
        swapOptions.setHiveTableEmpty(true);
    }



    @Test
    public void testFile(){
        swapOptions.setFromName("es");
        swapOptions.setToName("file");


        String[] addrs=new String[]{"http://192.168.200.101:9200"};
        String index="lmtest";
        String type="_doc";
        swapOptions.setEsAddrs(addrs);
        swapOptions.setEsIndex(index);
        swapOptions.setEsType(type);
        swapOptions.setEsIdFn("id");

        swapOptions.setFilePath("test.cvs");
        swapOptions.setFieldDelim("#");
    }

    @Test
    public void testES(){
        swapOptions.setFromName("es");
        swapOptions.setToName("es");


        String[] addrs=new String[]{"http://192.168.200.101:9200"};

        String type="_doc";
        swapOptions.setEsAddrsFrom(addrs);
        swapOptions.setEsAddrsTo(addrs);
        swapOptions.setEsTypes(new String[]{type,type});
        swapOptions.setEsIndexs(new String[]{"lmtest","lmtest2"});
        swapOptions.setEsIdFn("id");
    }



    @Test
    public void testNeo4jNode(){
        swapOptions.setFromName("es");
        swapOptions.setToName("neo4j");

        String[] addrs=new String[]{"http://192.168.200.101:9200"};
        String index="lmtest";
        String type="_doc";
        swapOptions.setEsAddrs(addrs);
        swapOptions.setEsIndex(index);
        swapOptions.setEsType(type);
        swapOptions.setEsIdFn("id");


        swapOptions.setNeoUrl("bolt://localhost:7687");
        swapOptions.setNeoUsername("neo4j");
        swapOptions.setNeoPassword("limeng");

        //id:ID(Node) name iscp regCap regCapTyp invGrtTyp
        /**
         * 按照模板
         * 模板字段等于插入顺序
         * 第一个ID固定
         */
        swapOptions.setNeoFormat("id:ID(Node) name age ctime");
    }
    @Test
    public void testNeo4jNode2(){
        swapOptions.setFromName("es");
        swapOptions.setToName("neo4j");

        String[] addrs=new String[]{"http://192.168.200.101:9200"};
        String index="lmtest";
        String type="_doc";
        swapOptions.setEsAddrs(addrs);
        swapOptions.setEsIndex(index);
        swapOptions.setEsType(type);
        swapOptions.setEsIdFn("id");


        swapOptions.setNeoUrl("bolt://localhost:7687");
        swapOptions.setNeoUsername("neo4j");
        swapOptions.setNeoPassword("limeng");

        swapOptions.setCypher("CREATE (a:Node {name: {name},age:{age},id:{id},ctime:{ctime}})");
    }

    @Test
    public void testNeo4jRelate(){
        swapOptions.setFromName("es");
        swapOptions.setToName("neo4j");


        String[] addrs=new String[]{"http://192.168.200.101:9200"};
        String index="lmtest";
        String type="_doc";
        swapOptions.setEsAddrs(addrs);
        swapOptions.setEsIndex(index);
        swapOptions.setEsType(type);
        swapOptions.setEsIdFn("id");

        swapOptions.setNeoUrl("bolt://localhost:7687");
        swapOptions.setNeoUsername("neo4j");
        swapOptions.setNeoPassword("limeng");


        /**
         * type 为固定列，标识关系的标签名称
         * 开始 start_id 固定列
         * 结束 end_id 固定列
         */
        swapOptions.setNeoFormat(":START_ID(Node) :END_ID(Node) weight type");
    }

    @Test
    public void testNeo4jRelate2(){
        swapOptions.setFromName("es");
        swapOptions.setToName("neo4j");

        String[] addrs=new String[]{"http://192.168.200.101:9200"};
        String index="lmtest";
        String type="_doc";
        swapOptions.setEsAddrs(addrs);
        swapOptions.setEsIndex(index);
        swapOptions.setEsType(type);
        swapOptions.setEsIdFn("id");

        swapOptions.setNeoUrl("bolt://localhost:7687");
        swapOptions.setNeoUsername("neo4j");
        swapOptions.setNeoPassword("limeng");

        //id:ID(Node) name iscp regCap regCapTyp invGrtTyp
        /**
         * type 为固定列，标识关系的标签名称
         * isPerson createDate updateDate type title
         */
        swapOptions.setCypher("MATCH (a:Node),(b:Node) WHERE a.id={start_id} AND b.id={end_id} CREATE (a)-[r:Test {weight:{weight} ,type:{type}}] ->(b) ");
    }





}
