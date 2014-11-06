package nz.co.trademe.fedex5.magiccardwall.api;

import nz.co.trademe.fedex5.magiccardwall.api.converter.JacksonConverter;
import retrofit.RestAdapter;
import retrofit.converter.Converter;

/**
 * Created by pakuhata on 6/11/14.
 */
public class JiraApiWrapper {

    private static JiraApiWrapper instance;
    private JiraApi jiraApi;

    private JiraApiWrapper() {
        Converter converter = new JacksonConverter();

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setConverter(converter)
                .setEndpoint("http://tmlt218.trademe.local:8888")
                .setLogLevel(RestAdapter.LogLevel.FULL);

        RestAdapter restAdapter = builder.build();
        jiraApi = restAdapter.create(JiraApi.class);
    }

    static public JiraApiWrapper getSingleton() {
        if (instance == null) {
            instance = new JiraApiWrapper();
        }

        return instance;
    }

    public JiraApi getApi() {
        return jiraApi;
    }
}