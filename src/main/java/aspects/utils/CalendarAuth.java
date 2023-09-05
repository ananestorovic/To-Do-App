package aspects.utils;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

public class CalendarAuth {

    private static final String CREDENTIALS_FILE_PATH = "C:\\Users\\na223367m.DESKTOP-QLFHC8P\\IdeaProjects\\TaskManager\\src\\main\\resources\\credentials.json";
    private static final String TOKENS_DIRECTORY_PATH =  "tokens";

    public static Credential authorize() throws Exception {
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        List<String> scopes = Collections.singletonList(CalendarScopes.CALENDAR);

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory,
                new InputStreamReader(new FileInputStream(CREDENTIALS_FILE_PATH)));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, jsonFactory, clientSecrets, scopes)
                .setDataStoreFactory(new FileDataStoreFactory(new File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    public static Calendar getCalendarService() throws Exception {
        Credential credential = authorize();
        return new Calendar.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), credential)
                .setApplicationName("Your Application Name")
                .build();
    }
}