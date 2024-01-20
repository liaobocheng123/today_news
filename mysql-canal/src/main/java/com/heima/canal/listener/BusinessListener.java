package com.heima.canal.listener;


import com.alibaba.otter.canal.protocol.CanalEntry;
import com.xpand.starter.canal.annotation.CanalEventListener;
import com.xpand.starter.canal.annotation.ListenPoint;


@CanalEventListener //声明当前的类是canal的监听类
public class BusinessListener {



    /**
     *
     * @param eventType 当前操作数据库的类型
     * @param rowData 当前操作数据库的数据
     */
    /*@ListenPoint(schema = "immp_sys")
    public void adUpdate(CanalEntry.EventType eventType, CanalEntry.RowData rowData){
        System.out.println("====================================================================================");
        
        rowData.getBeforeColumnsList().forEach((c)-> System.out.println("immp_sys改变前的数据:"+c.getName()+" 为  "+c.getValue()));
        
        System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>");
        
        rowData.getAfterColumnsList().forEach((c)-> System.out.println("immp_sys改变后的数据:"+c.getName()+" 为  "+c.getValue()));

        System.out.println("====================================================================================");
    }*/
    
    
    @ListenPoint(schema = "immp_wf")
    public void adUpdate2(CanalEntry.EventType eventType, CanalEntry.RowData rowData){
        System.out.println("====================================================================================");
        
        rowData.getBeforeColumnsList().forEach((c)-> System.out.println("immp_wf库改变前的数据:"+c.getName()+" 为  "+c.getValue()));

        System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>");

        rowData.getAfterColumnsList().forEach((c)-> System.out.println("immp_wf库改变后的数据:"+c.getName()+" 为  "+c.getValue()));

        System.out.println("====================================================================================");
    }
    
    
    /*@ListenPoint(schema = "immp_ds")
    public void adUpdate3(CanalEntry.EventType eventType, CanalEntry.RowData rowData){
        System.out.println("====================================================================================");

        rowData.getBeforeColumnsList().forEach((c)-> System.out.println("immp_ds改变前的数据:"+c.getName()+" 为  "+c.getValue()));

        System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>");

        rowData.getAfterColumnsList().forEach((c)-> System.out.println("immp_ds改变后的数据:"+c.getName()+" 为  "+c.getValue()));

        System.out.println("====================================================================================");
    }*/
}