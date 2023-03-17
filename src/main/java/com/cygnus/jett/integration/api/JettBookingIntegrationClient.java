package com.cygnus.jett.integration.api;

import com.cygnus.jett.constants.Constants;
import com.cygnus.jett.integration.resources.AuthorizationJettIntegration;
import com.cygnus.jett.integration.resources.InstructorsJettBookingResponse;
import com.cygnus.jett.integration.resources.ReservationJettBookingResponse;
import com.google.gson.Gson;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JettBookingIntegrationClient {
    private final static Logger LOGGER = LoggerFactory.getLogger(JettBookingIntegrationClient.class);
    private static OkHttpClient httpClient = new OkHttpClient();
    public static final MediaType mediaType = MediaType.parse("application/json;");

    public static AuthorizationJettIntegration getAuthorizationToken() throws IOException {
        LOGGER.info("stage=init method=sendPost");
        Response response = null;
        AuthorizationJettIntegration authorizationJettIntegration = null;

        String autRQ = new StringBuilder()
                .append("{")
                .append("\"email\":\"admin@jettbooking.com\",")
                .append("\"password\":\"astrotech2022\"")
                .append("}").toString();

        RequestBody body = RequestBody.create(
                autRQ,
                mediaType
        );

        Request request = new Request.Builder()
                .url(Constants.BASE_URL + "/sign-in")
                .addHeader("User-Agent", "OkHttp Bot")
                .post(body)
                .build();

        try {
            response = httpClient.newCall(request).execute();

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Gson gson = new Gson();

            authorizationJettIntegration = gson.fromJson(response.body().string(), AuthorizationJettIntegration.class);
        }catch (Exception e){
            throw new IOException(e);
        }

        LOGGER.info("stage=end method=sendPost response={}", authorizationJettIntegration);
        return authorizationJettIntegration;
    }

    public static InstructorsJettBookingResponse getInstructors(String token) throws IOException {
        InstructorsJettBookingResponse instructorsJettBookingResponse = null;

        Request request = new Request.Builder()
                .url(Constants.BASE_URL + "/instructors")
                .addHeader("Context", "4346061e-f544-49a4-b034-076bcbff2101")
                .addHeader("Authorization", "Bearer " + token)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Gson gson = new Gson();

            instructorsJettBookingResponse = gson.fromJson(response.body().string(), InstructorsJettBookingResponse.class);
        }

        return instructorsJettBookingResponse;

    }

    public static ReservationJettBookingResponse getReservations(String token, String dataReservation) throws IOException {
        ReservationJettBookingResponse reservationJettBookingResponse = null;

        Request request = new Request.Builder()
                .url(Constants.BASE_URL + "/regular-view/reservations/"+ dataReservation)
                .addHeader("Context", "4346061e-f544-49a4-b034-076bcbff2101")
                .addHeader("Authorization", "Bearer " + token)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Gson gson = new Gson();

            reservationJettBookingResponse = gson.fromJson(response.body().string(), ReservationJettBookingResponse.class);
        }

        return reservationJettBookingResponse;

    }

}
