package nz.co.trademe.fedex5.magiccardwall.api;

import retrofit.RestAdapter;

/**
 * Created by pakuhata on 6/11/14.
 */
public class JiraApiWrapper {

    private static JiraApiWrapper instance;
    private JiraApi jiraApi;

    private JiraApiWrapper() {
        RestAdapter.Builder builder = new RestAdapter
                .Builder();

        builder.setEndpoint("TODO add endpoint");

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