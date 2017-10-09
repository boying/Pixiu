package es;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class EsTest {
    static String index = "customer";
    static String type = "user";

    @Test
    void testIndex() throws UnknownHostException {
        Settings settings = Settings.builder()
                .put("cluster.name", "jzw_cluster").build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9301))
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9303));

        Map<String, Object> json = new HashMap<String, Object>();
        json.put("id", 123);
        json.put("user", "kimchy");
        json.put("message", "trying out Elasticsearch");

        IndexResponse indexResponse = client.prepareIndex(index, type).setSource(json).get();
        String id = indexResponse.getId();
        System.out.println("ret id is " + id);
        client.close();
    }

    /**
     * es client很强大
     * 会将addTransportAddress做负载均衡
     * 若某个连接节点无效了，会自动去请求剩下的有效节点
     * 还有client.transport.sniff开关，打开的话，client会去连接集群中的data节点，代码中原先设置的连接节点有可能就不去连了
     *
     * https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/transport-client.html
     */
    @Test
    void testQuery() throws UnknownHostException {
        Settings settings = Settings.builder()
                .put("cluster.name", "jzw_cluster").build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9301))
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9303))
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9310));

        for(int i = 0; i < Integer.MAX_VALUE; ++i){
            MatchAllQueryBuilder builder = QueryBuilders.matchAllQuery();
            SearchResponse searchResponse = client.prepareSearch(index).setTypes(type).setQuery(builder).get();
            System.out.println("response status is " + searchResponse.status());
            for (SearchHit hit : searchResponse.getHits().getHits()) {
                System.out.println(hit.getSourceAsString());
            }

            try {
                Thread.sleep(2000);
            }catch (Throwable ignore){}
        }

        client.close();
    }


}
