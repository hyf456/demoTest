package com.han.es;

import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

/**
 * @Author:
 * @Description:
 * @Date: 2018/4/8 14:52
 */
public class UpdateObject {

    @Autowired
    private TransportClient transportClient;

    /**
     * 使用Script对嵌套对象指定属性进行修改
     */
    @Test
    public void updateObject4() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", 1106);
        params.put("firstSaletypeName", "一级分类");
        params.put("thirdSaletypeName", "三级分类");
        StringBuffer sbStr = new StringBuffer("" +
                "ArrayList list = ctx._source.saletypes; " +
                "for(int i=0; i<list.size();i++) { " +
                "   if(list[i].id == params.id) { " +
                "       list[i].firstSaletypeName=params.firstSaletypeName; " +
                "       list[i].thirdSaletypeName=params.thirdSaletypeName; " +
                "} }");
        Script script = new Script(ScriptType.INLINE, "painless", sbStr.toString(), params);
        UpdateResponse response = transportClient
                .prepareUpdate("my_index_v1", "my_index", "1")
                .setScript(script).execute().actionGet();
        System.out.println("修改结果：" + response.getResult().getLowercase());
    }
}
