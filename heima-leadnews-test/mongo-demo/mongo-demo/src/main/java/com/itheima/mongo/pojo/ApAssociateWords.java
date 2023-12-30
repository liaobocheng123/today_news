package com.itheima.mongo.pojo;


import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 联想词表
 * </p>
 *
 * @author itheima
 */

@Document("ap_associate_words")
public class ApAssociateWords implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 联想词
     */
    private String associateWords;

    /**
     * 创建时间
     */
    private Date createdTime;

    public String getAssociateWords() {
        return associateWords;
    }

    public void setAssociateWords(String associateWords) {
        this.associateWords = associateWords;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "ApAssociateWords{" +
                "id='" + id + '\'' +
                ", associateWords='" + associateWords + '\'' +
                ", createdTime=" + createdTime +
                '}';
    }
}