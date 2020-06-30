package com.knowlegene.parent.process.io.neo4j.impl;

import com.knowlegene.parent.config.util.BaseUtil;
import com.knowlegene.parent.process.common.constantenum.Neo4jEnum;
import com.knowlegene.parent.process.io.neo4j.Neo4jIO;
import com.knowlegene.parent.process.io.neo4j.Neo4jSwap;
import com.knowlegene.parent.process.pojo.neo4j.Neo4jObject;

import org.apache.beam.sdk.coders.SerializableCoder;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.StatementResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * @Author: limeng
 * @Date: 2019/9/23 17:49
 */
public abstract class Neo4jSwapImpl implements Neo4jSwap, Serializable {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Neo4jIO.Write<Neo4jObject> write(String statement) {
        if(BaseUtil.isNotBlank(statement)){

            return Neo4jIO.<Neo4jObject>write()
                    .withDriverConfiguration(Neo4jIO.DriverConfiguration
                            .create(getDriver()))
                    .withStatement(statement);
        }else{
            logger.info("statement is null");
        }
        return null;
    }

    @Override
    public Neo4jIO.Write<Neo4jObject> relate(String statement, String label) {
        if(BaseUtil.isNotBlank(statement) && BaseUtil.isNotBlank(label)){

            return Neo4jIO.<Neo4jObject>write()
                    .withDriverConfiguration(Neo4jIO.DriverConfiguration
                            .create(getDriver()))
                    .withStatement(statement).withLabel(label).withOptionsType(Neo4jEnum.RELATE.getName());
        }else{
            logger.info("statement is null");
        }
        return null;
    }


    @Override
    public Neo4jIO.Read<Neo4jObject> query(String sql) {
        if(BaseUtil.isNotBlank(sql)){

            return Neo4jIO.<Neo4jObject>read()
                    .withDriverConfiguration(Neo4jIO.DriverConfiguration.create(getDriver()))
                    .withQuery(sql)
                    .withCoder(SerializableCoder.of(Neo4jObject.class))
                    .withRowMapper(new Neo4jIO.RowMapper<Neo4jObject>(){
                        @Override
                        public Neo4jObject mapRow(StatementResult resultSet) throws Exception {
                            return new Neo4jObject(resultSet.list().toArray());
                        }
                    });

        }
        return null;
    }

    public abstract Driver getDriver();

}
