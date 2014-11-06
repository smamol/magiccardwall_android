package nz.co.trademe.fedex5.magiccardwall.api.response;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;

/**
 * Created by pakuhata on 7/11/14.
 */
public class HistoryResponse {

    @JsonProperty("List")
    private ArrayList<HistoryItem> list;

    public ArrayList<HistoryItem> getList() {
        return list;
    }

    public void setList(ArrayList<HistoryItem> list) {
        this.list = list;
    }
}
