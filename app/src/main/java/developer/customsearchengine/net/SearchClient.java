package developer.customsearchengine.net;

import android.content.Context;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import developer.customsearchengine.R;
import developer.customsearchengine.models.ImageFilter;

/**
 * Created by appaz on 12/24/17.
 */

public class SearchClient {
    private static final String API_BASE_URL = "https://www.googleapis.com/customsearch/v1?";
    private static final String API_KEY = "AIzaSyC3iIVRWgmK_LJg1HY95Agv9Bil9mwgeDY";
    private static final String CX_KEY = "016661389732567762114:yir8iacczn0";
    private AsyncHttpClient client;

    public SearchClient(){
        this.client = new AsyncHttpClient();
    }

    private String getApiUrl(String relativeUrl){
        return API_BASE_URL + relativeUrl;   }

    public String getFilterUrl (ImageFilter imageFilter){
        String filterUrl = "";
        if (imageFilter.getColor() != null && !imageFilter.getColor().equals("Any")){
            filterUrl += "&imgDominantColor=" + imageFilter.getColor();
        }
        if (imageFilter.getSize() != null && !imageFilter.getSize().equals("Any")){
            filterUrl += "&imgSize=" + imageFilter.getSize();
        }
        if (imageFilter.getType() != null && !imageFilter.getType().equals("Any")){
            filterUrl += "&imgType=" + imageFilter.getType();
        }
        if (imageFilter.getSite() != null && !imageFilter.getSite().equals("")){
            filterUrl += "&siteSearch=" + imageFilter.getSite();
        }

        return filterUrl;
    }

    public void getSearch(final String query, int startPage, ImageFilter imageFilter, Context context, JsonHttpResponseHandler handler ){
        try {
            String url = getApiUrl("q="+ URLEncoder.encode(query,"utf-8")+"&start="+startPage+
                    "&cx="+CX_KEY+"&searchType=image"+getFilterUrl(imageFilter)+"&key="+API_KEY);
            client.get(url, handler);
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
            Toast.makeText(context, R.string.search_not_found, Toast.LENGTH_SHORT).show();
        }
    }
}