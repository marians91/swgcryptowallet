package com.swag.crypto.wallet.portfolio.connector;

import com.google.gson.Gson;
import com.swag.crypto.wallet.portfolio.model.bean.Crypto;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

public class ExternalConnector {

    private static final String COINBASE_ENDPOINT = "https://api.coinbase.com/v2/prices/BTC-USD/spot";

    public static Crypto btcRTdata() {
        Crypto bitcoin = null;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(COINBASE_ENDPOINT)
                .build();
        try {
            Response response = client.newCall(request).execute();
            JSONObject jsonResponse = new JSONObject(response.body().string());

            String json = jsonResponse.getJSONObject("data").toString();
            Gson gson = new Gson();
            bitcoin = gson.fromJson(json, Crypto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitcoin;
    }

}
