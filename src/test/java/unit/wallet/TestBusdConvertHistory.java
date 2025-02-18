package unit.wallet;

import com.binance.connector.client.enums.HttpMethod;
import com.binance.connector.client.exceptions.BinanceConnectorException;
import com.binance.connector.client.impl.SpotClientImpl;
import java.util.LinkedHashMap;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Before;
import org.junit.Test;
import unit.MockData;
import unit.MockWebServerDispatcher;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class TestBusdConvertHistory {
    private MockWebServer mockWebServer;
    private String baseUrl;

    private final Integer size = 20;
    private final Long startTime = 118263400000L;
    private final Long endTime = 118263407119L;


    @Before
    public void init() {
        this.mockWebServer = new MockWebServer();
        this.baseUrl = mockWebServer.url(MockData.PREFIX).toString();
    }

    @Test
    public void testBusdConverWithWrongParamType() {
        String path = "/sapi/v1/asset/convert-transfer/queryByPage";
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("startTime", "118263400000L");
        parameters.put("endTime", "118263407119L");
        parameters.put("asset", "USDC");
        parameters.put("size", size);

        Dispatcher dispatcher = MockWebServerDispatcher.getDispatcher(MockData.PREFIX, path, MockData.MOCK_RESPONSE, HttpMethod.GET, MockData.HTTP_STATUS_OK);
        mockWebServer.setDispatcher(dispatcher);

        SpotClientImpl client = new SpotClientImpl(MockData.API_KEY, MockData.SECRET_KEY, baseUrl);
        assertThrows(BinanceConnectorException.class, () -> client.createWallet().busdConvertHistory(parameters));
    }

    @Test
    public void testBusdConverWithoutMandatoryParam() {
        String path = "/sapi/v1/asset/convert-transfer/queryByPage";
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("startTime", startTime);
        parameters.put("asset", "USDC");
        parameters.put("size", size);

        Dispatcher dispatcher = MockWebServerDispatcher.getDispatcher(MockData.PREFIX, path, MockData.MOCK_RESPONSE, HttpMethod.GET, MockData.HTTP_STATUS_OK);
        mockWebServer.setDispatcher(dispatcher);

        SpotClientImpl client = new SpotClientImpl(MockData.API_KEY, MockData.SECRET_KEY, baseUrl);
        assertThrows(BinanceConnectorException.class, () -> client.createWallet().busdConvertHistory(parameters));
    }

    @Test
    public void testBusdConvertHistory() {
        String path = "/sapi/v1/asset/convert-transfer/queryByPage";
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("startTime", startTime);
        parameters.put("endTime", endTime);
        parameters.put("asset", "USDC");
        parameters.put("size", size);

        Dispatcher dispatcher = MockWebServerDispatcher.getDispatcher(MockData.PREFIX, path, MockData.MOCK_RESPONSE, HttpMethod.GET, MockData.HTTP_STATUS_OK);
        mockWebServer.setDispatcher(dispatcher);

        SpotClientImpl client = new SpotClientImpl(MockData.API_KEY, MockData.SECRET_KEY, baseUrl);
        String result = client.createWallet().busdConvertHistory(parameters);
        assertEquals(MockData.MOCK_RESPONSE, result);
    }
}
