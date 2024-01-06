package com.heima.model.schedule.dtos;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Builder;
import lombok.Data;
import java.io.Serializable;

@Data
public class Task implements Serializable {
    /**
     * 任务id
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long taskId;
    /**
     * 类型
     */
    private Integer taskType;

    /**
     * 优先级
     */
    private Integer priority;

    /**
     * 执行id
     */
    private long executeTime;

    /**
     * task参数
     */
    private byte[] parameters;

}
