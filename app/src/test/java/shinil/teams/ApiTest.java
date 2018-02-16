package shinil.teams;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

import io.reactivex.observers.TestObserver;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import shinil.teams.api.TeamApiServiceImp;
import shinil.teams.model.Team;
import shinil.teams.util.Constants;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * @author shinilms
 */

public class ApiTest {
    private MockWebServer mockWebServer;
    private TestObserver<List<Team>> testObserver;

    @Before
    public void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        testObserver = new TestObserver<>();
    }

    @Test
    public void test_getTeams_request() throws Exception {
        mockWebServer.enqueue(new MockResponse());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        TeamApiServiceImp remoteDataSource = new TeamApiServiceImp(retrofit);

        remoteDataSource.getTeams().subscribe(testObserver);

        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals("/teams.json", request.getPath());
        assertEquals("GET", request.getMethod());
    }

    @Test
    public void test_api_success() throws InterruptedException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        TeamApiServiceImp remoteDataSource = new TeamApiServiceImp(retrofit);
        remoteDataSource.getTeams().subscribe(testObserver);

        testObserver.awaitTerminalEvent();

        testObserver.assertNoErrors();
        testObserver.assertValueCount(1);
    }

    @Test
    public void test_404_response_code() throws InterruptedException {
        mockWebServer.enqueue(new MockResponse().setResponseCode(HttpURLConnection.HTTP_NOT_FOUND));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        TeamApiServiceImp remoteDataSource = new TeamApiServiceImp(retrofit);

        remoteDataSource.getTeams().subscribe(testObserver);

        testObserver.awaitTerminalEvent();

        assertEquals(1, testObserver.errorCount());
        assertTrue(testObserver.errors().get(0) instanceof HttpException);
    }

    @After
    public void tearDown() throws IOException {
        mockWebServer.shutdown();
    }
}
